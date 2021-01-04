package com.sonuydv.mywidgets.data.model

import androidx.room.Entity

@Entity
data class Note(
  var title:String,
  var body: String
)
