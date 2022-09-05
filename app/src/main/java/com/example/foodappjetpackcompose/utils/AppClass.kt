package com.example.foodappjetpackcompose.utils

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import timber.log.Timber.Forest.plant


@HiltAndroidApp
class AppClass : Application() {

    override fun onCreate() {
        super.onCreate()
        // initialize timber in application class
        plant(Timber.DebugTree())
    }
}