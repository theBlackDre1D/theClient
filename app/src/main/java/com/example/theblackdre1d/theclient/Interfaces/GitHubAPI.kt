package com.example.theblackdre1d.theclient.Interfaces

import com.example.theblackdre1d.theclient.Models.Result
import com.example.theblackdre1d.theclient.Models.User
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.*

interface GitHubAPI {
    @GET("user")
    fun getUser(@Query("access_token") accessToken: String): Call<User>

    companion object Factory {
        fun create(): GitHubAPI {
            val retrofit = Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(MoshiConverterFactory.create())
                    .baseUrl("https://api.github.com/")
                    .build()
            return retrofit.create(GitHubAPI::class.java)
        }
    }
}