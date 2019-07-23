package com.lxm.wanandroid.ui

import android.arch.lifecycle.Observer
import android.os.Bundle
import com.lxm.module_library.base.BaseFragment
import com.lxm.module_library.utils.RefreshHelper
import com.lxm.module_library.xrecycleview.XRecyclerView
import com.lxm.wanandroid.R
import com.lxm.wanandroid.repository.model.ArticleBean
import com.lxm.wanandroid.repository.model.Banner
import com.lxm.wanandroid.repository.model.Status
import com.lxm.wanandroid.ui.adapter.ArticleAdapter
import com.lxm.wanandroid.ui.base.OnItemClickListener
import com.lxm.wanandroid.utils.webview.WebViewActivity
import com.lxm.wanandroid.viewmodel.ArticleViewModel
import com.lxm.wanandroid.viewmodel.ProjectViewModel
import kotlinx.android.synthetic.main.article_fragment.*

class ProjectFragment : BaseFragment<ProjectViewModel>() {

    private val mAdapter: ArticleAdapter by lazy {
        ArticleAdapter()
    }

    override fun getLayoutID(): Int {
        return R.layout.article_fragment
    }

    companion object {
        fun getInstance(): ProjectFragment {
            return ProjectFragment()
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
        recyclerView.adapter = mAdapter
        recyclerView.setLoadingListener(object : XRecyclerView.LoadingListener {

            override fun onLoadMore() {
                viewModel.mPage = viewModel.mPage + 1;
                getProjectList()
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
            swipeLayout.isRefreshing = false
            if (it == null) {
                return@Observer
            }
            if(viewModel.mPage ==0){
                mAdapter.setData(it.data?.datas!!)
                return@Observer
            }
            mAdapter.addDataAll(it.data?.datas!!)
            if(it?.data?.datas?.size!! < it?.data?.size!!){
                recyclerView.noMoreLoading()
            }else{
                recyclerView.refreshComplete()
            }
        })

        mAdapter.setOnItemClickListener(object : OnItemClickListener<ArticleBean> {
            override fun onClick(t: ArticleBean, position: Int) {
                WebViewActivity.loadUrl(activity, t.link, t.title)
            }
        })
    }

    override fun onRetry() {
        loadData()
    }

    override fun loadData() {
        getProjectList()
    }

    private fun getProjectList() {
        this.viewModel.getProjects()
    }
}