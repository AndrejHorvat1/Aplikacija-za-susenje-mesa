package com.example.aplikacija_za_susenje_mesa_v2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase

class CreateAccountActivity : AppCompatActivity() {
    private lateinit var firebaseAuth:FirebaseAuth
    var emailEditText: EditText? = null
    var passwordEditText: EditText? = null
    var confirmPasswordEditText: EditText? = null
    lateinit var createAccountBtn: Button
    var progressBar: ProgressBar? = null
    lateinit var loginBtnTextView: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_account)
        firebaseAuth=FirebaseAuth.getInstance()
        emailEditText = findViewById(R.id.email_edit_text)
        passwordEditText = findViewById(R.id.password_edit_text)
        confirmPasswordEditText = findViewById(R.id.confirm_password_edit_text)
        createAccountBtn = findViewById(R.id.create_account_btn)
        progressBar = findViewById(R.id.progress_bar)
        loginBtnTextView = findViewById(R.id.login_text_view_btn)

        //Gumb za kreiranje acc-a->activity_create_account_xml
        createAccountBtn.setOnClickListener { v: View? -> createAccount() }
        //Gumb za promjenu u login->activity_create_account_xml
        loginBtnTextView.setOnClickListener{ v: View? -> finish() }
    }

    fun createAccount() {
        val email = emailEditText!!.text.toString()
        val password = passwordEditText!!.text.toString()
        val confirmPassword = confirmPasswordEditText!!.text.toString()
        val isValidated = validateData(email, password, confirmPassword)
        if (!isValidated) {
            return
        }
        createAccountInFirebase(email, password)
    }

    fun createAccountInFirebase(email: String?, password: String?) {
        changeInProgress(true)

        firebaseAuth.createUserWithEmailAndPassword(email!!, password!!).addOnCompleteListener(
            this@CreateAccountActivity
        ) { task ->
            changeInProgress(false)
            if (task.isSuccessful) {
                Toast.makeText(
                    this@CreateAccountActivity,
                    "Successfully created an account, Check email to verify",
                    Toast.LENGTH_SHORT
                ).show()
                firebaseAuth.currentUser!!.sendEmailVerification()
                firebaseAuth.signOut()
                finish()
            } else {
                Toast.makeText(
                    this@CreateAccountActivity,
                    task.exception!!.localizedMessage,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    //Dok se pravi acc
    fun changeInProgress(inProgress: Boolean) {
        if (inProgress) {
            progressBar!!.visibility = View.VISIBLE
            createAccountBtn!!.visibility = View.GONE
        } else {
            progressBar!!.visibility = View.GONE
            createAccountBtn!!.visibility = View.VISIBLE
        }
    }

    fun validateData(email: String?, password: String, confirmPassword: String): Boolean {
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailEditText!!.error = "Email is invalid"
            return false
        }
        if (password.length < 6) {
            passwordEditText!!.error = "Password length is invalid"
            return false
        }
        if (password != confirmPassword) {
            confirmPasswordEditText!!.error = "Password not matched"
            return false
        }
        return true
    }
}
