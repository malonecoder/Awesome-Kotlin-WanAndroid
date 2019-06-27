package com.lxm.module_library.utils

import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.ViewModel
import com.lxm.module_library.base.NoViewModel
import java.lang.reflect.ParameterizedType


object ClassUtil {

    /**
     * 获取泛型ViewModel的class对象
     */
    fun <T> getViewModel(obj: Any): Class<T>? {
        val currentClass = obj.javaClass
        val tClass = getGenericClass<T>(currentClass, ViewModel::class.java)
        return if (tClass == null || tClass == AndroidViewModel::class.java || tClass == NoViewModel::class.java) {
            null
        } else tClass
    }

    private fun <T> getGenericClass(klass: Class<*>, filterClass: Class<*>): Class<T>? {
        val type = klass.genericSuperclass
        if (type == null || type !is ParameterizedType) return null
        val types = type.actualTypeArguments
        for (t in types) {
            val tClass = t as Class<T>
            if (filterClass.isAssignableFrom(tClass)) {
                return tClass
            }
        }
        return null
    }
}
