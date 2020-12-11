package com.android.stmx.myapp.data.database

import android.content.SharedPreferences
import com.android.stmx.myapp.MyApp
import com.android.stmx.myapp.data.mapper.FromDatabaseActionMapper
import com.android.stmx.myapp.domain.model.Action
import com.android.stmx.myapp.domain.model.User
import com.android.stmx.myapp.helper.UserSharedPreference
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject

class FirebaseRealtimeDatabase @Inject constructor(
    private var userSharedPreference: UserSharedPreference,
    private val reference: DatabaseReference
) {
    companion object {
        const val FIREBASE_CHILD_ACTION = "actions"
        const val FIREBASE_CHILD_USER = "users"
    }

    private val _dataFlow = MutableSharedFlow<MutableList<Action>>(replay = 1)
    val dataFlow = _dataFlow.asSharedFlow()

    private val callback = ChildEventListenerImpl(_dataFlow)
    private lateinit var userId: String
    private lateinit var oneTimeCallback: ValueEventListener


    init {
        reference.child(FIREBASE_CHILD_ACTION).addChildEventListener(callback)
        MyApp.appComponent.inject(this)
    }


    private fun checkUserId() {
        userId = userSharedPreference.checkUserId { getNewUserId() }
    }

    fun getUserId(): String {
        checkUserId()
        return userId
    }

    fun updateUser(user: User) {
        checkUserId()
        reference.child(FIREBASE_CHILD_USER).child(userId).setValue(user)
    }

    fun addAction(action: Action) {
        checkUserId()
        action.idOwner = userId
        reference.child(FIREBASE_CHILD_ACTION).push()
            .setValue(FromDatabaseActionMapper.mapper(action))
    }

    fun updateAction(action: Action) {
        checkUserId()
        action.idOwner = userId
        reference.child(FIREBASE_CHILD_ACTION).child(action.id)
            .setValue(FromDatabaseActionMapper.mapper(action))
    }

    suspend fun getAllAction(): List<Action> {
        val result = getResult()
        reference.child(FIREBASE_CHILD_ACTION).removeEventListener(oneTimeCallback)
        return result
    }

    fun clearDatabaseContext() {
        userSharedPreference.clearContext()
        reference.child(FIREBASE_CHILD_ACTION).removeEventListener(oneTimeCallback)
        reference.child(FIREBASE_CHILD_ACTION).removeEventListener(callback)

    }

    private fun getNewUserId(): String =
        reference.child(FIREBASE_CHILD_USER).push().key!!


    private suspend fun getResult(): List<Action> =
        suspendCancellableCoroutine { cont ->
            oneTimeCallback = ValueEventListenerImpl(cont)
            reference.child(FIREBASE_CHILD_ACTION).addValueEventListener(oneTimeCallback)
        }


}