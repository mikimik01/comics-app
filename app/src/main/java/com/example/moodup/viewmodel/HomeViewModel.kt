package com.example.moodup.viewmodel

import android.util.Log
import android.widget.Toast
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

class HomeViewModel : ViewModel() {
    private val _comics = MutableLiveData<List<Comic>>()
    val comics: LiveData<List<Comic>> get() = _comics

    private val publicKey = "ddf99221f3ef736b4d3443c8cfd36c18"
    private val privateKey = "5de5706c3a4837d91652a91702efbf62b0410722"
    private val ts = "1"
    private var offset = 0
    private val limit = 10

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    init {
        loadMoreComics()
    }

    fun loadMoreComics() {
        _loading.value = true
        val hash = md5("$ts$privateKey$publicKey")
        val call = MarvelApi.retrofitService.getComics(ts, publicKey, hash, limit, offset)
        call.enqueue(object : Callback<ComicDataWrapper> {
            override fun onResponse(call: Call<ComicDataWrapper>, response: Response<ComicDataWrapper>) {
                if (response.isSuccessful) {
                    val newComics = response.body()?.data?.results ?: emptyList()
                    _comics.value = _comics.value?.plus(newComics) ?: newComics
                    offset += limit
                } else {
                    Log.e("HomeViewModell", "Zla odp: ${response.errorBody()}")
                }
                _loading.value = false
            }

            override fun onFailure(call: Call<ComicDataWrapper>, t: Throwable) {
                Log.e("HomeViewModell", "Brak internetu/blad", t)
                _loading.value = false
            }
        })
    }

    fun md5(input: String): String {
        val md = MessageDigest.getInstance("MD5")
        return BigInteger(1, md.digest(input.toByteArray()))
            .toString(16)
            .padStart(32, '0')
    }
}
