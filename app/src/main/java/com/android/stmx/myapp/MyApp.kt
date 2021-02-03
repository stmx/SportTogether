package com.android.stmx.myapp

import android.app.Application
import com.android.stmx.myapp.di.AppComponent
import com.android.stmx.myapp.di.module.AppModule
import com.android.stmx.myapp.di.DaggerAppComponent

class MyApp:Application() {
    //comment
    companion object{
        lateinit var appComponent:AppComponent
    }
    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder().appModule(AppModule(applicationContext)).build()
    }
}