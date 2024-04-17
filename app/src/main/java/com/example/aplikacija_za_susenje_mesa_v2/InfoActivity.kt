package com.example.aplikacija_za_susenje_mesa_v2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentManager

class InfoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)

        //prazna aktivnost otvara prvi fragment
       supportFragmentManager.beginTransaction().replace(R.id.activity, InfoFragment1()).commit()
    }
}