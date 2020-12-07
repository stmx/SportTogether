package com.android.stmx.myapp.data.mapper

import com.android.stmx.myapp.data.database.model.ActionDatabase
import com.android.stmx.myapp.domain.model.Action
import com.google.firebase.database.DataSnapshot

object FromDatabaseActionMapper {

    private fun mapper(action: ActionDatabase, actionId: String): Action {
        return Action(
                actionId,
                action.idOwner,
                action.dateTime,
                action.description,
                action.needPeople,
                action.payment,
                action.place,
                action.sport
        )
    }

    fun mapper(action: Action): ActionDatabase {

        val actionDatabase = ActionDatabase()
        actionDatabase.idOwner = action.idOwner
        actionDatabase.dateTime = action.dateTime
        actionDatabase.description = action.description
        actionDatabase.needPeople = action.needPeople
        actionDatabase.payment = action.payment
        actionDatabase.place = action.place
        actionDatabase.sport = action.sport
        return actionDatabase
    }

    fun mapperListFromSnapshot(snapshot: DataSnapshot): List<Action> {
        return snapshot
                .children
                .toList()
                .map {
                    val actionDatabase = it.getValue(ActionDatabase::class.java)!!
                    val key = it.key!!
                    mapper(actionDatabase, key)
                }
    }

    fun mapperItemFromSnapshot(snapshot: DataSnapshot): Action {
        val actionDatabase = snapshot.getValue(ActionDatabase::class.java)!!
        val key = snapshot.key!!
        return mapper(actionDatabase, key)
    }
}