package com.example.myecomartapp

import android.app.Application
import com.razorpay.Checkout
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyEcomApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Checkout.preload(applicationContext)
    }
}