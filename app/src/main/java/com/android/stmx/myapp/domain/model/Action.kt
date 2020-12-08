package com.android.stmx.myapp.domain.model

import com.google.firebase.database.IgnoreExtraProperties
import java.text.SimpleDateFormat
import java.util.*

@IgnoreExtraProperties
data class Action(
        var id: String,
        var idOwner: String,
        val dateTimeCreation: Date,
        val dateTime: Date,
        val description: String,
        val needPeople: Int,
        val payment: Int,
        val place: String,
        val sport: String
) {
    companion object {
        fun newInstance(
                dateTimeCreation: Date,
                dateTime: Date,
                description: String,
                needPeople: Int,
                payment: Int,
                place: String,
                sport: String
        ) = Action(
                "",
                "",
                dateTimeCreation,
                dateTime,
                description,
                needPeople,
                payment,
                place,
                sport
        )
    }


    fun getDateString() = SimpleDateFormat("dd.MM.yyyy", Locale.ROOT).format(dateTime)!!
    fun getTimeString() = SimpleDateFormat("HH:mm", Locale.ROOT).format(dateTime)!!

}