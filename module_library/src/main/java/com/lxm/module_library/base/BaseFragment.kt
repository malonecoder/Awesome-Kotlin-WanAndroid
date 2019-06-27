package com.lxm.module_library.base

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProviders
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewStub
import android.widget.ImageView
import android.widget.RelativeLayout
import com.lxm.module_library.R
import com.lxm.module_library.utils.ClassUtil
import io.reactivex.disposables.CompositeDisposable

abstract class BaseFragment<VM : ViewModel> : Fragment() {

    // ViewModel
    protected lateinit var viewModel: VM
    // 布局view
    protected lateinit var contentView: View
    // fragment是否显示了
    protected var mIsVisible = false
    // 加载中
    private val loadingView: View by lazy{
        (getView<View>(R.id.vs_loading) as ViewStub).inflate()
    }

    // 加载失败
    private val errorView: View by lazy{
        (getView<View>(R.id.vs_error_refresh) as ViewStub).inflate()
    }
    // 动画
    private var mAnimationDrawable: AnimationDrawable? = null

    private var mCompositeDisposable: CompositeDisposable? = null


    private var isViewCreated: Boolean = false // 界面是否已创建完成
    private var isVisibleToUser: Boolean = false // 是否对用户可见
    private var isDataLoaded: Boolean = false // 数据是否已请求, isNeedReload()返回false的时起作用

    /**
     * ViewPager场景下，判断父fragment是否可见
     *
     * @return
     */
    private val isParentVisible: Boolean
        get() {
            val fragment = parentFragment
            return fragment == null || fragment is BaseFragment<*> && fragment.isVisibleToUser
        }

    /**
     * fragment再次可见时，是否重新请求数据，默认为flase则只请求一次数据
     *
     * @return
     */
    protected val isNeedReload: Boolean
        get() = false

    /**
     * 布局
     */
    protected abstract fun getLayoutID():Int

    // 实现具体的数据请求逻辑
    protected abstract fun loadData()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val ll = inflater.inflate(R.layout.fragment_base, null)
        contentView = LayoutInflater.from(activity).inflate(getLayoutID(), null, false)
        val params =
            RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        contentView.layoutParams = params
        val mContainer = ll.findViewById<RelativeLayout>(R.id.container)
        mContainer.addView(contentView)
        return ll
    }

    /**
     * 在这里实现Fragment数据的缓加载.
     */
    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        this.isVisibleToUser = isVisibleToUser
        tryLoadData()
    }

    private fun tryLoadData() {
        if (isViewCreated && isVisibleToUser && isParentVisible && (isNeedReload || !isDataLoaded)) {
            loadData()
            isDataLoaded = true
            dispatchParentVisibleState()
        }
    }

    /**
     * ViewPager场景下，当前fragment可见，如果其子fragment也可见，则尝试让子fragment加载请求
     */
    private fun dispatchParentVisibleState() {
        val fragmentManager = childFragmentManager
        val fragments = fragmentManager.fragments
        if (fragments.isEmpty()) {
            return
        }

        for (child in fragments) {
            if (child is BaseFragment<*> && child.isVisibleToUser) {
                child.tryLoadData()
            }
        }
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        isViewCreated = true
        initLoadView()
        initViewModel()
        tryLoadData()
    }


    private fun initLoadView() {
        val img = loadingView.findViewById<ImageView>(R.id.img_progress)
        // 加载动画
        mAnimationDrawable = img.drawable as AnimationDrawable
        // 默认进入页面就开启动画
        if (!mAnimationDrawable?.isRunning!!) {
            mAnimationDrawable?.start()
        }
        contentView.visibility = View.GONE
    }

    /**
     * 初始化ViewModel
     */
    private fun initViewModel() {
        val viewModelClass = ClassUtil.getViewModel<VM>(this)
        if (viewModelClass != null) {
            this.viewModel = ViewModelProviders.of(this).get(viewModelClass)
        }
    }

    protected fun <T : View> getView(id: Int): T {
        return view!!.findViewById<View>(id) as T
    }

    /**
     * 加载失败后点击后的操作
     */
    protected abstract fun onRetry()

    /**
     * 显示加载中状态
     */
    protected fun showLoading() {
        if (loadingView != null && loadingView.visibility != View.VISIBLE) {
            loadingView.visibility = View.VISIBLE
        }
        // 开始动画
        if (!mAnimationDrawable!!.isRunning) {
            mAnimationDrawable!!.start()
        }
        if (contentView.visibility != View.GONE) {
            contentView.visibility = View.GONE
        }
        if (errorView != null) {
            errorView.visibility = View.GONE
        }
    }

    /**
     * 加载完成的状态
     */
    protected fun showContentView() {
        if (loadingView != null && loadingView.visibility != View.GONE) {
            loadingView.visibility = View.GONE
        }
        // 停止动画
        if (mAnimationDrawable != null && mAnimationDrawable!!.isRunning) {
            mAnimationDrawable!!.stop()
        }
        if (errorView != null) {
            errorView.visibility = View.GONE
        }
        if (contentView.visibility != View.VISIBLE) {
            contentView.visibility = View.VISIBLE
        }
    }

    /**
     * 加载失败点击重新加载的状态
     */
    protected fun showError() {
        if (loadingView != null && loadingView.visibility != View.GONE) {
            loadingView.visibility = View.GONE
        }
        // 停止动画
        if (mAnimationDrawable != null && mAnimationDrawable!!.isRunning) {
            mAnimationDrawable!!.stop()
        }
        if (errorView != null) {
            errorView.visibility = View.VISIBLE
            // 点击加载失败布局
            errorView.setOnClickListener {
                showLoading()
                onRetry()
            }
        } else {

        }
        if (contentView.visibility != View.GONE) {
            contentView.visibility = View.GONE
        }
    }

}
