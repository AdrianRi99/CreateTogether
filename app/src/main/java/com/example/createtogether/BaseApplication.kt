package com.example.createtogether

import android.app.Application
import com.example.createtogether.utility.UserUtil
import dagger.hilt.android.HiltAndroidApp

//needed to tell App , it should use Hilt for Dependency Injection
//after rebuilding app, many automated files are created
@HiltAndroidApp
class BaseApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        UserUtil.setDefaultUserName(this)
    }
}