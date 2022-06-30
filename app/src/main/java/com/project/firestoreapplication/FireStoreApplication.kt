package com.project.firestoreapplication

import android.app.Application
import com.google.firebase.FirebaseApp

class FireStoreApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(applicationContext)
    }
}