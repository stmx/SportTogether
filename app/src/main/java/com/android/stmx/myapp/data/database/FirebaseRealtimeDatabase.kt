package com.android.stmx.myapp.data.database

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import com.android.stmx.myapp.data.mapper.FromDatabaseActionMapper
import com.android.stmx.myapp.domain.model.Action
import com.android.stmx.myapp.domain.model.User
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.suspendCancellableCoroutine

class FirebaseRealtimeDatabase(private var context: Context?) {
    companion object {
        const val USER_ID_TAG: String = "UserId"
        const val STORAGE_NAME: String = "StorageName"
        const val FIREBASE_CHILD_ACTION = "actions"
        const val FIREBASE_CHILD_USER = "users"
        const val DEFAULT_USER_ID = "default_user_id"
    }

    private val _dataFlow = MutableSharedFlow<MutableList<Action>>(replay = 1)
    val dataFlow = _dataFlow.asSharedFlow()
    private val reference = Firebase.database.reference
    private val callback = ChildEventListenerImpl(_dataFlow)
    private lateinit var userId: String
    private lateinit var oneTimeCallback: ValueEventListener


    init {
        reference.child(FIREBASE_CHILD_ACTION).addChildEventListener(callback)
//        CoroutineScope(Dispatchers.Main).launch {
//            dataFlow
//                    .debounce(250)
//                    .collect {
//                        Log.d("tag", it.toString())
//                    }
//        }
    }


    private fun checkUserId() {
        val sharedPreferences = getSharedPreferences()
        userId = getUserIdFromSharedPreferences(sharedPreferences)
        if (isDefaultUserId()) {
            userId = getNewUserId()
            writeUserIdToSharedPreferences(userId, sharedPreferences)
        }
    }

    fun getUserId():String {
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
        reference.child(FIREBASE_CHILD_ACTION).push().setValue(action)
    }

    fun updateAction(action: Action) {
        checkUserId()
        action.idOwner = userId
        reference.child(FIREBASE_CHILD_ACTION).child(action.id).setValue(action)
    }

    suspend fun getAllAction(): List<Action> {
        val result = getResult()
        reference.child(FIREBASE_CHILD_ACTION).removeEventListener(oneTimeCallback)
        return result
    }

    fun clearDatabaseContext() {
        context = null
        reference.child(FIREBASE_CHILD_ACTION).removeEventListener(oneTimeCallback)
        reference.child(FIREBASE_CHILD_ACTION).removeEventListener(callback)

    }

    private fun getUserIdFromSharedPreferences(sharedPreferences: SharedPreferences): String =
            sharedPreferences.getString(USER_ID_TAG, DEFAULT_USER_ID)!!

    private fun getSharedPreferences(): SharedPreferences =
            context!!.getSharedPreferences(STORAGE_NAME, AppCompatActivity.MODE_PRIVATE)

    private fun writeUserIdToSharedPreferences(userId: String, sharedPreferences: SharedPreferences) =
            sharedPreferences.edit().putString(USER_ID_TAG, userId).apply()

    private fun getNewUserId(): String =
            reference.child(FIREBASE_CHILD_USER).push().key!!

    private fun isDefaultUserId(): Boolean =
            (userId == DEFAULT_USER_ID)


    private suspend fun getResult(): List<Action> =
            suspendCancellableCoroutine { cont ->
                oneTimeCallback = ValueEventListenerImpl(cont)
                reference.child(FIREBASE_CHILD_ACTION).addValueEventListener(oneTimeCallback)
            }


}