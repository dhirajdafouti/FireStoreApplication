package com.project.firestoreapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val textView:TextView =findViewById(R.id.textId)
        textView.text =BuildConfig.BUILD_TYPE
    }
}