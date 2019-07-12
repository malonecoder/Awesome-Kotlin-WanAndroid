package com.lxm.wanandroid.ui.adapter


import android.graphics.Paint
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.lxm.wanandroid.R
import com.lxm.wanandroid.repository.model.ArticleBean
import com.lxm.wanandroid.repository.remote.httpClient.RetrofitClient
import com.lxm.wanandroid.ui.base.BaseRecyclerAdapter
import com.lxm.wanandroid.ui.base.BaseRecyclerViewHolder
import com.lxm.wanandroid.utils.webview.WebViewActivity

class ArticleAdapter : BaseRecyclerAdapter<ArticleBean>() {

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
        if(articleBean.tags!= null && articleBean.tags?.size!! >0){
            tagTextView.paint.flags = Paint.UNDERLINE_TEXT_FLAG; //下划线
            tagTextView.paint.isAntiAlias = true;//抗锯齿
            tagTextView.setOnClickListener{
                WebViewActivity.loadUrl(tagTextView.context, RetrofitClient.WAN_BASE_URL+ articleBean.tags?.get(0)?.url, articleBean.chapterName)

            }
        }
        val isNewImageView: ImageView = holder.getView(R.id.iv_new) as ImageView
        if (articleBean.fresh) {
            isNewImageView.visibility = View.VISIBLE
        } else {
            isNewImageView.visibility = View.GONE
        }
    }

    override fun getItemCount(): Int {
        return mutableList.size
    }
}