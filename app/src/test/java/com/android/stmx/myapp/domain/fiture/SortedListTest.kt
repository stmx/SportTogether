package com.android.stmx.myapp.domain.fiture

import com.android.stmx.myapp.domain.model.Action
import com.android.stmx.myapp.helper.RandomAction
import org.junit.Test

class SortedListTest {
    @Test
    fun test() {
        val actionList = ArrayList<Action>()
        actionList.add(RandomAction.generateRandomAction())
        actionList.add(RandomAction.generateRandomAction())
        actionList.add(RandomAction.generateRandomAction())
        actionList.add(RandomAction.generateRandomAction())
        actionList.add(RandomAction.generateRandomAction())
        actionList.add(RandomAction.generateRandomAction())
        actionList.add(RandomAction.generateRandomAction())
        actionList.add(RandomAction.generateRandomAction())
        actionList.add(RandomAction.generateRandomAction())
        actionList.add(RandomAction.generateRandomAction())

        println(SortAndFilter.getPlaceList(actionList).toString())
        println(SortAndFilter.sortByDate(actionList).map { it.dateTime }.toString())
        println(SortAndFilter.sortByDate(actionList,true).map { it.dateTime }.toString())
    }

}