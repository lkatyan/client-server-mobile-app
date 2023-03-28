package com.example.clientservermobileapp.ui.favourite

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.clientservermobileapp.data.api.NewsRepository
import com.example.clientservermobileapp.models.Article
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouriteViewModel @Inject constructor(private val repository: NewsRepository): ViewModel() {

    val favLiveData: MutableLiveData<List<Article>> = MutableLiveData()

    init {
        getFavouriteNews()
    }

    private fun getFavouriteNews() =
        viewModelScope.launch(Dispatchers.IO) {
            val res = repository.getFavouriteArticles()
            favLiveData.postValue(res)
        }
}