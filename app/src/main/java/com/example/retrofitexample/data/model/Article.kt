package com.example.retrofitexample.data.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Article (
    val id: Long,
    val title: String,
    val content: String
)