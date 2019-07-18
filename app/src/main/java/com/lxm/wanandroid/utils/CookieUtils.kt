package com.lxm.wanandroid.utils

import android.content.Context
import android.util.Log
import android.webkit.CookieManager
import android.webkit.CookieSyncManager
import com.google.android.gms.common.util.SharedPreferencesUtils
import okhttp3.Cookie


object CookieUtils {

    /**
     * clear Cookie
     *
     * @param context
     */
    fun clearCookie(context: Context) {
        CookieSyncManager.createInstance(context)
        CookieSyncManager.getInstance().startSync()
        CookieManager.getInstance().removeSessionCookie()
    }

    /**
     * Sync Cookie
     */
    fun syncCookie(context: Context, url: String,cookies:List<Cookie>) {
        try {
            CookieSyncManager.createInstance(context)
            val cookieManager = CookieManager.getInstance()
            cookies.forEach {
                val name = it.name()
                val value = it.value()
                val cookieStr = "$name=$value"
                cookieManager.setCookie(url,cookieStr)
            }
            cookieManager.flush()
        } catch (e: Exception) {
        }
    }
}
