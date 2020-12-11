package com.android.stmx.myapp.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.android.stmx.myapp.di.utils.ViewModelFactory
import com.android.stmx.myapp.di.utils.ViewModelKey
import com.android.stmx.myapp.ui.viewmodel.ActionListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {
    @Binds
    internal abstract fun demoViewModel(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(ActionListViewModel::class)
    internal abstract fun postListViewModel(viewModel: ActionListViewModel): ViewModel
}