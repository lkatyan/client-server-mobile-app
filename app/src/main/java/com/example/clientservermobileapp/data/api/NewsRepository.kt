package com.example.clientservermobileapp.data.api

import com.example.clientservermobileapp.data.db.ArticleDao
import com.example.clientservermobileapp.models.Article
import javax.inject.Inject

class NewsRepository @Inject constructor(
    private val newsService: NewsService,
    private val articleDao: ArticleDao
    ) {
    suspend fun getNews(countryCode: String, pageNumber: Int) =
        newsService.getHeadlines(countryCode = countryCode, page = pageNumber)

    suspend fun getSearchNews(query: String, pageNumber: Int) =
        newsService.getEverything(query = query, page = pageNumber)

    fun getFavouriteArticles() = articleDao.getAllArticles()

    suspend fun addToFavourite(article: Article) = articleDao.insert(article = article)
    suspend fun deleteFromFavourite(article: Article) = articleDao.delete(article = article)
}