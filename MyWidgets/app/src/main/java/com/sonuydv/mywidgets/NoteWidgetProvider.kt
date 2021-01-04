package com.sonuydv.mywidgets

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetManager.ACTION_APPWIDGET_UPDATE
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.widget.RemoteViews
import com.sonuydv.mywidgets.data.Constants.NOTE_CONTENT
import com.sonuydv.mywidgets.data.Constants.NOTE_TITLE
import com.sonuydv.mywidgets.data.Constants.SHAREDPREF_NOTE

class NoteWidgetProvider :AppWidgetProvider() {


    override fun onReceive(context: Context?, intent: Intent?) {
        super.onReceive(context, intent)
        if(intent?.action.equals(ACTION_APPWIDGET_UPDATE)){
            val thisWidget=ComponentName(context!!,NoteWidgetProvider::class.java)
            val allWidgetIds= AppWidgetManager.getInstance(context).getAppWidgetIds(thisWidget)
            for(widgetId in allWidgetIds!!){
                val widgetViews=RemoteViews(context.packageName,R.layout.note_widget_layout)
                val note=getNoteData(context)
                widgetViews.setTextViewText(R.id.tv_title_note,note.title)
                widgetViews.setTextViewText(R.id.tv_note_content,note.content)
                AppWidgetManager.getInstance(context).updateAppWidget(widgetId,widgetViews)
            }
        }
    }

    //On First initialization
    override fun onUpdate(context: Context?, appWidgetManager: AppWidgetManager?, appWidgetIds: IntArray?) {
 ;        super.onUpdate(context, appWidgetManager, appWidgetIds)

        //Get all ids
        val thisWidget=ComponentName(context!!,NoteWidgetProvider::class.java)
        val allWidgetIds= appWidgetManager?.getAppWidgetIds(thisWidget)
        for(widgetId in allWidgetIds!!){
            val widgetViews=RemoteViews(context.packageName,R.layout.note_widget_layout)
            val note=getNoteData(context)
            widgetViews.setTextViewText(R.id.tv_title_note,note.title)
            widgetViews.setTextViewText(R.id.tv_note_content,note.content)
            appWidgetManager.updateAppWidget(widgetId,widgetViews)
        }

    }

    private fun getNoteData(context:Context):Note{
        val prefs=context.getSharedPreferences(SHAREDPREF_NOTE,MODE_PRIVATE)
        val note=Note("","")
        note.title=prefs.getString(NOTE_TITLE,"Not Found").toString()
        note.content=prefs.getString(NOTE_CONTENT,"Not Found").toString()
        return note
    }



}