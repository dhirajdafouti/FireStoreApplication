package com.project.realdatabaseapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.security.spec.ECField

class RealDatabase : AppCompatActivity() {

    private lateinit var user: EditText
    private lateinit var id: EditText
    private lateinit var startname: EditText
    private lateinit var age: EditText
    private lateinit var button: Button
    private lateinit var database: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_real_database)
        user = findViewById(R.id.textView)
        id = findViewById(R.id.textView2)
        startname = findViewById(R.id.textView3)
        age = findViewById(R.id.textView4)
        button = findViewById(R.id.button2)
        database = FirebaseDatabase.getInstance().reference.child("User")
        button.setOnClickListener {
            updateRealDataBase()
        }

    }

    private fun updateRealDataBase() {
        val age1: String = age.text.toString()
        val user: String = user.text.toString()
        val id: String = id.text.toString()
        val startName: String = startname.text.toString()
        val user1 = User(user, id, startName, age1)
        for (i in 1..3) {
            database.child(id).setValue(startName+age1+id)
        }
    }
}