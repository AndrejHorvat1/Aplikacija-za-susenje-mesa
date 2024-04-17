package com.example.aplikacija_za_susenje_mesa_v2

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.aplikacija_za_susenje_mesa_v2.Utility.collectionReferenceForNotes
import com.example.aplikacija_za_susenje_mesa_v2.Utility.showToast
import com.google.firebase.firestore.DocumentReference
import java.text.SimpleDateFormat
import java.util.*


class DetailsActivity : AppCompatActivity() {
   private  var titleEditText: EditText? = null
    var numberOfSmokesEditText: EditText? = null
    lateinit var firstSmokeEditText: EditText
    lateinit var lastSmokeEditText: EditText
    lateinit var saveNoteBtn: ImageButton
    lateinit var deleteNoteTextViewBtn:TextView

    var title: String  ?= null
    var numberOfSmokes:kotlin.String? = null
    var lastSmoke:kotlin.String? = null
    var firstSmoke:kotlin.String? = null
    var docId:String?= null
    var isEditMode:Boolean=false



    var pageTitleTextView:TextView?=null

    val myCalendar = Calendar.getInstance()


    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        titleEditText = findViewById(R.id.notes_title_text)
        numberOfSmokesEditText = findViewById(R.id.notes_number_text)
        firstSmokeEditText = findViewById(R.id.notes_first_smoke_date)
        lastSmokeEditText = findViewById(R.id.notes_last_smoke_date)
        saveNoteBtn = findViewById(R.id.save_note_btn)
        deleteNoteTextViewBtn=findViewById(R.id.delete_note_text_view_btn)

        pageTitleTextView=findViewById(R.id.page_Title)
        //recive data
        title=intent.getStringExtra("title");
        numberOfSmokes=intent.getStringExtra("number of smokes")
        lastSmoke=intent.getStringExtra("last smoke")
        firstSmoke=intent.getStringExtra("first smoke")
        docId=intent.getStringExtra("docId")

        //Provjera docId-a kako bi se znalo radili se o dodavanju novog proizvoda ili o uređivanju starog
        if(docId!=null&& !docId!!.isEmpty()){
            isEditMode=true
        }

        with(titleEditText) {
            this?.setText(title)
        }
        with(numberOfSmokesEditText) {
            this?.setText(numberOfSmokes)
        }
        with(firstSmokeEditText) {
            this?.setText(firstSmoke)
        }
        with(lastSmokeEditText) {
            this?.setText(lastSmoke)
        }

        //ako je edit mode umjesto dodaj proizvod pisati će uredi proizvod
        if(isEditMode){
            with(pageTitleTextView) { this?.setText("Uredi proizvod") }
            deleteNoteTextViewBtn.setVisibility(View.VISIBLE)
        }

        //gumbovi za spremanje i brisanje->activity_details.xml
        saveNoteBtn.setOnClickListener(View.OnClickListener { v: View? -> saveNote() })
        deleteNoteTextViewBtn.setOnClickListener(View.OnClickListener{v:View? -> deleteNoteFromFirebase() })

        //popup kalendar za datume
        firstSmokeEditText.transformIntoDatePicker(this, "dd.MM.yyyy")
        lastSmokeEditText.transformIntoDatePicker(this, "dd.MM.yyyy")
    }


    fun EditText.transformIntoDatePicker(context: Context, format: String, maxDate: Date? = null) {
        isFocusableInTouchMode = false
        isClickable = true
        isFocusable = false

        val myCalendar = Calendar.getInstance()
        val datePickerOnDataSetListener =
            DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                myCalendar.set(Calendar.YEAR, year)
                myCalendar.set(Calendar.MONTH, monthOfYear)
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                val sdf = SimpleDateFormat(format, Locale.UK)
                setText(sdf.format(myCalendar.time))
            }

        setOnClickListener {
            DatePickerDialog(
                context, datePickerOnDataSetListener, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)
            ).run {
                maxDate?.time?.also { datePicker.maxDate = it }
                show()
            }
        }
    }

    fun saveNote() {
        val noteTitle = titleEditText!!.text.toString()
        val noteSmokeNumber = numberOfSmokesEditText!!.text.toString()
        val firstSmoke = firstSmokeEditText!!.text.toString()
        val lastSmoke = lastSmokeEditText!!.text.toString()
        if (noteTitle == null || noteTitle.isEmpty()) {
            titleEditText!!.error = "Title is required"
            return
        }
        val note = Note()
        note.title = noteTitle
        note.numberOfSmokes = noteSmokeNumber
        note.firstSmoke = firstSmoke
        note.lastSmoke = lastSmoke

        saveNoteToFirebase(note)
    }

    fun saveNoteToFirebase(note: Note?) {
        val documentReference: DocumentReference
        if(isEditMode){
            documentReference= collectionReferenceForNotes.document(docId.toString())
        }
        else{
            documentReference = collectionReferenceForNotes.document()
        }

        documentReference.set(note!!).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                showToast(this@DetailsActivity, "Note added")
                finish()
            } else {
                showToast(this@DetailsActivity, "Failed while adding note")
            }
        }
    }

    fun deleteNoteFromFirebase(){
        val documentReference: DocumentReference

        documentReference= collectionReferenceForNotes.document(docId.toString())

        documentReference.delete().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                showToast(this@DetailsActivity, "Note deleted")
                finish()
            } else {
                showToast(this@DetailsActivity, "Failed while deleting note")
            }
        }

    }
}





