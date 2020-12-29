package com.pluu.sample.coroutinektx

import android.app.Application
import com.pluu.util.registerLifecycleCallbacks
import timber.log.Timber

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        registerLifecycleCallbacks(this)
    }
}