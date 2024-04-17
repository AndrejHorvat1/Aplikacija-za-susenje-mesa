package com.example.aplikacija_za_susenje_mesa_v2

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.PopupMenu
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.aplikacija_za_susenje_mesa_v2.Utility.collectionReferenceForNotes
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.Query

class MainActivity : AppCompatActivity() {
    lateinit var addNoteBtn: FloatingActionButton

    var recyclerView: RecyclerView? = null
    lateinit var menuBtn: ImageButton
    lateinit var noteAdapter: NoteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView = findViewById(R.id.recycler_view)
        menuBtn = findViewById(R.id.menu_btn)
        setupRecyclerView()

        //dodavanje proizvoda
        addNoteBtn = findViewById<FloatingActionButton>(R.id.add_note_btn)
        //menu
        menuBtn.setOnClickListener(View.OnClickListener { v:View?->showMenu() })

        //iz main u details za unos ili edit proizvoda
        addNoteBtn.setOnClickListener(View.OnClickListener { v: View? ->
            startActivities(
                arrayOf(
                    Intent(
                        this@MainActivity,
                        DetailsActivity::class.java
                    )
                )
            )
        })
    }

    //prikaz recyclerItema u recyclerView
    fun setupRecyclerView() {
        val query = collectionReferenceForNotes.orderBy("lastSmoke", Query.Direction.ASCENDING)
        val options = FirestoreRecyclerOptions.Builder<Note>()
            .setQuery(query, Note::class.java).build()
        recyclerView!!.layoutManager = LinearLayoutManager(this)
        noteAdapter = NoteAdapter(options, this@MainActivity)
        recyclerView!!.adapter = noteAdapter
    }
    override fun onStart() {
        super.onStart()
        noteAdapter!!.startListening()
    }

    override fun onStop() {
        super.onStop()
        noteAdapter!!.stopListening()
    }

    override fun onResume() {
        super.onResume()
        noteAdapter!!.notifyDataSetChanged()
    }

    fun showMenu(){
        val popupMenu = PopupMenu(this@MainActivity, menuBtn)
        popupMenu.getMenu().add("Logout")
        popupMenu.getMenu().add("Info")
        popupMenu.show()
        popupMenu.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item ->
            //logout
            if (item.title === "Logout") {
                FirebaseAuth.getInstance().signOut()
                startActivity(Intent(this@MainActivity, LoginActivity::class.java))
                finish()
                return@OnMenuItemClickListener true
            }
            //info fragmneti
            if(item.title==="Info"){
                startActivity(Intent(this, InfoActivity::class.java))
                finish()
                return@OnMenuItemClickListener true
            }
            false
        })
    }


}