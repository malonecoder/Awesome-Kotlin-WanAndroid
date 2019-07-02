package com.lxm.wanandroid.utils

import android.content.Context
import android.graphics.Bitmap
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.Transformation
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import jp.wasabeef.glide.transformations.BlurTransformation
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.load.resource.bitmap.RoundedCorners




object GlideUtil {

    private fun displayGaussian(context: Context, url: String, imageView: ImageView) {
        // "23":模糊度；"4":图片缩放4倍后再进行模糊
        Glide.with(context)
            .load(url)
            .transition(DrawableTransitionOptions.withCrossFade())
            .transition(DrawableTransitionOptions.withCrossFade(500))
            .transform(BlurTransformation(50, 8) as Transformation<Bitmap>)
            .into(imageView)
    }

    /**
     * 加载圆角图,暂时用到显示头像
     */
    fun displayCircle(imageView: ImageView, imageUrl: String) {
        Glide.with(imageView.context)
            .load(imageUrl)
            .transition(DrawableTransitionOptions.withCrossFade(500))
            .transform(CircleCrop())
            //                .apply(bitmapTransform(new CircleCrop()))
            //                .transform(new GlideCircleTransform())
            //                .transform(new RoundedCorners(20))
            //                .transform(new CenterCrop(), new RoundedCorners(20))
            .into(imageView)
    }

    fun displayImage(imageView: ImageView, imageUrl: String) {
        Glide.with(imageView.context)
            .load(imageUrl)
            .into(imageView)
    }
    fun displayCircleCorner(imageView: ImageView, imageUrl: String){
        //设置图片圆角角度
        val roundedCorners = RoundedCorners(6)
//通过RequestOptions扩展功能,override:采样率,因为ImageView就这么大,可以压缩图片,降低内存消耗
        val options = RequestOptions.bitmapTransform(roundedCorners)
        Glide.with(imageView.context).load(imageUrl).apply(options).into(imageView)
    }

}
