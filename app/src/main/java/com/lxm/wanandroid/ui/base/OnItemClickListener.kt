package com.lxm.wanandroid.ui.base


interface OnItemClickListener<T> {
    fun onClick(t: T, position: Int)
}
