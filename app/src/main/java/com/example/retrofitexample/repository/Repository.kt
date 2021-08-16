package com.example.retrofitexample.repository

import com.example.retrofitexample.network.ApiService

class Repository(private val apiService: ApiService) {
    fun getCharacters(page: String) = apiService.fetchCharacters(page)
}