package com.example.newsfresh

class dataSource {
    public fun loadTopics():List<topic_item>{
        return listOf<topic_item>(
            topic_item(R.drawable.a,"All","https://newsapi.org/v2/top-headlines?country=in&" +
                    "category=general&language=en&apiKey=55f0f9f1dd6c4aaa9d33720dc1485c17"),
            topic_item(R.drawable.politics,"Politics","https://newsapi.org/v2/everything?" +
                    "q=" + "politics" +
                    "&sortBy=popularity&" +
                    "apiKey=55f0f9f1dd6c4aaa9d33720dc1485c17"),
            topic_item(R.drawable.business,"Business","https://newsapi.org/v2/top-headlines?" +
                    "country=in&category=business&language=en&apiKey=55f0f9f1dd6c4aaa9d33720dc1485c17"),
            topic_item(R.drawable.health,"Health","https://newsapi.org/v2/top-headlines?country=in&" +
                    "category=health&language=en&apiKey=55f0f9f1dd6c4aaa9d33720dc1485c17"),
            topic_item(R.drawable.science,"Science","https://newsapi.org/v2/top-headlines?country=in&" +
                    "category=science&language=en&apiKey=55f0f9f1dd6c4aaa9d33720dc1485c17"),
            topic_item(R.drawable.sport,"Sport","https://newsapi.org/v2/top-headlines?country=in&" +
                    "category=sports&language=en&apiKey=55f0f9f1dd6c4aaa9d33720dc1485c17"),
            topic_item(R.drawable.technology,"Technology","https://newsapi.org/v2/top-headlines?country=in&" +
                    "category=technology&language=en&apiKey=55f0f9f1dd6c4aaa9d33720dc1485c17"),
            topic_item(R.drawable.international,"International","https://newsapi.org/v2/everything?" +
                    "q=" + "international" +
                    "&sortBy=popularity&" +
                    "apiKey=55f0f9f1dd6c4aaa9d33720dc1485c17")
        )
    }
}