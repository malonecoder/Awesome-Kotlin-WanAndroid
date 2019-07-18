package com.lxm.wanandroid.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import com.jeremyliao.liveeventbus.LiveEventBus
import com.lxm.module_library.materialLogin.DefaultLoginView
import com.lxm.module_library.materialLogin.DefaultRegisterView
import com.lxm.module_library.materialLogin.MaterialLoginView
import com.lxm.module_library.statusbar.StatusBarUtil
import com.lxm.module_library.utils.PreferencesUtil
import com.lxm.module_library.utils.ToastUtils
import com.lxm.wanandroid.R
import com.lxm.wanandroid.repository.remote.LoginRepository
import com.lxm.wanandroid.viewmodel.LoginViewModel
import kotlinx.android.synthetic.main.main_content.*



class LoginActivity : AppCompatActivity() {

    companion object{
         const val LOGIN_SUCCESS = "loginSuccess"
    }

    private lateinit var model: LoginViewModel
    private lateinit var login: MaterialLoginView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initToolbar()
        initView()
        initData()
    }

    private fun initData() {
        model = ViewModelProviders.of(this@LoginActivity, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return LoginViewModel(LoginRepository()) as T
            }

        })[LoginViewModel::class.java]

        model.apply {
            loginStatus.observe(this@LoginActivity, Observer {
                if (it?.errorCode == 0) {
                    //存储账号和cookie等信息
                    ToastUtils.showToast("登录成功", Toast.LENGTH_LONG)
                    var isLogin: Boolean by PreferencesUtil<Boolean>("login", false)
                    var userName: String by PreferencesUtil<String>("userName", "Android")
                    var nikeName: String by PreferencesUtil<String>("nikeName", "易水寒")
                    isLogin = true
                    userName = it.data?.username!!
                    nikeName = it.data?.nickname!!

                    LiveEventBus.get()
                        .with(LOGIN_SUCCESS)
                        .post("登录成功")
                    finish()
                } else {
                    ToastUtils.showToast(it?.errorMsg!!, Toast.LENGTH_LONG)
                }
            })
            registerStauts.observe(this@LoginActivity, Observer {
                if (it?.errorCode == 0) {
                    login.animateLogin()
                    ToastUtils.showToast("注册成功，请登录", Toast.LENGTH_LONG)
                } else {
                    ToastUtils.showToast(it?.errorMsg!!, Toast.LENGTH_LONG)
                }
            })
        }
    }

    private fun initView() {
        login = findViewById<View>(R.id.login) as MaterialLoginView
        (login.loginView as DefaultLoginView).setListener { loginUser, loginPass ->
            if (!TextUtils.isEmpty(loginUser.editText?.text) && !TextUtils.isEmpty(loginPass.editText?.text)) {
                model.login(loginUser.editText?.text.toString(), loginPass.editText?.text.toString())
            } else {
                ToastUtils.showToast("用户名和密码不能为空", Toast.LENGTH_SHORT)
            }
        }

        (login.registerView as DefaultRegisterView).setListener { registerUser, registerPass, registerPassRep ->
            if (!TextUtils.isEmpty(registerUser.editText?.text) && !TextUtils.isEmpty(registerPass.editText?.text) &&
                !TextUtils.isEmpty( registerPassRep.editText?.text)) {
                    model.register(
                        registerUser.editText?.text.toString(),
                        registerPass.editText?.text.toString(),
                        registerPassRep.editText?.text.toString()
                    )
                } else {
                ToastUtils.showToast("用户名和密码不能为空", Toast.LENGTH_SHORT)
            }
        }
    }

    private fun initToolbar() {
        StatusBarUtil.setColor(this, resources.getColor(R.color.colorTheme), 0)
        toolbar.setNavigationOnClickListener {
            finish()
        }
    }
}

