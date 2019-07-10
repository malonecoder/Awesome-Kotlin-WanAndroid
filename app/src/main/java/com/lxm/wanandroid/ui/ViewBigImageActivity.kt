package com.lxm.wanandroid.ui

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager.OnPageChangeListener
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.lxm.wanandroid.R
import kotlinx.android.synthetic.main.activity_show_image.*
import uk.co.senab.photoview.PhotoView
import uk.co.senab.photoview.PhotoViewAttacher

import java.util.ArrayList


class ViewBigImageActivity : FragmentActivity(), OnPageChangeListener, PhotoViewAttacher.OnPhotoTapListener {

    override fun onPhotoTap(p0: View?, p1: Float, p2: Float) {
        finish()
    }

    /**
     * 图片集合
     */
    private var imageList: MutableList<String>? = null
    /**
     * 接收穿过来当前选择的图片的position
     */
    internal var position: Int = 0
    /**
     * 当前页数
     */
    private var page: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_image)
        getIntentData()
    }

    private fun getIntentData() {
        val bundle = intent.extras
        if (bundle != null) {
            position = bundle.getInt("code")
            imageList = bundle.getStringArrayList("imageList")
        }

        val adapter = ViewPagerAdapter()
        viewpager?.adapter = adapter
        viewpager?.currentItem = position
        page = position
        viewpager?.addOnPageChangeListener(this)
        viewpager?.isEnabled = false

        viewpager_text?.text = (position + 1).toString() + " / " + imageList!!.size
    }

    /**
     * ViewPager的适配器
     */
    private inner class ViewPagerAdapter internal constructor() : PagerAdapter() {

        private val inflater: LayoutInflater = layoutInflater

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            val view = inflater.inflate(R.layout.item_image_view_pager, container, false)
            val zoomImageView = view.findViewById<PhotoView>(R.id.zoom_image_view)
            val progressBar = view.findViewById<ProgressBar>(R.id.loading)
            // 保存网络图片的路径
            val adapterImageEntity = getItem(position) as String
            val imageUrl: String
            imageUrl = adapterImageEntity
            progressBar.visibility = View.VISIBLE
            progressBar.isClickable = false

            Glide.with(this@ViewBigImageActivity).load(imageUrl)
                .transition(DrawableTransitionOptions.withCrossFade(700))
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any,
                        target: Target<Drawable>,
                        isFirstResource: Boolean
                    ): Boolean {
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable,
                        model: Any,
                        target: Target<Drawable>,
                        dataSource: DataSource,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBar.visibility = View.GONE
                        /**这里应该是加载成功后图片的高,最大为屏幕的高 */
                        val height = resource.intrinsicHeight
                        val wHeight = windowManager.defaultDisplay.height
                        if (height < wHeight) {
                            zoomImageView.scaleType = ImageView.ScaleType.FIT_CENTER
                        } else {
                            zoomImageView.scaleType = ImageView.ScaleType.CENTER_CROP
                        }
                        return false
                    }
                }).into(zoomImageView)

            zoomImageView.setOnPhotoTapListener(this@ViewBigImageActivity)
            container.addView(view, 0)
            return view
        }

        override fun getCount(): Int {
            return if (imageList == null || imageList?.size == 0) {
                0
            } else imageList!!.size
        }

        override fun isViewFromObject(arg0: View, arg1: Any): Boolean {
            return arg0 === arg1
        }

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            val view = `object` as View
            container.removeView(view)
        }

        private fun getItem(position: Int): Any {
            return imageList!![position]
        }
    }

    /**
     * 下面是对Viewpager的监听
     */
    override fun onPageScrollStateChanged(arg0: Int) {}

    override fun onPageScrolled(arg0: Int, arg1: Float, arg2: Int) {}

    /**
     * 本方法主要监听viewpager滑动的时候的操作
     * 每当页数发生改变时重新设定一遍当前的页数和总页数
     */
    override fun onPageSelected(arg0: Int) {
        viewpager_text?.text = (arg0 + 1).toString() + " / " + imageList!!.size
        page = arg0
    }

    override fun onDestroy() {
        imageList?.clear()
        super.onDestroy()
    }

    companion object {

        /**
         * @param position    大图的话是第几张图片 从0开始
         * @param imageList   图片集合
         */
        fun startImageList(
            context: Context,
            position: Int,
            imageList: ArrayList<String>
        ) {
            val bundle = Bundle()
            bundle.putInt("code", position)
            bundle.putStringArrayList("imageList", imageList)
            val intent = Intent(context, ViewBigImageActivity::class.java)
            intent.putExtras(bundle)
            context.startActivity(intent)
        }
    }
}
