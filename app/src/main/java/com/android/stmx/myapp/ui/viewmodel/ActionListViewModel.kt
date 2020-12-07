package com.android.stmx.myapp.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.android.stmx.myapp.data.database.FirebaseRealtimeDatabase
import com.android.stmx.myapp.data.repository.Repository
import com.android.stmx.myapp.domain.model.Action
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch

class ActionListViewModel(application: Application) : AndroidViewModel(application) {

    private val database = FirebaseRealtimeDatabase(application)
    private val repository = Repository(database)
    private val liveData: MutableLiveData<List<Action>> by lazy {
        MutableLiveData<List<Action>>()
    }

    init {
        CoroutineScope(Dispatchers.Main).launch {
            repository.getDataFlowAction()
                    .debounce(250)
                    .collect {
                        liveData.postValue(it)
                    }
        }
    }

    fun getDataFlowAction(): MutableLiveData<List<Action>> {
        return liveData
    }

    fun addAction(action: Action) {
        repository.addAction(action)
    }

    fun getUserId() = repository.getUserId()

}