package com.example.demoweatherapp.di

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.example.demoweatherapp.BaseApp

object AppInjector {
    fun init(app : BaseApp){
        DaggerAppComponent.builder()
                .application(app) // AppModule bindsInstance of App
                .build()
                .inject(app)
        app.registerActivityLifecycleCallbacks(object : Application.ActivityLifecycleCallbacks{
            override fun onActivityCreated(p0: Activity?, p1: Bundle?) {
            }

            override fun onActivityStarted(p0: Activity?) {
            }

            override fun onActivityResumed(p0: Activity?) {
            }

            override fun onActivityPaused(p0: Activity?) {
            }

            override fun onActivityStopped(p0: Activity?) {
            }

            override fun onActivitySaveInstanceState(p0: Activity?, p1: Bundle?) {
            }

            override fun onActivityDestroyed(p0: Activity?) {
            }

        })
    }
}