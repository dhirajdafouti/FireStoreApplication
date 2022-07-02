package com.project.firestoreapplication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.project.firestoreapplication.Constant.EMAIL_ID
import com.project.firestoreapplication.Constant.PHONE_NUMBER
import com.project.firestoreapplication.Constant.USERNAME
import com.project.firestoreapplication.Constant.USER_CREATED_TIME_STAMP


class CreateUserActivity : AppCompatActivity() {
    lateinit var userName: EditText
    lateinit var auth: FirebaseAuth
    lateinit var passWord: EditText
    lateinit var userEmail: EditText
    lateinit var cancelButton: Button
    lateinit var createUserButton: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_user)
        userEmail = findViewById(R.id.createEmailTxt)
        passWord = findViewById(R.id.createPasswordTxt)
        userName = findViewById(R.id.createUsernameTxt)
        cancelButton = findViewById(R.id.cancel_button)
        createUserButton = findViewById(R.id.createCreateBtn)
        auth = FirebaseAuth.getInstance()
        cancelButton.setOnClickListener {
            cancelUser()
        }
        createUserButton.setOnClickListener {
            createUserButtonAction()
        }
    }

    private fun createUserButtonAction() {
        val emailText = userEmail.text.toString()
        val password = passWord.text.toString()
        val userName = userName.text.toString()

        auth.createUserWithEmailAndPassword(emailText, password).addOnSuccessListener { result ->
            result.user?.sendEmailVerification()?.addOnSuccessListener {
                Toast.makeText(this, "Verification Email Has been Sent.", Toast.LENGTH_SHORT)
                    .show();
            }?.addOnFailureListener { exception ->
                Log.d(TAG, "onFailure: Email not sent " + exception.localizedMessage);
            }

            val changeRequest = UserProfileChangeRequest.Builder().setDisplayName(userName).build()
            result.user?.updateProfile(changeRequest)?.addOnFailureListener { exception ->
                Log.d(TAG, "onFailure: Could Not Update Profile " + exception.localizedMessage);
            }
            val data = HashMap<String, Any>()
            data.put(USERNAME, userName)
            data.put(EMAIL_ID, emailText)
            data.put(PHONE_NUMBER, "76766727665")
            data.put(USER_CREATED_TIME_STAMP, FieldValue.serverTimestamp())

            FirebaseFirestore.getInstance().collection(Constant.USER).document(result.user!!.uid)
                .set(data)
                .addOnSuccessListener {
                    Toast.makeText(this, "The data is updated!!! SuccessFully.", Toast.LENGTH_SHORT)
                        .show();
                    finish()
                }.addOnFailureListener { exception ->
                    Log.d(TAG,
                        "onFailure:Could not add user document " + exception.localizedMessage);
                }

        }.addOnFailureListener {
            Toast.makeText(this, "Error in Creating User", Toast.LENGTH_SHORT).show()
        }
    }

    private fun cancelUser() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    companion object {
        var TAG: String = CreateUserActivity::class.java.simpleName
    }
}