package com.example.moodup.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moodup.data.Comic
import com.example.moodup.data.ComicDataWrapper
import com.example.moodup.data.MarvelApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.security.MessageDigest
import java.math.BigInteger

class SearchViewModel : ViewModel() {
    private val _comics = MutableLiveData<List<Comic>>()
    val comics: LiveData<List<Comic>> get() = _comics

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    private val publicKey = "1b1d366d594a5a142c7d2c04a5e0e17b"
    private val privateKey = "d4aed0917e835baaf7ec60e084fc53f4bb011fff"
    private val ts = "1"

    fun searchComics(query: String) {
        _loading.value = true
        val hash = md5("$ts$privateKey$publicKey")
        val call = MarvelApi.retrofitService.searchComics(ts, publicKey, hash, query)
        call.enqueue(object : Callback<ComicDataWrapper> {
            override fun onResponse(call: Call<ComicDataWrapper>, response: Response<ComicDataWrapper>) {
                _loading.value = false
                if (response.isSuccessful) {
                    _comics.value = response.body()?.data?.results ?: emptyList()
                } else {
                    Log.e("HomeViewModel", "Zla odp: ${response.errorBody()}")
                    _comics.value = emptyList()
                }
            }

            override fun onFailure(call: Call<ComicDataWrapper>, t: Throwable) {
                _loading.value = false
                Log.e("SearchViewModel", "Brak internetu", t)
                _comics.value = emptyList()
            }
        })
    }

    private fun md5(input: String): String {
        val md = MessageDigest.getInstance("MD5")
        return BigInteger(1, md.digest(input.toByteArray()))
            .toString(16)
            .padStart(32, '0')
    }
}
