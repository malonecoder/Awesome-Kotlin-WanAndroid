package com.lxm.module_library.utils

import android.app.Activity
import android.content.Context
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import com.lxm.module_library.global.GlobalApplication

/**
 * Created by Horrarndoo on 2017/8/31.
 *
 *
 * 显示相关工具类
 */
object DisplayUtils {

    /**
     * 将px值转换为dp值
     */
    fun px2dp(pxValue: Float, context: Context): Int {
        val scale = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, pxValue, context.resources.displayMetrics)

        return scale.toInt()
    }

    /**
     * 将dp值转换为px值
     */
    fun dp2px(dpValue: Float): Int {
        val scale = GlobalApplication.getInstance().getResources().getDisplayMetrics().density
        return (dpValue * scale + 0.5f).toInt()
    }

    /**
     * 将px值转换为sp值
     */
    fun px2sp(pxValue: Float, context: Context): Int {
        val scale = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, pxValue, context.resources.displayMetrics)
        return scale.toInt()
    }

    /**
     * 将sp值转换为px值
     */
    fun sp2px(spValue: Float, context: Context): Int {
        val scale = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spValue, context.resources.displayMetrics)
        return scale.toInt()
    }

    /**
     * 获取屏幕宽度
     */
    fun getScreenWidthPixels(context: Activity): Int {
        val metric = DisplayMetrics()
        context.windowManager.defaultDisplay.getMetrics(metric)
        return metric.widthPixels
    }

    /**
     * 获取屏幕高度
     */
    fun getScreenHeightPixels(context: Activity): Int {
        val metric = DisplayMetrics()
        context.windowManager.defaultDisplay.getMetrics(metric)
        return metric.heightPixels
    }

    /**
     * 设置某个View的margin
     *
     * @param view   需要设置的view
     * @param isDp   需要设置的数值是否为DP
     * @param left   左边距
     * @param right  右边距
     * @param top    上边距
     * @param bottom 下边距
     * @return
     */
    fun setViewMargin(
        view: View?,
        isDp: Boolean,
        left: Int,
        right: Int,
        top: Int,
        bottom: Int
    ): ViewGroup.LayoutParams? {
        if (view == null) {
            return null
        }

        var leftPx = left
        var rightPx = right
        var topPx = top
        var bottomPx = bottom
        val params = view.layoutParams
        var marginParams: ViewGroup.MarginLayoutParams? = null
        //获取view的margin设置参数
        if (params is ViewGroup.MarginLayoutParams) {
            marginParams = params
        } else {
            //不存在时创建一个新的参数
            marginParams = ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 250)
        }

        //根据DP与PX转换计算值
        if (isDp) {
            leftPx = dp2px(left.toFloat())
            rightPx = dp2px(right.toFloat())
            topPx = dp2px(top.toFloat())
            bottomPx = dp2px(bottom.toFloat())
        }
        //设置margin
        marginParams.setMargins(leftPx, topPx, rightPx, bottomPx)
        view.layoutParams = marginParams
        view.requestLayout()
        return marginParams
    }

}
