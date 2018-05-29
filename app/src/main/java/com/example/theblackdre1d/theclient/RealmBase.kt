package com.example.theblackdre1d.theclient

import android.app.Application
import io.realm.Realm

class RealmBase : Application() {
    override fun onCreate() {
        super.onCreate()
        Realm.init(applicationContext)
    }
}