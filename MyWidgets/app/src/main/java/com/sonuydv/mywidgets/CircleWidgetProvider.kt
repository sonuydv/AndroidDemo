package com.sonuydv.mywidgets

import android.app.PendingIntent
import android.app.admin.DevicePolicyManager
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Context.DEVICE_POLICY_SERVICE
import android.content.Intent
import android.widget.RemoteViews
import android.widget.Toast
import com.sonuydv.mywidgets.utils.DeviceAdmin


class CircleWidgetProvider : AppWidgetProvider() {
    companion object {
        const val CIRCLE_WIDGET_CLICKED = "com.sonuydv.mywidgets.CircleWidgetProvider.CIRCLE_WIDGET_CLICKED"
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        super.onReceive(context, intent)
        if (intent != null) {
            when (intent.action) {
                CIRCLE_WIDGET_CLICKED -> {
                    Toast.makeText(context, "circle clicked", Toast.LENGTH_SHORT).show()
                }
                AppWidgetManager.ACTION_APPWIDGET_UPDATE -> {
                    val thisWidget = ComponentName(context!!, CircleWidgetProvider::class.java)
                    val allWidgetIds =
                        AppWidgetManager.getInstance(context).getAppWidgetIds(thisWidget)
                    for (widgetId in allWidgetIds!!) {
                        val widgetViews = RemoteViews(context.packageName, R.layout.circle_widget)

                        AppWidgetManager.getInstance(context).updateAppWidget(widgetId, widgetViews)
                    }
                }
            }
        }
    }

    override fun onUpdate(context: Context?, appWidgetManager: AppWidgetManager?, appWidgetIds: IntArray?) {
        super.onUpdate(context, appWidgetManager, appWidgetIds)
        val thisWidget=ComponentName(context!!, CircleWidgetProvider::class.java)
        val allWidgetIds= appWidgetManager?.getAppWidgetIds(thisWidget)
        for(widgetId in allWidgetIds!!){
            val widgetViews=RemoteViews(context.packageName, R.layout.circle_widget)
            widgetViews.setOnClickPendingIntent(
                R.id.view_circle, getPendingSelfIntent(
                    context,
                    CIRCLE_WIDGET_CLICKED
                )
            )
            appWidgetManager.updateAppWidget(widgetId, widgetViews)
        }
    }

    private fun getPendingSelfIntent(context: Context?, action: String?): PendingIntent? {
        val intent = Intent(context, javaClass)
        intent.action = action
        return PendingIntent.getBroadcast(context, 0, intent, 0)
    }
}