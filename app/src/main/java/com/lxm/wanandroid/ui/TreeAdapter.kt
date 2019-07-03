package com.lxm.wanandroid.ui

import android.support.design.internal.FlowLayout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.lxm.wanandroid.R
import com.lxm.wanandroid.repository.model.TreeBean
import com.lxm.wanandroid.ui.base.BaseRecyclerAdapter
import com.lxm.wanandroid.ui.base.BaseRecyclerViewHolder


class TreeAdapter : BaseRecyclerAdapter<TreeBean>() {
    override fun getItemLayout(): Int {
        return R.layout.item_tree
    }

    override fun onBindViewHoder(holder: BaseRecyclerViewHolder, position: Int) {
        val treeBean = mutableList.get(position)
        val viewHoder: ViewHolder = holder as ViewHolder
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseRecyclerViewHolder {
        val item = LayoutInflater.from(parent.context).inflate(R.layout.item_tree, parent, false)
        return ViewHolder(item)
    }


    class ViewHolder(itemView: View) : BaseRecyclerViewHolder(itemView) {
        var titleView: TextView? = null
        var flowLayout: FlowLayout? = null

        init {
            titleView = itemView.findViewById<TextView>(R.id.tv_tree_title)
            flowLayout = itemView.findViewById(R.id.fl_tree)
        }
        companion object {

        }
    }

}