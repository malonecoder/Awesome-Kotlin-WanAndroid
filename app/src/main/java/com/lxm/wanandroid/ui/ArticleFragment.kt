package com.lxm.wanandroid.ui

import android.arch.lifecycle.Observer
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import com.lxm.module_library.base.BaseFragment
import com.lxm.module_library.utils.RefreshHelper
import com.lxm.module_library.xrecycleview.XRecyclerView
import com.lxm.wanandroid.R
import com.lxm.wanandroid.repository.model.ArticleBean
import com.lxm.wanandroid.repository.model.Banner
import com.lxm.wanandroid.repository.model.Status
import com.lxm.wanandroid.ui.base.OnItemClickListener
import com.lxm.wanandroid.utils.GlideUtil
import com.lxm.wanandroid.utils.webview.WebViewActivity
import com.lxm.wanandroid.viewmodel.ArticleViewModel
import com.zhouwei.mzbanner.holder.MZViewHolder
import kotlinx.android.synthetic.main.article_banner.*
import kotlinx.android.synthetic.main.article_fragment.*

class ArticleFragment : BaseFragment<ArticleViewModel>() {

    private lateinit var headerView: View
    private var bannerList : List<Banner>? = null
    private val mAdapter: ArticleAdapter by lazy {
        ArticleAdapter()
    }

    override fun getLayoutID(): Int {
        return R.layout.article_fragment
    }

    companion object {
        fun getInstance(): ArticleFragment {
            return ArticleFragment()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        showContentView()
        initView()
    }

    private fun initView() {
        swipeLayout.setOnRefreshListener {
            viewModel.mPage = 0
            loadData()
        }
        swipeLayout.isRefreshing = true
        RefreshHelper.init(recyclerView, false)
        headerView = layoutInflater.inflate(R.layout.article_banner, null)
        recyclerView.addHeaderView(headerView)

        recyclerView.adapter = mAdapter
        recyclerView.setLoadingListener(object : XRecyclerView.LoadingListener {

            override fun onLoadMore() {
                viewModel.mPage = viewModel.mPage + 1;
                getHomeList()
            }

            override fun onRefresh() {
            }
        })
        viewModel.loadStatus.observe(this, Observer {

            when (it?.status) {
                Status.ERROR -> showError()
                Status.SUCCESS -> showContentView()
            }
        })
        this.viewModel.pagedList.observe(this, Observer {
            if (it == null) {
                return@Observer
            }

            if(viewModel.mPage ==0){
                mAdapter.mutableList.clear()
            }
            swipeLayout.isRefreshing = false
            recyclerView.refreshComplete()

            mAdapter.addDataAll(it.data?.datas!!)
            val positionStart = mAdapter.itemCount + 2
            mAdapter.notifyItemRangeInserted(positionStart, it.data?.datas?.size!!)
        })

        mAdapter.setOnItemClickListener(object : OnItemClickListener<ArticleBean> {
            override fun onClick(t: ArticleBean, position: Int) {
                WebViewActivity.loadUrl(activity, t.link, t.title)
            }
        })
    }

    private fun getBanners() {
        viewModel.getBanners().observe(this@ArticleFragment, Observer {
            bannerList = it?.data
            banner.setBannerPageClickListener { view, i ->
                WebViewActivity.loadUrl(activity, it?.data?.get(i)?.url,null)
            }
            banner.setPages(
                it?.data as List<Nothing>?
            ) {
                BannerViewHolder()
            }
            banner.start()

        })
    }

    class BannerViewHolder : MZViewHolder<Banner> {
        private var mImageView: ImageView? = null

        override fun createView(context: Context): View {
            val view = LayoutInflater.from(context).inflate(R.layout.item_banner, null)
            mImageView = view.findViewById(R.id.banner_image) as ImageView
            return view
        }

        override fun onBind(context: Context, position: Int, data: Banner?) {
            data?.let {
                GlideUtil.displayCircleCorner(mImageView!!, data.imagePath)
            }
        }
    }


    override fun onRetry() {
        loadData()
    }

    override fun loadData() {
        getHomeList()
        getBanners()
    }

    private fun getHomeList() {
        this.viewModel.getHomeList()
    }

}