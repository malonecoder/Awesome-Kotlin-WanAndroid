package com.lxm.module_library.base

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProviders
import android.content.res.Configuration
import android.content.res.Resources
import android.graphics.drawable.AnimationDrawable
import android.os.Build
import android.support.annotation.LayoutRes
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.View
import android.view.ViewGroup
import android.view.ViewStub
import android.widget.ImageView
import android.widget.RelativeLayout
import com.lxm.module_library.R
import com.lxm.module_library.utils.ClassUtil

abstract class BaseActivity<VM : ViewModel> : AppCompatActivity() {

    // ViewModel
    protected lateinit var viewModel: VM
    // 布局view
    protected lateinit var contentsView: View

    private lateinit var errorView: View
    private lateinit var loadingView: View
    private lateinit var mBaseView: View
    private var mAnimationDrawable: AnimationDrawable? = null

    protected fun <T : View> getView(id: Int): T {
        return findViewById<View>(id) as T
    }

    protected abstract fun getLayoutID():Int

    override fun setContentView(@LayoutRes layoutResID: Int) {

        mBaseView = layoutInflater.inflate(R.layout.activity_base, null, false)
        contentsView = layoutInflater.inflate(getLayoutID(), null, false)

        // content
        val params =
            RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        contentsView.layoutParams = params
        val mContainer = mBaseView.findViewById<View>(R.id.container) as RelativeLayout
        mContainer.addView(contentsView)
        window.setContentView(mBaseView)

        // 设置透明状态栏，兼容4.4
        //        StatusBarUtil.setColor(this, CommonUtils.getColor(R.color.colorTheme), 0);
        loadingView = (findViewById<View>(R.id.vs_loading) as ViewStub).inflate()
        val img = loadingView.findViewById<ImageView>(R.id.img_progress)

        // 加载动画
        mAnimationDrawable = img.drawable as AnimationDrawable
        // 默认进入页面就开启动画
        if (!mAnimationDrawable!!.isRunning) {
            mAnimationDrawable!!.start()
        }

        setToolBar()
        contentsView.visibility = View.GONE
        initViewModel()
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

    /**
     * 设置titlebar
     */
    protected fun setToolBar() {
        setSupportActionBar(mBaseView.findViewById<View>(R.id.tool_bar) as Toolbar)
        val actionBar = supportActionBar
        if (actionBar != null) {
            //去除默认Title显示
            actionBar.setDisplayShowTitleEnabled(false)
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.icon_back)
        }
        (mBaseView.findViewById<View>(R.id.tool_bar) as Toolbar).setNavigationOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                finishAfterTransition()
            } else {
                onBackPressed()
            }
        }
    }

    override fun setTitle(text: CharSequence) {
        (mBaseView.findViewById<View>(R.id.tool_bar) as Toolbar).title = text
    }

    protected fun showLoading() {
        if (loadingView != null && loadingView.visibility != View.VISIBLE) {
            loadingView.visibility = View.VISIBLE
        }
        // 开始动画
        if (!mAnimationDrawable!!.isRunning) {
            mAnimationDrawable!!.start()
        }
        if (contentsView.visibility != View.GONE) {
            contentsView.visibility = View.GONE
        }
        if (errorView != null) {
            errorView.visibility = View.GONE
        }
    }

    protected fun showContentView() {
        if (loadingView != null && loadingView.visibility != View.GONE) {
            loadingView.visibility = View.GONE
        }
        // 停止动画
        if (mAnimationDrawable!!.isRunning) {
            mAnimationDrawable!!.stop()
        }
        if (errorView != null) {
            errorView.visibility = View.GONE
        }
        if (contentsView.visibility != View.VISIBLE) {
            contentsView.visibility = View.VISIBLE
        }
    }

    protected fun showError() {
        if (loadingView != null && loadingView.visibility != View.GONE) {
            loadingView.visibility = View.GONE
        }
        // 停止动画
        if (mAnimationDrawable!!.isRunning) {
            mAnimationDrawable!!.stop()
        }
        if (errorView != null) {
            errorView.visibility = View.VISIBLE
            // 点击加载失败布局
            errorView.setOnClickListener {
                showLoading()
                onRefresh()
            }
        }
        if (contentsView.visibility != View.GONE) {
            contentsView.visibility = View.GONE
        }
    }

    /**
     * 失败后点击刷新
     */
    protected fun onRefresh() {

    }


    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        if (newConfig.fontScale != 1f) {
            resources
        }
    }

    /**
     * 禁止改变字体大小
     */
    override fun getResources(): Resources {
        val res = super.getResources()
        val config = Configuration()
        config.setToDefaults()
        res.updateConfiguration(config, res.displayMetrics)
        return res
    }

    public override fun onDestroy() {
        super.onDestroy()

    }
}
