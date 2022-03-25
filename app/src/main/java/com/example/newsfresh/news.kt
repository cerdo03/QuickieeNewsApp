package com.example.newsfresh

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="news_table")
data class news (
    @ColumnInfo(name="title")val title: String,
    @ColumnInfo(name="author")val author: String,
    @ColumnInfo(name="url")var url:String,
    @ColumnInfo(name="imageurl")val imageurl: String,
    @ColumnInfo(name="content")var content: String
){
    @PrimaryKey(autoGenerate = true) var id =0
}
