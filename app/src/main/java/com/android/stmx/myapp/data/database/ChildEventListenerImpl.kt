package com.android.stmx.myapp.data.database

import android.util.Log
import com.android.stmx.myapp.data.mapper.ActionDatabaseMapper
import com.android.stmx.myapp.domain.model.Action
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

class ChildEventListenerImpl(
        private var _dataFlow: MutableSharedFlow<MutableList<Action>>
) : ChildEventListener {

    private val data: MutableList<Action> = ArrayList()

    override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
        val actionItem = ActionDatabaseMapper.mapItemFromSnapshot(snapshot)
        data.add(actionItem)
        emit()
    }

    override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
        val actionItem = ActionDatabaseMapper.mapItemFromSnapshot(snapshot)
        data.map {
            if (it.id == actionItem.id) {
                actionItem
            } else it
        }
        emit()
    }

    override fun onChildRemoved(snapshot: DataSnapshot) {
        val actionItem = ActionDatabaseMapper.mapItemFromSnapshot(snapshot)
        data.remove(actionItem)
        emit()
    }

    override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
        Log.d("tag", snapshot.toString())
    }

    override fun onCancelled(error: DatabaseError) {
        Log.d("tag", error.toString())
    }

    private fun emit() {
        CoroutineScope(Dispatchers.Main).launch {
            _dataFlow.emit(data)
        }
    }
}