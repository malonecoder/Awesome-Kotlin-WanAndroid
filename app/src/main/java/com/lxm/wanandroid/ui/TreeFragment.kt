package com.lxm.wanandroid.ui

import android.arch.lifecycle.Observer
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.lxm.module_library.base.BaseFragment
import com.lxm.module_library.xrecycleview.XRecyclerView
import com.lxm.wanandroid.R
import com.lxm.wanandroid.repository.model.Status
import com.lxm.wanandroid.repository.model.TreeBean
import com.lxm.wanandroid.ui.adapter.TreeAdapter
import com.lxm.wanandroid.viewmodel.TreeViewModel
import com.zhy.view.flowlayout.FlowLayout
import kotlinx.android.synthetic.main.article_fragment.*

class TreeFragment : BaseFragment<TreeViewModel>() {

    private val mAdapter: TreeAdapter by lazy {
        TreeAdapter(itemClickListener)
    }
    private val itemClickListener: TreeAdapter.OnItemNavigationClickListener = object :
        TreeAdapter.OnItemNavigationClickListener {

        override fun itemClick(view: View, position: Int, parent: FlowLayout, children: List<TreeBean>) {
            val intent = Intent(parent.context, TagArticleActivity::class.java)
            intent.putExtra("TagBean", children[position])
            parent.context.startActivity(intent)
        }
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
            it?.let { list -> mAdapter.setData(list) }
            mAdapter.notifyDataSetChanged()
        })
    }

}