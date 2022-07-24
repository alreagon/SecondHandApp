package com.example.finalprojectbinaracademy_secondhandapp.utils

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.example.finalprojectbinaracademy_secondhandapp.R

fun Toast.successToast(message: String, context: Context) {

    val inflater = LayoutInflater.from(context)
    val layout: View = inflater.inflate(R.layout.toast_notification, null)
    val textView = layout.findViewById<TextView>(R.id.textNotifToast)

    textView.text = message
    this.apply {
        setGravity(Gravity.TOP, 0, 200)
        duration = Toast.LENGTH_LONG
        view = layout
        show()
    }
}


fun Toast.errorToast(message: String, context: Context) {

    val inflater = LayoutInflater.from(context)
    val layout: View = inflater.inflate(R.layout.toast_notification_error, null)
    val textView = layout.findViewById<TextView>(R.id.textNotifToast)

    textView.text = message
    this.apply {
        setGravity(Gravity.TOP, 0, 200)
        duration = Toast.LENGTH_LONG
        view = layout
        show()
    }
}
