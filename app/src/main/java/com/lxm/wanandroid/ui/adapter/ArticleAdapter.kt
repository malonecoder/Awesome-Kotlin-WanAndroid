package com.lxm.wanandroid.ui.adapter


import android.content.Intent
import android.graphics.Paint
import android.support.v4.content.ContextCompat.startActivity
import android.view.View
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import com.lxm.module_library.base.BaseViewModel
import com.lxm.module_library.utils.PreferencesUtil
import com.lxm.module_library.utils.ToastUtils
import com.lxm.wanandroid.R
import com.lxm.wanandroid.repository.model.ArticleBean
import com.lxm.wanandroid.repository.model.HttpResponse
import com.lxm.wanandroid.repository.remote.httpClient.RetrofitClient
import com.lxm.wanandroid.ui.LoginActivity
import com.lxm.wanandroid.ui.base.BaseRecyclerAdapter
import com.lxm.wanandroid.ui.base.BaseRecyclerViewHolder
import com.lxm.wanandroid.utils.webview.WebViewActivity
import com.lxm.wanandroid.viewmodel.CollectViewModel

class ArticleAdapter : BaseRecyclerAdapter<ArticleBean>() {
    var isCollectionList: Boolean = false
    private var collectionViewModel: CollectViewModel = CollectViewModel()
    override fun getItemLayout(): Int {
        return R.layout.item_view
    }

    override fun onBindViewHoder(holder: BaseRecyclerViewHolder, position: Int) {
        var articleBean: ArticleBean = mutableList?.get(position)
        holder.setValue(R.id.tv_title, articleBean.title)
        holder.setValue(R.id.tv_time, articleBean.niceDate)
        holder.setValue(R.id.tv_author, articleBean.author)
        holder.setValue(R.id.iv_image, articleBean.envelopePic)
        var tagTextView = holder.getView(R.id.tv_tag_name) as TextView
        tagTextView.text = articleBean.chapterName
        if (articleBean.tags != null && articleBean.tags?.size!! > 0) {
            tagTextView.paint.flags = Paint.UNDERLINE_TEXT_FLAG; //下划线
            tagTextView.paint.isAntiAlias = true;//抗锯齿
            tagTextView.setOnClickListener {
                WebViewActivity.loadUrl(
                    tagTextView.context,
                    RetrofitClient.WAN_BASE_URL + articleBean.tags?.get(0)?.url,
                    articleBean.chapterName
                )
            }
        }
        val isNewImageView: ImageView = holder.getView(R.id.iv_new) as ImageView
        if (articleBean.fresh) {
            isNewImageView.visibility = View.VISIBLE
        } else {
            isNewImageView.visibility = View.GONE
        }
        val isCollectImage: CheckBox = holder.getView(R.id.iv_collect) as CheckBox
        isCollectImage.isChecked = articleBean.collect
        if(isCollectionList){
            isCollectImage.isChecked = true
            articleBean.id = articleBean.originId!!
        }
        isCollectImage.setOnClickListener {
            var isLogin: Boolean by PreferencesUtil<Boolean>("login", false)
            if(isLogin){
                if (isCollectImage.isChecked) {
                    collectionViewModel.collect(articleBean.id, object : CollectionObserver<HttpResponse<Any>> {
                        override fun onChanged(t: HttpResponse<Any>?) {
                            if (t?.errorCode == 0) {
                                ToastUtils.showToast("收藏成功")
                            } else {
                                ToastUtils.showToast("收藏失败")
                            }
                        }
                    })
                } else {
                    collectionViewModel.unCollect(articleBean.id, object : CollectionObserver<HttpResponse<Any>> {
                        override fun onChanged(t: HttpResponse<Any>?) {
                            if (t?.errorCode == 0) {
                                ToastUtils.showToast("取消收藏成功")
                            } else {
                                ToastUtils.showToast("取消收藏失败")
                            }
                        }
                    })
                }
            }else{
                val intent = Intent(context, LoginActivity::class.java)
                context.startActivity(intent)
            }

        }
    }

    interface CollectionObserver<T> {
        fun onChanged(t: T?)
    }
}