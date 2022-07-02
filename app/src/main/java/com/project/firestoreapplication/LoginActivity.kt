package com.project.firestoreapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    lateinit var createButton: Button
    lateinit var loginButton: Button
    lateinit var loginUserEmail: EditText
    lateinit var password: EditText
    lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        createButton = findViewById(R.id.createLogin)
        loginButton = findViewById(R.id.loginLoginBtn)
        loginUserEmail = findViewById(R.id.loginEmailTxt)
        password = findViewById(R.id.loginPasswordTxt)
        auth = FirebaseAuth.getInstance()
        loginButton.setOnClickListener {
            if (!loginUserEmail.toString().isEmpty() && !password.toString().isEmpty()) {
                loginAction()
            }
            createButton.setOnClickListener {
                createUserAction()
            }
        }

    }

    private fun loginAction() {
        auth.signInWithEmailAndPassword(loginUserEmail.toString(), password.toString())
            .addOnSuccessListener { result ->
                Toast.makeText(this, "Login SuccessFull", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener { result ->
                Toast.makeText(this, "Login UnsuccessFull .Please again!!!", Toast.LENGTH_SHORT)
                    .show()
            }
    }

    private fun createUserAction() {
        createButton.setOnClickListener {
            Toast.makeText(this, "Create User Activity", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, CreateUserActivity::class.java)
            startActivity(intent)

        }

    }
}