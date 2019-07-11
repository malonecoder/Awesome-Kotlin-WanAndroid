package com.lxm.wanandroid.ui

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import com.lxm.module_library.base.BaseFragment
import com.lxm.module_library.xrecycleview.XRecyclerView
import com.lxm.wanandroid.R
import com.lxm.wanandroid.repository.model.Status
import com.lxm.wanandroid.repository.model.Welfare
import com.lxm.wanandroid.ui.adapter.WelfareAdapter
import com.lxm.wanandroid.ui.base.OnItemClickListener
import com.lxm.wanandroid.viewmodel.WelfareModelView
import kotlinx.android.synthetic.main.article_fragment.*

class WelfareFragment : BaseFragment<WelfareModelView>() {

    var imageList: ArrayList<String> = ArrayList()
    override fun getLayoutID() = R.layout.article_fragment

    private val mAdapter: WelfareAdapter by lazy {
        WelfareAdapter()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val ll = inflater.inflate(com.lxm.module_library.R.layout.fragment_base, null)
        contentView = LayoutInflater.from(activity).inflate(R.layout.article_fragment, null, false)
        val params =
            RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        contentView.layoutParams = params
        val mContainer = ll.findViewById<RelativeLayout>(com.lxm.module_library.R.id.container)
        mContainer.addView(contentView)
        return ll
    }

    companion object {
        fun getInstance(): WelfareFragment {
            return WelfareFragment()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        showContentView()
        initView()
    }

    private fun initView() {
        swipeLayout.setOnRefreshListener {
            viewModel.mPage = 1
            loadData()
        }
        swipeLayout.isRefreshing = true
        recyclerView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        recyclerView.setPullRefreshEnabled(false)
        recyclerView.clearHeader()
        recyclerView.adapter = mAdapter
        recyclerView.setLoadingListener(object : XRecyclerView.LoadingListener {
            override fun onLoadMore() {
                viewModel.mPage = viewModel.mPage+1
                getWelfare()
            }

            override fun onRefresh() {
            }
        })
        viewModel.loadStatus.observe(this, Observer {

            when (it?.status) {
                Status.ERROR -> showError()
            }
        })

        mAdapter.setOnItemClickListener(object : OnItemClickListener<Welfare> {
            override fun onClick(t: Welfare, position: Int) {

                activity?.let { ViewBigImageActivity.startImageList(it, position, imageList) }
            }

        })
    }

    override fun onRetry() {
        loadData()
    }

    override fun loadData() {
        getWelfare()
    }

    private fun getWelfare() {
        this.viewModel.getWelfare().observe(this@WelfareFragment, Observer {
            if (viewModel.mPage == 1) {
                mAdapter.setData(it?.results)
                imageList.clear()
                for (item in it?.results!!) {
                    imageList.add(item.url)
                }
            } else {
                mAdapter.addDataAll(it?.results!!)
                for (item in it?.results!!) {
                    imageList.add(item.url)
                }
            }
            swipeLayout.isRefreshing = false
            recyclerView.refreshComplete()
            mAdapter.notifyDataSetChanged()
        })
    }

}