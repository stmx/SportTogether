package com.android.stmx.myapp.domain.fiture

import com.android.stmx.myapp.domain.model.Action

object SortAndFilter {

    fun sortByDate(actionList: ArrayList<Action>): List<Action> =
            actionList.sortedWith(compareByDescending { it.dateTime })

    fun sortByDateCreation(actionList: ArrayList<Action>) {
        actionList.sortedWith(compareByDescending { it.dateTimeCreation })
    }

    fun filterByPlace(actionList: ArrayList<Action>, place: String) {
        actionList.filter { it.place == place }
    }

    fun filterByOwner(actionList: ArrayList<Action>, idOwner: String) {
        actionList.filter { it.idOwner == idOwner }
    }

    fun filterPlaceList(actionList: ArrayList<Action>): List<String> {
        return actionList.map { it.place }
    }

}