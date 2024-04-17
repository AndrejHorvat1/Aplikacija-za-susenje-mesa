package com.example.aplikacija_za_susenje_mesa_v2

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.aplikacija_za_susenje_mesa_v2.NoteAdapter.NoteViewHolder
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions

class NoteAdapter(options: FirestoreRecyclerOptions<Note?>, var context: Context) :
    FirestoreRecyclerAdapter<Note, NoteViewHolder>(options) {
    val note:Note? =null
    override fun onBindViewHolder(holder: NoteViewHolder, position: Int, note: Note) {
        holder.titleTextView.text = note.title
        holder.smokesTextView.text = note.numberOfSmokes
        holder.firstSmokeTextView.text = note.firstSmoke
        holder.lastSmokeTextView.text = note.lastSmoke

        //postavljanje vrijednosti iz baze za prikaz
        holder.itemView.setOnClickListener { v ->
            val intent = Intent(context, DetailsActivity::class.java)
            intent.putExtra("title", note.title)
            intent.putExtra("number of smokes", note.numberOfSmokes)
            intent.putExtra("first smoke", note.firstSmoke)
            intent.putExtra("last smoke", note.lastSmoke)
            val docId = this.snapshots.getSnapshot(position).id
            intent.putExtra("docId", docId)
            context.startActivity(intent)
        }


    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.recycler_item, parent, false)
        return NoteViewHolder(view)
    }

    inner class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var titleTextView: TextView
        var smokesTextView: TextView
        var lastSmokeTextView: TextView
        var firstSmokeTextView: TextView


        init {
            titleTextView = itemView.findViewById(R.id.title_text_view)
            smokesTextView = itemView.findViewById(R.id.smokes_text_view)
            lastSmokeTextView = itemView.findViewById(R.id.last_smoke_text_view)
            firstSmokeTextView = itemView.findViewById(R.id.first_smoke_text_view)

        }
    }
}