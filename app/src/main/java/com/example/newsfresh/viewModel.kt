package com.example.newsfresh

import android.app.Application
import android.provider.ContactsContract
import androidx.lifecycle.*
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ViewModel(application: Application) : AndroidViewModel(application) {

    val repository: newsRepository
    val allNotes: LiveData<List<news>>

    init{
        val dao  = NewsDatabase.getDatabase(application).getNewsDao()
        repository = newsRepository(dao)
        allNotes = repository.allNews
    }
    fun deleteNote(news:news) = viewModelScope.launch(Dispatchers.IO){
        repository.delete(news)
    }
    fun insertNote(news:news) = viewModelScope.launch(Dispatchers.IO){
        repository.insert(news)
    }
    fun updateNote(news:news) = viewModelScope.launch(Dispatchers.IO){
        repository.update(news)
    }

}