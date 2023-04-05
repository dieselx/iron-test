package com.iron.test.di

import android.content.Context
import android.util.Log
import androidx.room.Room
import com.iron.test.db.BootEventDao
import com.iron.test.db.DataBase
import com.iron.test.push.PushManager
import com.iron.test.push.PushManagerImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
internal interface AppModule {

    @Binds
    @Singleton
    fun bindManager(manager: PushManagerImpl): PushManager

    companion object {
        @Provides
        @Singleton
        fun provideDb(@ApplicationContext context: Context): DataBase {
            return Room.databaseBuilder(context, DataBase::class.java, "db").build()
        }

        @Provides
        @Singleton
        fun provideDao(db: DataBase): BootEventDao {
            return db.bootEventDao()
        }
    }
}