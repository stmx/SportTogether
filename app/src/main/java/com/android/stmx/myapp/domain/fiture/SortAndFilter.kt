package com.android.stmx.myapp.domain.fiture

import com.android.stmx.myapp.domain.model.Action

object SortAndFilter {

    fun sortByDate(actionList: List<Action>, desc: Boolean = false): List<Action> = if (desc) {
        actionList.sortedWith(compareByDescending { it.dateTime })
    } else actionList.sortedWith(compareBy { it.dateTime })

    fun sortByDateCreation(actionList: ArrayList<Action>, desc: Boolean = false) =  if (desc) {
        actionList.sortedWith(compareByDescending { it.dateTimeCreation })
    } else actionList.sortedWith(compareBy { it.dateTimeCreation })

    fun filterByPlace(actionList: ArrayList<Action>, place: String) =
        actionList.filter { it.place == place }


    fun filterByOwner(actionList: ArrayList<Action>, idOwner: String) =
        actionList.filter { it.idOwner == idOwner }

    fun getPlaceList(actionList: ArrayList<Action>): List<String> {
        return actionList.map { it.place }.distinct()
    }


}