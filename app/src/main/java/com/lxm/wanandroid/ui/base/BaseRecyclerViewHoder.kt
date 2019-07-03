package com.lxm.wanandroid.ui.base

import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

open class BaseRecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val map = mutableMapOf<Int, View>()
    fun getView(id: Int): View? {
        var viewId = map[id]
        if (viewId == null) {
            viewId = itemView.findViewById(id)
            map[id] = viewId
        }
        return viewId
    }


    fun setValue(id: Int, string: String?) {
        val view = getView(id)

        when (view) {
            is TextView -> view.text = string
            is ImageView -> showPic(view, string)
        }
    }

    private fun showPic(view: ImageView, string: String?) {
        if (TextUtils.isEmpty(string)) {
            view.visibility = View.GONE
        } else {
            view.visibility = View.VISIBLE
            Glide.with(view.context)
                .load(string)
                .transition(DrawableTransitionOptions.withCrossFade(500))
                .into(view)
        }
    }

}
