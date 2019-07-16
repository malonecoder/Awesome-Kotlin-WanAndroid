package com.lxm.wanandroid.viewmodel

import android.arch.lifecycle.Transformations
import com.lxm.module_library.base.BaseViewModel
import com.lxm.wanandroid.repository.remote.LoginRepository

class LoginViewModel(val loginRepository: LoginRepository) : BaseViewModel() {
     var loginStatus = Transformations.map(loginRepository.login){it}
     var registerStauts = Transformations.map(loginRepository.register){it}
    var logoutStatus = Transformations.map(loginRepository.logout){it}


    fun login(username:String,password:String) {
        loginRepository.login(username,password)
    }

    fun logout() {
        loginRepository.logout()
    }


    fun register(username:String,password:String,repassword:String) {
        loginRepository.register(username,password,repassword)
    }
}
