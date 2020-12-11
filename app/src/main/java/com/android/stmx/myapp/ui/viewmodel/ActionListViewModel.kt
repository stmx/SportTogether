package com.android.stmx.myapp.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.stmx.myapp.MyApp
import com.android.stmx.myapp.data.database.FirebaseRealtimeDatabase
import com.android.stmx.myapp.data.repository.Repository
import com.android.stmx.myapp.domain.model.Action
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import javax.inject.Inject

class ActionListViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val liveData: MutableLiveData<List<Action>> by lazy {
        MutableLiveData<List<Action>>()
    }

    init {
        MyApp.appComponent.inject(this)
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