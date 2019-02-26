package com.lxm.wanandroid

import com.goldze.base.config.ModuleLifecycleConfig
import me.goldze.mvvmhabit.base.BaseApplication

/**
 * Created by goldze on 2018/6/21 0021.
 */

class AppApplication : BaseApplication() {
    @Override
    fun onCreate() {
        super.onCreate()
        //初始化组件(靠前)
        ModuleLifecycleConfig.getInstance().initModuleAhead(this)
        //....
        //初始化组件(靠后)
        ModuleLifecycleConfig.getInstance().initModuleLow(this)
    }
}
