package com.doodle.todo

import android.app.Application
import com.doodle.todo.di.initKoin
import org.koin.android.ext.koin.androidContext

class KoinApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@KoinApplication)
        }
    }
}