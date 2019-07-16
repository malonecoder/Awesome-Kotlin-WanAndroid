package com.lxm.module_library.utils

import android.support.v4.content.ContextCompat
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import com.lxm.module_library.R
import com.lxm.module_library.xrecycleview.XRecyclerView

object RefreshHelper {

    /**
     * 默认不显示最后一个item的分割线
     *
     * @param isShowFirstDivider  第一个item是否显示分割线
     * @param isShowSecondDivider 第二个item是否显示分割线
     */
    @JvmOverloads
    fun init(recyclerView: XRecyclerView, isShowFirstDivider: Boolean = true, isShowSecondDivider: Boolean = true) {
        recyclerView.layoutManager = LinearLayoutManager(recyclerView.context)
        recyclerView.setPullRefreshEnabled(false)
        recyclerView.clearHeader()
//        val itemDecoration = MyDividerItemDecoration(
//            recyclerView.context,
//            DividerItemDecoration.VERTICAL,
//            false,
//            isShowFirstDivider,
//            isShowSecondDivider
//        )
//        itemDecoration.setDrawable(ContextCompat.getDrawable(recyclerView.context, R.drawable.shape_line)!!)
//        recyclerView.addItemDecoration(itemDecoration)
    }
}

