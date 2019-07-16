package com.lxm.wanandroid.ui.base

import android.content.Context
import android.support.annotation.IntRange
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup

abstract class BaseRecyclerAdapter<T> : RecyclerView.Adapter<BaseRecyclerViewHolder>() {

    lateinit var context: Context
    var layoutRes: Int = 0
    var mutableList: MutableList<T> = mutableListOf()
    var listener: OnItemClickListener<T>? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseRecyclerViewHolder {
        context = parent.context
        return BaseRecyclerViewHolder(
            LayoutInflater.from(context).inflate(
                getItemLayout(),
                null
            )
        )
    }


    fun setOnItemClickListener(listener: OnItemClickListener<T>) {
        this.listener = listener
    }

    override fun onBindViewHolder(holder: BaseRecyclerViewHolder, position: Int) {
        onBindViewHoder(holder, position)
        holder.itemView.setOnClickListener {
            listener?.onClick(mutableList.get(position),position)
        }
    }

    override fun getItemCount(): Int {
        return mutableList.size
    }


    fun setData(items: List<T>?) {
        this.mutableList = items as MutableList<T>
        notifyDataSetChanged()
    }

    fun addData(@IntRange(from = 0) position: Int, data: T) {
        mutableList.add(position, data)
        notifyItemInserted(position)
        compatibilityDataSizeChanged(1)
    }

    fun addData(data: T) {
        mutableList.add(data)
        notifyItemInserted(mutableList?.size)
    }

    fun addDataAll(data: List<T>) {
        mutableList.addAll(data)
        notifyItemInserted(mutableList?.size)
    }

    fun addAdd(data: T) {
        mutableList.add(data)
        notifyItemInserted(mutableList?.size)
    }


    fun remove(position: Int) {
        mutableList.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, mutableList!!.size - position)
    }

    private fun compatibilityDataSizeChanged(size: Int) {
        val dataSize = if (mutableList == null) 0 else mutableList!!.size
        if (dataSize == size) {
            notifyDataSetChanged()
        }
    }

    /**
     * 需要重写的方法
     * @param holder
     * @param position
     */
    abstract fun onBindViewHoder(holder: BaseRecyclerViewHolder, position: Int)

    abstract fun getItemLayout(): Int
}

