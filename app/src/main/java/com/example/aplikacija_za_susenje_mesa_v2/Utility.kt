package com.example.aplikacija_za_susenje_mesa_v2

import android.content.Context
import android.widget.Toast
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

object Utility {
    @JvmStatic
    fun showToast(context: Context?, message: String?) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
    //id proizvoda
    @JvmStatic
    val collectionReferenceForNotes: CollectionReference
        get() {
            val currentUSer = FirebaseAuth.getInstance().currentUser
            return FirebaseFirestore.getInstance().collection("notes")
                .document(currentUSer!!.uid).collection("my notes")
        }
}