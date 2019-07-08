package com.lxm.wanandroid.ui.adapter

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.lxm.wanandroid.R
import com.lxm.wanandroid.repository.model.ArticleBean
import com.lxm.wanandroid.repository.model.Navigation
import com.lxm.wanandroid.repository.model.TreeBean
import com.lxm.wanandroid.ui.base.BaseRecyclerAdapter
import com.lxm.wanandroid.ui.base.BaseRecyclerViewHolder
import com.zhy.view.flowlayout.FlowLayout
import com.zhy.view.flowlayout.TagAdapter
import com.zhy.view.flowlayout.TagFlowLayout
import java.util.*


class NaviAdapter(var itemClickListener: OnItemNavigationClickListener) : BaseRecyclerAdapter<Navigation>() {
    override fun getItemLayout(): Int {
        return R.layout.item_tree
    }

    override fun onBindViewHoder(holder: BaseRecyclerViewHolder, position: Int) {
        val navigation = mutableList[position]
        val viewHolder: ViewHolder = holder as ViewHolder
        viewHolder.titleView?.text = navigation.name
        showTag(navigation, viewHolder.flowLayout)

    }

    private fun showTag(
        navigation: Navigation,
        flowLayout: TagFlowLayout?
    ) {
        flowLayout?.adapter = object : TagAdapter<ArticleBean>(navigation.articles) {
            override fun getView(
                parent: FlowLayout,
                position: Int,
                articleBean: ArticleBean
            ): View {
                val textView = View.inflate(parent.context, R.layout.item_tree_tag, null) as TextView
                textView.text = articleBean.title
//                textView.textColors = Color.rgb(Random().nextInt(255), Random().nextInt(255), Random().nextInt(255))
                return textView
            }
        }
        flowLayout?.setOnTagClickListener { view, position, parent ->
            itemClickListener?.itemClick(view, position, parent,navigation.articles)
            true
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseRecyclerViewHolder {
        val item = LayoutInflater.from(parent.context).inflate(R.layout.item_tree, parent, false)
        return ViewHolder(item)
    }


    class ViewHolder(itemView: View) : BaseRecyclerViewHolder(itemView) {
        var titleView: TextView? = null
        var flowLayout: TagFlowLayout? = null

        init {
            titleView = itemView.findViewById<TextView>(R.id.tv_tree_title)
            flowLayout = itemView.findViewById<TagFlowLayout>(R.id.fl_tree)
        }

        companion object
    }

    private fun getBackGround(): Drawable {
        val drawable = GradientDrawable()
        drawable.cornerRadius = 16f
        drawable.setColor(Color.rgb(Random().nextInt(255), Random().nextInt(255), Random().nextInt(255)))
        return drawable
    }

    interface OnItemNavigationClickListener {
        /**
         *单击事件
         */
        fun itemClick(view: View, position: Int, parent: FlowLayout, articles: List<ArticleBean>)
    }

}