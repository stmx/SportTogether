package com.android.stmx.myapp.di

import com.android.stmx.myapp.data.database.FirebaseRealtimeDatabase
import com.android.stmx.myapp.di.module.AppModule
import com.android.stmx.myapp.di.module.FirebaseModule
import com.android.stmx.myapp.di.module.ViewModelModule
import com.android.stmx.myapp.di.utils.ViewModelFactory
import com.android.stmx.myapp.helper.UserSharedPreference
import com.android.stmx.myapp.ui.fragments.ActionListFragment
import com.android.stmx.myapp.ui.viewmodel.ActionListViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        FirebaseModule::class,
        ViewModelModule::class
    ]
)
interface AppComponent {

    fun inject(vm: ActionListViewModel)
    fun inject(vm: ActionListFragment)
    fun inject(vm: FirebaseRealtimeDatabase)
    fun inject(vm: ViewModelFactory)
    fun inject(vm: UserSharedPreference)

}