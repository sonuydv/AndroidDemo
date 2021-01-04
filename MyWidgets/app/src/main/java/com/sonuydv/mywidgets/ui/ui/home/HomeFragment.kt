package com.sonuydv.mywidgets.ui.ui.home

import android.appwidget.AppWidgetManager
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.sonuydv.mywidgets.Note
import com.sonuydv.mywidgets.NoteWidgetProvider
import com.sonuydv.mywidgets.R
import com.sonuydv.mywidgets.data.Constants.NOTE_CONTENT
import com.sonuydv.mywidgets.data.Constants.NOTE_TITLE
import com.sonuydv.mywidgets.data.Constants.SHAREDPREF_NOTE

class HomeFragment : Fragment() {

  private lateinit var homeViewModel: HomeViewModel

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)
    val root = inflater.inflate(R.layout.fragment_home, container, false)
    homeViewModel.text.observe(viewLifecycleOwner, Observer {
    })
    return root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    val title=view.findViewById<EditText>(R.id.edt_note_title)
    val content=view.findViewById<EditText>(R.id.edt_note_content)

    view.findViewById<Button>(R.id.btn_save_note).setOnClickListener {
      if(title.text.isEmpty()){
        title.error = "Title"
      }else if(content.text.isEmpty()){
        content.error="content"
      }else{
        saveNoteToPrefs(Note(title.text.toString(),content.text.toString()))
        toast("Note Saved Successfully")
        updateWidgetState()
      }
    }
  }


  private fun saveNoteToPrefs(note:Note){
    val editor=
      context?.getSharedPreferences(SHAREDPREF_NOTE, AppCompatActivity.MODE_PRIVATE)
        ?.edit()
    editor!!.putString(NOTE_TITLE,note.title)
    editor.putString(NOTE_CONTENT,note.content)
    editor.apply()
  }

  private fun toast(msg:String){
    Toast.makeText(context,msg, Toast.LENGTH_SHORT).show()
  }

  private fun updateWidgetState(){
    context?.sendBroadcast(
      Intent(context,
        NoteWidgetProvider::class.java).setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE))
  }
  
  
}