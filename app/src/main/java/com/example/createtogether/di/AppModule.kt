package com.example.createtogether.di

import android.content.Context
import androidx.room.Room
import com.example.createtogether.db.DatabaseApp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class) //dependencies live as long as Application - there is also ActivityComponent, ServiceComponent
object AppModule {

    //Dagger calls all the functions it needs from the manual in the background with generated classes

    //this is a manual to create RunningDatabase
    @Singleton
    @Provides
    fun provideRunningDatabase(
        @ApplicationContext app: Context  // @ApplicationContext also comes from Dagger, inserts Context behind the scenes
    ) = Room.databaseBuilder(
        app,
        DatabaseApp::class.java,
        "database"
    ).build() //with Firebase -> allowMainThreadQueries().build()

    //but we also need access to the Dao with the help of the first created RunningDatabase
    @Singleton
    @Provides //we can use Objects (Dependencies) that we already created in the Module in the parameter of functions - here it is the Database we created above
    fun provideTextsDao(db: DatabaseApp) = db.getTextContentDao()

}