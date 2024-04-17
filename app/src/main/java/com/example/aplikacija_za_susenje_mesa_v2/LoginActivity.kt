package com.example.aplikacija_za_susenje_mesa_v2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.aplikacija_za_susenje_mesa_v2.R
import android.content.Intent
import android.util.Patterns
import android.view.View
import android.widget.*
import com.example.aplikacija_za_susenje_mesa_v2.CreateAccountActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.example.aplikacija_za_susenje_mesa_v2.MainActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {
    private lateinit var firebaseAuth:FirebaseAuth
    var emailEditText: EditText? = null
    var passwordEditText: EditText? = null
    lateinit var loginBtn: Button
    var progressBar: ProgressBar? = null
    lateinit var createAccountBtnTextView: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        emailEditText = findViewById(R.id.email_edit_text)
        passwordEditText = findViewById(R.id.password_edit_text)
        loginBtn = findViewById(R.id.login_btn)
        progressBar = findViewById(R.id.progress_bar)
        createAccountBtnTextView = findViewById(R.id.create_account_text_view_btn)

        //gumb za promjenu activitya u createacc->activity_login.xml
        loginBtn.setOnClickListener(View.OnClickListener { v: View? -> loginUser() })
        //gumb za kreiranje acc-a->activity_login.xml
        createAccountBtnTextView.setOnClickListener(View.OnClickListener { v: View? ->
            startActivity(
                Intent(this@LoginActivity, CreateAccountActivity::class.java)
            )
        })
    }

    fun loginUser() {
        val email = emailEditText!!.text.toString()
        val password = passwordEditText!!.text.toString()
        val isValidated = validateData(email, password)
        if (!isValidated) {
            return
        }
        loginAccountInFirebase(email, password)
    }

    fun loginAccountInFirebase(email: String?, password: String?) {

        changeInProgress(true)
        firebaseAuth=Firebase.auth
        firebaseAuth.signInWithEmailAndPassword(email!!, password!!).addOnCompleteListener(this
        ) { task ->
            changeInProgress(false)
            if (task.isSuccessful) {

                if (Firebase.auth.currentUser!!.isEmailVerified) {
                    startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                } else {
                    Toast.makeText(this@LoginActivity, "Email not verified, ", Toast.LENGTH_SHORT)
                        .show()
                }
            } else {
                Toast.makeText(
                    this@LoginActivity,
                    task.exception!!.localizedMessage,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    fun changeInProgress(inProgress: Boolean) {
        if (inProgress) {
            progressBar!!.visibility = View.VISIBLE
            loginBtn!!.visibility = View.GONE
        } else {
            progressBar!!.visibility = View.GONE
            loginBtn!!.visibility = View.VISIBLE
        }
    }

    fun validateData(email: String?, password: String): Boolean {
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailEditText!!.error = "Email is invalid"
            return false
        }
        if (password.length < 6) {
            passwordEditText!!.error = "Password length is invalid"
            return false
        }
        return true
    }
}