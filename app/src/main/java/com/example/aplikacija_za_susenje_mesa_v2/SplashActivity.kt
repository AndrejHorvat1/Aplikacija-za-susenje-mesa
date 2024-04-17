package com.example.aplikacija_za_susenje_mesa_v2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SplashActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val handler= Handler(Looper.getMainLooper())
        handler.postDelayed({
            //animacija load kod kod cekanaja logina
            val user=Firebase.auth.currentUser
            if(user==null){
                val intent=Intent(this@SplashActivity, LoginActivity::class.java)
                startActivity(Intent(this@SplashActivity, LoginActivity::class.java))

            }
            else {
                val intent = Intent(this@SplashActivity, MainActivity::class.java)
                startActivity(Intent(this@SplashActivity, MainActivity::class.java))

            }
            finish()
        },1000)
    }
}