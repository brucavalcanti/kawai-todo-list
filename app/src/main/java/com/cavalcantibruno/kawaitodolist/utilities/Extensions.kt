package com.cavalcantibruno.kawaitodolist.utilities

import android.app.Activity
import android.widget.Toast


fun Activity.customMessage(text:String?){
        Toast.makeText(this, "$text", Toast.LENGTH_SHORT).show()
    }

