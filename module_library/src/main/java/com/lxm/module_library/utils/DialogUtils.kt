package com.lxm.module_library.utils

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.support.v7.app.AlertDialog


/**
 * Created by Horrarndoo on 2017/8/31.
 *
 * 对话框工具类, 提供常用对话框显示, 使用support.v7包内的AlertDialog样式
 */
object DialogUtils {


    fun showCommonDialog(context: Context, message: String, positiveText: String,
                         negativeText: String, listener: DialogInterface.OnClickListener): Dialog {
        return AlertDialog.Builder(context)
                .setMessage(message)
                .setPositiveButton(positiveText, listener)
                .setNegativeButton(negativeText, null)
                .show()
    }

    fun showConfirmDialog(context: Context, message: String, positiveText: String,
                          listener: DialogInterface.OnClickListener): Dialog {
        return AlertDialog.Builder(context)
                .setMessage(message)
                .setPositiveButton(positiveText, listener)
                .show()
    }
}
