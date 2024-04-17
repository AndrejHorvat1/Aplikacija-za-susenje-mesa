package com.example.aplikacija_za_susenje_mesa_v2

import android.content.Intent
import android.media.Image
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction


class InfoFragment1 : Fragment(R.layout.activity_info) {




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)




    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_info1, container, false)

        val text=view.findViewById<TextView>(R.id.firs_fragment_text)

        text.setText("Aplikacija za sušenje mesa nudi Vam mogućnosti za izradu personaliziranog računa, dodavanje i uređivanje suhomesnatih proizvoda s informacijama poput imena, broja dimova, datum prvog dimljenja, datum zadnjeg dimljenja te brisanje pojedinog proizvoda.")

        //gumbovi za navigaciju po fragmentiam->fragment_info1.xml
        val buttonFoward=view.findViewById<ImageButton>(R.id.next_fragment_btn)
            .setOnClickListener {
                val transaction = fragmentManager?.beginTransaction()
                transaction?.replace(R.id.activity, InfoFragment2())?.commit()
            }
        val buttonBack=view.findViewById<ImageButton>(R.id.back_btn_1)
            .setOnClickListener{
                val intent = Intent (getActivity(), MainActivity::class.java)
                getActivity()?.startActivity(intent)
            }


        return  view
    }


}