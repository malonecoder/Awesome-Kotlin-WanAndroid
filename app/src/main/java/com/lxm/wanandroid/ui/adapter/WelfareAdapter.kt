package com.lxm.wanandroid.ui.adapter


import android.widget.ImageView
import com.lxm.module_library.utils.DisplayUtils
import com.lxm.wanandroid.R
import com.lxm.wanandroid.repository.model.Welfare
import com.lxm.wanandroid.ui.base.BaseRecyclerAdapter
import com.lxm.wanandroid.ui.base.BaseRecyclerViewHolder
import com.lxm.wanandroid.utils.GlideUtil

class WelfareAdapter : BaseRecyclerAdapter<Welfare>() {

    override fun getItemLayout(): Int {
        return R.layout.item_view_welfare
    }

    override fun onBindViewHoder(holder: BaseRecyclerViewHolder, position: Int) {
        var welfare: Welfare = mutableList?.get(position)
        val imageView: ImageView = holder.getView(R.id.iv_welfare) as ImageView
        GlideUtil.displayCircleCorner(imageView,welfare.url)
    }
}