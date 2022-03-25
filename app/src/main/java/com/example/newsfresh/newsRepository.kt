package com.example.newsfresh

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData

class newsRepository(private val nDao: newsDao) {

    // Room executes all queries on a separate thread.
    // Observed Flow will notify the observer when the data has changed.
    val allNews: LiveData<List<news>> = nDao.getAllNotes()

    // By default Room runs suspend queries off the main thread, therefore, we don't need to
    // implement anything else to ensure we're not doing long running database work
    // off the main thread.
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(news: news) {
        nDao.insert(news)
    }
    @WorkerThread
    fun delete(news: news) {
        nDao.delete(news)
    }
    fun update(news: news){
        nDao.update(news)
    }
}