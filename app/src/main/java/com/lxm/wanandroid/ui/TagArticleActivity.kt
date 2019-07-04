package com.lxm.wanandroid.ui

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.os.PersistableBundle
import com.lxm.module_library.base.BaseActivity
import com.lxm.module_library.utils.RefreshHelper
import com.lxm.module_library.xrecycleview.XRecyclerView
import com.lxm.wanandroid.R
import com.lxm.wanandroid.repository.model.ArticleBean
import com.lxm.wanandroid.repository.model.Status
import com.lxm.wanandroid.repository.model.TreeBean
import com.lxm.wanandroid.ui.base.OnItemClickListener
import com.lxm.wanandroid.utils.webview.WebViewActivity
import com.lxm.wanandroid.viewmodel.CategoryViewModel
import kotlinx.android.synthetic.main.article_fragment.*

class TagArticleActivity: BaseActivity<CategoryViewModel>() {
    lateinit var treeBean: TreeBean
    private val mAdapter: ArticleAdapter by lazy {
        ArticleAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.category_activity)
        initView()
        treeBean = intent.getSerializableExtra("TagBean") as TreeBean
        getCategory(treeBean.id)
    }


    private fun initView() {
        swipeLayout.setOnRefreshListener {
            viewModel.mPage = 0
            getCategory(treeBean.id)
        }
        swipeLayout.isRefreshing = true
        RefreshHelper.init(recyclerView, false)
        recyclerView.adapter = mAdapter
        recyclerView.setLoadingListener(object : XRecyclerView.LoadingListener {

            override fun onLoadMore() {
                viewModel.mPage = viewModel.mPage + 1;
                getCategory(treeBean.id)
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
            swipeLayout.isRefreshing = false
            recyclerView.refreshComplete()

            mAdapter.addDataAll(it.data?.datas!!)
            val positionStart = mAdapter.itemCount + 2
            mAdapter.notifyItemRangeInserted(positionStart, it.data?.datas?.size!!)
        })

        mAdapter.setOnItemClickListener(object : OnItemClickListener<ArticleBean> {
            override fun onClick(t: ArticleBean, position: Int) {
                WebViewActivity.loadUrl(this@TagArticleActivity, t.link, t.title)
            }
        })
    }

    private fun getCategory(id: Int) {
        viewModel.getCategory(id)
    }
}
