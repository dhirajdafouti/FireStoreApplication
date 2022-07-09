package com.project.realdatabaseapplication

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class User(private val user: String, private val id: String, private val startName: String,private val age:String)