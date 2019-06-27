package com.lxm.module_library.helper.okhttp.cache;

import com.lxm.module_library.utils.AppUtils;
import okhttp3.Cache;

import java.io.File;

/**
 * Created by Horrarndoo on 2017/9/12.
 * <p>
 */
public class HttpCache {

    private static final int HTTP_RESPONSE_DISK_CACHE_MAX_SIZE = 50 * 1024 * 1024;

    public static Cache getCache() {
        return new Cache(new File(AppUtils.INSTANCE.getContext().getExternalCacheDir().getAbsolutePath() + File
                .separator + "data/NetCache"),
                HTTP_RESPONSE_DISK_CACHE_MAX_SIZE);
    }
}
