package com.android.stmx.myapp.domain.fiture

import com.android.stmx.myapp.domain.model.Action

object SortAndFilter {

    fun sortByDate(actionList: ArrayList<Action>): List<Action> =
            actionList.sortedWith(compareByDescending { it.dateTime })

    fun sortByDateCreation(actionList: ArrayList<Action>) {
//        actionList.sortedWith (  )
    }
}