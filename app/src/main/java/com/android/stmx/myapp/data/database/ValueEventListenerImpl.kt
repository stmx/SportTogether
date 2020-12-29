package com.android.stmx.myapp.data.database

import com.android.stmx.myapp.data.mapper.ActionDatabaseMapper
import com.android.stmx.myapp.domain.model.Action
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.CancellableContinuation
import java.io.IOException

class ValueEventListenerImpl(private val cont:CancellableContinuation<List<Action>>): ValueEventListener {
    override fun onDataChange(snapshot: DataSnapshot) {
        val result = ActionDatabaseMapper.mapListFromSnapshot(snapshot)
        cont.resumeWith(Result.success(result))
    }

    override fun onCancelled(error: DatabaseError) {
        cont.resumeWith(Result.failure(IOException("Database error $error")))
    }
}