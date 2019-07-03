package com.lxm.wanandroid.ui

import android.arch.lifecycle.Observer
import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
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
import com.lxm.wanandroid.repository.model.TreeBean
import com.lxm.wanandroid.ui.base.OnItemClickListener
import com.lxm.wanandroid.utils.GlideUtil
import com.lxm.wanandroid.utils.webview.WebViewActivity
import com.lxm.wanandroid.viewmodel.ArticleViewModel
import com.lxm.wanandroid.viewmodel.TreeViewModel
import com.zhouwei.mzbanner.holder.MZViewHolder
import kotlinx.android.synthetic.main.article_fragment.*

class TreeFragment : BaseFragment<TreeViewModel>() {

    private val mAdapter: TreeAdapter by lazy {
        TreeAdapter()
    }

    override fun getLayoutID(): Int {
        return R.layout.tree_fragment
    }

    companion object {
        fun getInstance(): TreeFragment {
            return TreeFragment()
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

        mAdapter.setOnItemClickListener(object : OnItemClickListener<TreeBean> {
            override fun onClick(t: TreeBean, position: Int) {
//                WebViewActivity.loadUrl(activity, t.link, t.title)
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
        this.viewModel.getTrees().observe(this@TreeFragment, Observer {
            swipeLayout.isRefreshing = false
            recyclerView.refreshComplete()
            it?.let { list -> mAdapter.addDataAll(list) }
            mAdapter.notifyDataSetChanged()
        })
    }
}