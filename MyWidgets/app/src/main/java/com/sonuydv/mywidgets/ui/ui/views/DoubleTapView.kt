package com.sonuydv.mywidgets.ui.ui.views

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View

class DoubleTapView(context: Context) : View(context) {
    private var mGestureDetector : GestureDetector
    init {
        mGestureDetector = GestureDetector(context,MyGestureListener())
    }

    private class MyGestureListener : GestureDetector.SimpleOnGestureListener(){
        override fun onDoubleTap(e: MotionEvent?): Boolean {
            return super.onDoubleTap(e)
        }

        override fun onDown(e: MotionEvent?): Boolean {
            return super.onDown(e)
        }
    }

    constructor(context: Context, attributeSet: AttributeSet):this(context){
        mGestureDetector = GestureDetector(context,MyGestureListener())
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return mGestureDetector.onTouchEvent(event)
    }

}