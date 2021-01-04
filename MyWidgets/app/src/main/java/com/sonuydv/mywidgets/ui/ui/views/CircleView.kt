package com.sonuydv.mywidgets.ui.ui.views

import android.content.Context
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.view.GestureDetectorCompat
import androidx.core.view.ViewGroupCompat

class CircleView(context: Context) : LinearLayoutCompat(context) {

    private lateinit var mDetector : GestureDetectorCompat

    constructor(context: Context, attributeSet: AttributeSet) : this(context){
        mDetector = GestureDetectorCompat(context, MyGestureListener())
    }

    constructor(context: Context, attributeSet: AttributeSet, defStyleAttr: Int):this(context){
        mDetector = GestureDetectorCompat(context, MyGestureListener())
    }

    private class MyGestureListener : GestureDetector.SimpleOnGestureListener(){
        override fun onDoubleTap(e: MotionEvent?): Boolean {
            return super.onDoubleTap(e)
        }

        override fun onDown(e: MotionEvent?): Boolean {
            return super.onDown(e)
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return mDetector.onTouchEvent(event)
    }


}