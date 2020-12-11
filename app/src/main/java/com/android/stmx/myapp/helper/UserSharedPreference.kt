package com.android.stmx.myapp.helper

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import com.android.stmx.myapp.MyApp
import javax.inject.Inject

class UserSharedPreference @Inject constructor(private var context: Context?) {
    private lateinit var userId: String
    private var sharedPreferences: SharedPreferences?

    init {
        sharedPreferences = getSharedPreferences()
        userId = getUserId()
        MyApp.appComponent.inject(this)
    }

    companion object {
        const val STORAGE_NAME: String = "StorageName"
        const val USER_ID_TAG: String = "UserId"
        const val DEFAULT_USER_ID = "default_user_id"
    }

    private fun getSharedPreferences(): SharedPreferences? =
        context?.getSharedPreferences(STORAGE_NAME, AppCompatActivity.MODE_PRIVATE)

    private fun getUserId(): String =
        sharedPreferences?.getString(
            USER_ID_TAG,
            DEFAULT_USER_ID
        )!!

    private fun isDefaultUserId(): Boolean =
        (userId == DEFAULT_USER_ID)

    private fun writeUserIdToSharedPreferences() =
        sharedPreferences?.edit()?.putString(USER_ID_TAG, userId)?.apply()

    fun checkUserId(generatorUserId:()->String):String {
        if (isDefaultUserId()) {
            userId = generatorUserId()
            writeUserIdToSharedPreferences()
        }
        return userId
    }
    fun clearContext() {
        context =null
    }
}