package com.lxm.wanandroid.ui

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.lxm.module_library.base.BaseFragment
import com.lxm.module_library.xrecycleview.XRecyclerView
import com.lxm.wanandroid.R
import com.lxm.wanandroid.repository.model.ArticleBean
import com.lxm.wanandroid.repository.model.Status
import com.lxm.wanandroid.ui.adapter.NaviAdapter
import com.lxm.wanandroid.utils.webview.WebViewActivity
import com.lxm.wanandroid.viewmodel.NaviViewModel
import com.zhy.view.flowlayout.FlowLayout
import kotlinx.android.synthetic.main.article_fragment.*

class NavigationFragment : BaseFragment<NaviViewModel>() {

    private val mAdapter: NaviAdapter by lazy {
        NaviAdapter(itemClickListener)
    }
    private val itemClickListener: NaviAdapter.OnItemNavigationClickListener = object :
        NaviAdapter.OnItemNavigationClickListener {

        override fun itemClick(view: View, position: Int, parent: FlowLayout, articles: List<ArticleBean>) {
            var article = articles[position]
            WebViewActivity.loadUrl(activity, article.link, article.title)
        }
    }
    override fun getLayoutID(): Int {
        return R.layout.tree_fragment
    }

    companion object {
        fun getInstance(): NavigationFragment {
            return NavigationFragment()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        showContentView()
        initView()
    }

    private fun initView() {
        swipeLayout.setOnRefreshListener {
            loadData()
        }
        swipeLayout.isRefreshing = true
        val layoutManager = LinearLayoutManager(activity)
        recyclerView.layoutManager = layoutManager
        recyclerView.setPullRefreshEnabled(false)
        recyclerView.setLoadingMoreEnabled(false)
        recyclerView.clearHeader()
        recyclerView.adapter = mAdapter
        recyclerView.setLoadingListener(object : XRecyclerView.LoadingListener {
            override fun onLoadMore() {
                getTreeList()
            }

            override fun onRefresh() {
            }
        })
        viewModel.loadStatus.observe(this, Observer {

            when (it?.status) {
                Status.ERROR -> showError()
            }
        })
    }

    override fun onRetry() {
        loadData()
    }

    override fun loadData() {
        getTreeList()
    }

    private fun getTreeList() {
        this.viewModel.getVavigations().observe(this@NavigationFragment, Observer {
            swipeLayout.isRefreshing = false
            recyclerView.refreshComplete()
            it?.let { list -> mAdapter.addDataAll(it) }
            mAdapter.notifyDataSetChanged()
        })
    }

}