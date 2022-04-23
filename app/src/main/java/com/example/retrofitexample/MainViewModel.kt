package com.example.retrofitexample

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.retrofitexample.data.datasource.ApiService
import com.example.retrofitexample.data.datasource.RetrofitClient
import com.example.retrofitexample.data.model.Article
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(
    private val api: ApiService = RetrofitClient().api
): ViewModel() {
    private val _articles : MutableLiveData<List<Article>> = MutableLiveData()
    val articles : LiveData<List<Article>> = _articles

    private val _selectedArticle : MutableLiveData<Article> = MutableLiveData()
    val selectedArticle : LiveData<Article> = _selectedArticle

    fun articleClickAfterFetch(clickedItemId: Long) = viewModelScope.launch {
        runCatching { api.getArticles() }
            .onSuccess {
                _articles.postValue(it)
                getArticle(clickedItemId)
            }
            .onFailure { it.printStackTrace() }
    }

    private fun getArticle(id: Long) = viewModelScope.launch {
        runCatching { api.getArticle(id) }
            .onSuccess {
                _selectedArticle.postValue(it)
            }
            .onFailure { it.printStackTrace() }
    }
}