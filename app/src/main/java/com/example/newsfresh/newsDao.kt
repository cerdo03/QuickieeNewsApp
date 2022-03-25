package com.example.newsfresh

import android.provider.ContactsContract
import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow
@Dao
interface newsDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(news: news)

    @Delete
    fun delete(news: news)

    @Update
    fun update(news: news)

    @Query("Select * from news_table order by id ASC")
    fun getAllNotes(): LiveData<List<news>>

}