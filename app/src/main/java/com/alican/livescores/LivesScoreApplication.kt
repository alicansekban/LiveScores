package com.alican.livescores

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class LivesScoreApplication : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}