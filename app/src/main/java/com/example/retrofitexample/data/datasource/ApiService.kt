package com.example.retrofitexample.data.datasource

import com.example.retrofitexample.data.model.Article
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("/api/articles")
    suspend fun getArticles() : Response<List<Article>>

    @GET("/api/article/{id}")
    suspend fun getArticle(@Path("id") id : Long) : Response<Article>
}