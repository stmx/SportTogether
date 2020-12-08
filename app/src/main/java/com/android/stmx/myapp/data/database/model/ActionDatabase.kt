package com.android.stmx.myapp.data.database.model


import java.util.*

class ActionDatabase {
    var idOwner: String = ""
    var dateTimeCreation: Date = Date()
    var dateTime: Date = Date()
    var description: String = ""
    var needPeople: Int = 0
    var payment: Int = 0
    var place: String = ""
    var sport: String = ""

    override fun equals(other: Any?): Boolean {
        if (other is ActionDatabase) {
            return idOwner == other.idOwner &&
                    dateTimeCreation == other.dateTimeCreation&&
                    dateTime == other.dateTime &&
                    description == other.description &&
                    needPeople == other.needPeople &&
                    payment == other.payment &&
                    place == other.place &&
                    sport == other.sport
        }
        return false
    }
}