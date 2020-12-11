package com.android.stmx.myapp.di.module

import android.content.Context
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides

@Module
class AppModule constructor(private val context: Context) {

    @Provides
    fun provideContext() =
        context

    @Provides
    fun provideDatabaseReference() =
        Firebase.database.reference


}