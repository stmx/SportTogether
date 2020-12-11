package com.android.stmx.myapp.di.module

import com.android.stmx.myapp.data.database.FirebaseRealtimeDatabase
import com.android.stmx.myapp.data.repository.Repository
import com.android.stmx.myapp.helper.UserSharedPreference
import com.google.firebase.database.DatabaseReference
import dagger.Module
import dagger.Provides


@Module(includes = [AppModule::class])
class FirebaseModule {

    @Provides
    fun provideFirebaseDatabase(
        userSharedPreference: UserSharedPreference,
        reference: DatabaseReference
    ) = FirebaseRealtimeDatabase(userSharedPreference, reference)

    @Provides
    fun provideRepository(
        firebaseRealtimeDatabase: FirebaseRealtimeDatabase
    ) = Repository(firebaseRealtimeDatabase)
}