package com.project.firestoreapplication

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ToggleButton
import androidx.appcompat.app.AppCompatActivity

class ChatActivity : AppCompatActivity() {
    lateinit var t1: ToggleButton
    lateinit var t2: ToggleButton
    lateinit var t3: ToggleButton
    lateinit var t4: Button
    lateinit var e1: EditText
    lateinit var e2: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_thought)
        t1 = findViewById(R.id.addCrazyBtn)
        t2 = findViewById(R.id.addFunnyBtn)
        t3 = findViewById(R.id.addSeriousBtn)
        t4 = findViewById(R.id.addPostBtn)
        e1 = findViewById(R.id.addThoughtTxt)
        e2 = findViewById(R.id.addUsernameTxt)
        handleClickListener()
    }


    private fun handleClickListener() {
        t1.setOnClickListener {

        }
        t2.setOnClickListener {

        }
        t3.setOnClickListener { }

        t4.setOnClickListener {
            //add to the firebase project.
        }
    }

    companion object {
        const val THOUGHTS_REF = "thoughts"
        const val FUNNY = "funny"
        const val SERIOUS = "serious"
        const val CRAZY = "crazy"
        const val POPULAR = "popular"
        const val CATEGORY = "category"
        const val NUM_COMMENTS = "numComments"
        const val NUM_LIKES = "numLikes"
        const val THOUGHT_TXT = "thoughtTxt"
        const val TIMESTAMP = "timestamp"
        const val USERNAME = "username"
    }
}