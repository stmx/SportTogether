package com.android.stmx.myapp.data.repository

import com.android.stmx.myapp.data.database.FirebaseRealtimeDatabase
import com.android.stmx.myapp.domain.model.Action
import com.android.stmx.myapp.domain.model.User
import kotlinx.coroutines.flow.SharedFlow

class Repository(private val database: FirebaseRealtimeDatabase) {
    fun getDataFlowAction(): SharedFlow<MutableList<Action>> {
        return database.dataFlow
    }
    fun addAction(action: Action) {
        database.addAction(action)
    }
    fun updateAction(action: Action) {
        database.updateAction(action)
    }
    fun updateUser(user: User) {
        database.updateUser(user)
    }
    fun getUserId() = database.getUserId()
}