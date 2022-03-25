package com.example.newsfresh

data class topic_item(val uri: Int, val topic: String,val URL: String){
    fun getURI(): Int {
        return uri
    }
    fun getTopicName(): String{
        return topic
    }
    fun getUrl(): String{
        return URL
    }
}