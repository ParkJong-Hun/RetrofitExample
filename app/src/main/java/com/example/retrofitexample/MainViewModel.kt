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
        try {
            with(api.getArticles()) {
                if (isSuccessful) {
                    body()?.let {
                        _articles.postValue(it)
                    }
                    val getArticleResponse = api.getArticle(clickedItemId)
                    if(getArticleResponse.isSuccessful) {
                        getArticleResponse.body()?.let {selectedArticle ->
                            _selectedArticle.postValue(selectedArticle)
                        }
                    }
                } else {
                    //TODO: 뭔가 에러 바디를 처리하고 싶은 것
                }
            }
        } catch (t: Throwable) {
            t.printStackTrace()
        }
    }
}