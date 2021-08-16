package com.example.retrofitexample.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.retrofitexample.repository.Repository
import com.example.retrofitexample.utils.ScreenState
import com.example.retrofitexample.network.ApiClient
import com.example.retrofitexample.network.Character
import com.example.retrofitexample.network.CharacterResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(
    private val repository: Repository = Repository(ApiClient.apiService)
) : ViewModel() {

    private var _charactersLiveData = MutableLiveData<ScreenState<List<Character>?>>()

    val characterLiveData: LiveData<ScreenState<List<Character>?>>
        get() = _charactersLiveData

    init {
        fetchCharacter()
    }

    private fun fetchCharacter() {
        val client = repository.getCharacters("1")
        _charactersLiveData.postValue(ScreenState.Loading(null))
        client.enqueue(object : Callback<CharacterResponse> {

            override fun onResponse(
                call: Call<CharacterResponse>,
                response: Response<CharacterResponse>
            ) {
                if (response.isSuccessful)
                    _charactersLiveData.postValue(ScreenState.Success(response.body()?.result))
                else
                    _charactersLiveData.postValue(
                        ScreenState.Error(
                            response.code().toString(),
                            null
                        )
                    )
            }

            override fun onFailure(call: Call<CharacterResponse>, t: Throwable) {
                _charactersLiveData.postValue(ScreenState.Error(t.message.toString(), null))
            }
        })
    }
}