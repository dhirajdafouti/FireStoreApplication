package com.project.realdatabaseapplication

import android.content.Intent
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.project.realdatabaseapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    //https://www.youtube.com/watch?v=wa8OrQ_e76M&list=PL5jb9EteFAOCO_uRl2--aQ-0d8r01QjS-
    //https://www.youtube.com/watch?v=oPH7rfJel9w
    //https://youtube.com/playlist?list=PLl-K7zZEsYLmgdxMEHar35Wo26fLWm9BI
    //https://youtube.com/playlist?list=PLQ9S01mirRdXlj1xr2fn0NjUwa9HWNPfm
    //https://youtube.com/playlist?list=PLrnPJCHvNZuAXdWxOzsN5rgG2M4uJ8bH1
    //https://youtube.com/playlist?list=PLj76U7gxVixR0ZDpZIpDG4mDo7fqIR2ec
    //https://youtube.com/playlist?list=PLj76U7gxVixQ1JcaOCA7U12SoS8l-21xU
    //https://youtube.com/playlist?list=PLhhNsarqV6MQ-eMvAOwjuBUDm7hfsTUta
    //https://youtube.com/playlist?list=PLrnPJCHvNZuB_7nB5QD-4bNg6tpdEUImQ
    //https://firebase.google.com/docs/auth/?hl=en&authuser=0
    //https://www.youtube.com/watch?v=hXuI0nLWKTE&list=PLYx38U7gxBf3pmsHVTUwRT_lGON6ZIBHi&index=5
    //https://www.youtube.com/playlist?list=PLYx38U7gxBf3pmsHVTUwRT_lGON6ZIBHi
    //https://firebase.google.com/support/guides/launch-checklist?authuser=0

    lateinit var loginButton: Button
    lateinit var loginUserEmail: EditText
    lateinit var password: EditText
    lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loginButton = findViewById(R.id.loginLoginBtn)
        loginUserEmail = findViewById(R.id.loginEmailTxt)
        password = findViewById(R.id.loginPasswordTxt)
        auth = FirebaseAuth.getInstance()
        loginButton.setOnClickListener {
            if (!loginUserEmail.toString().isEmpty() && !password.toString().isEmpty()) {
                loginAction()
            }

        }

    }

    private fun loginAction() {
        auth.signInWithEmailAndPassword(loginUserEmail.toString().trim(), password.toString().trim())
            .addOnSuccessListener { result ->
                Toast.makeText(this, "Login SuccessFull", Toast.LENGTH_SHORT).show()

            }.addOnFailureListener { result ->
                Toast.makeText(this, "Login UnsuccessFull .Please again!!!", Toast.LENGTH_SHORT)
                    .show()
            }
    }


}