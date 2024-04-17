package com.example.aplikacija_za_susenje_mesa_v2

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView


class InfoFragment2 : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_info2, container, false)

        val text=view.findViewById<TextView>(R.id.second_fragment_text)
        text.setText("Ovu aplikaciju napravio je Andrej Horvat u svrhu predaje porjekta iz kolekija ORWIM.")


        //gumbovi za navigaciju po fragmentima->fragment_info2.xml
        val buttonBack=view.findViewById<ImageButton>(R.id.back_btn_2)
            .setOnClickListener{
                val transaction=fragmentManager?.beginTransaction()
                transaction?.replace(R.id.activity, InfoFragment1())?.commit()
            }

        val buttonExit=view.findViewById<ImageButton>(R.id.close_fragment_btn)
            .setOnClickListener{
                val intent = Intent (getActivity(), MainActivity::class.java)
                getActivity()?.startActivity(intent)
            }

        return view
    }


}