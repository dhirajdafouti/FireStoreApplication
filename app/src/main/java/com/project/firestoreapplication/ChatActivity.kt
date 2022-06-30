package com.project.firestoreapplication

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import android.widget.ToggleButton
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.project.firestoreapplication.Constant.CATEGORY
import com.project.firestoreapplication.Constant.CRAZY
import com.project.firestoreapplication.Constant.FUNNY
import com.project.firestoreapplication.Constant.NUM_COMMENTS
import com.project.firestoreapplication.Constant.NUM_LIKES
import com.project.firestoreapplication.Constant.SERIOUS
import com.project.firestoreapplication.Constant.THOUGHTS_REF
import com.project.firestoreapplication.Constant.THOUGHT_TXT
import com.project.firestoreapplication.Constant.TIMESTAMP
import com.project.firestoreapplication.Constant.USERNAME

class ChatActivity : AppCompatActivity() {

    var selectedCategory = FUNNY
    lateinit var firebaseFirestore:FirebaseFirestore
    lateinit var addCrazyButton: ToggleButton
    lateinit var addFunnyButton: ToggleButton
    lateinit var addSeriousButton: ToggleButton
    lateinit var clickPostBtn: Button
    lateinit var addthoughttext: EditText
    lateinit var addUserNameText: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_thought)
        addCrazyButton = findViewById(R.id.addCrazyBtn)
        addFunnyButton = findViewById(R.id.addFunnyBtn)
        addSeriousButton = findViewById(R.id.addSeriousBtn)
        clickPostBtn = findViewById(R.id.addPostBtn)
        addthoughttext = findViewById(R.id.addThoughtTxt)
        addUserNameText = findViewById(R.id.addUsernameTxt)
        firebaseFirestore=FirebaseFirestore.getInstance()
        handleClickListener()
    }


    private fun handleClickListener() {
        addCrazyButton.setOnClickListener {
            if (selectedCategory == CRAZY) {
                addCrazyButton.isChecked = true
                return@setOnClickListener
            }
            addFunnyButton.isChecked = false
            addSeriousButton.isChecked = false
            selectedCategory = CRAZY
        }
        addFunnyButton.setOnClickListener {
            if (selectedCategory == FUNNY) {
                addSeriousButton.isChecked = true
                return@setOnClickListener
            }
            addCrazyButton.isChecked = false
            addSeriousButton.isChecked = false
            selectedCategory = FUNNY
        }
        addSeriousButton.setOnClickListener {
            if (selectedCategory == SERIOUS) {
                addSeriousButton.isChecked = true
                return@setOnClickListener
            }
            addFunnyButton.isChecked = false
            addCrazyButton.isChecked = false
            selectedCategory = SERIOUS
        }

        clickPostBtn.setOnClickListener {
            val data = HashMap<String, Any>()
            data.put(CATEGORY, selectedCategory)
            data.put(THOUGHT_TXT, addthoughttext.text.toString())
            data.put(USERNAME, addUserNameText.text.toString())
            data.put(TIMESTAMP, FieldValue.serverTimestamp())
            firebaseFirestore.collection(THOUGHTS_REF).add(data).addOnCompleteListener { success ->
                Toast.makeText(this, "The Add to FireBase Success $success", Toast.LENGTH_LONG)
                    .show()

            }.addOnFailureListener { failure ->
                Toast.makeText(this, "The Add to FireBase Failure $failure", Toast.LENGTH_LONG)
                    .show()
            }

        }
    }


}