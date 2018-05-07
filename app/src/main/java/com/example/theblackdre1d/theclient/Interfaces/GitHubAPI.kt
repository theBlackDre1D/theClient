package com.example.theblackdre1d.theclient.Interfaces

import com.example.theblackdre1d.theclient.Models.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GitHubAPI {
    @GET("user")
    fun getUser(@Query("access_token") accessToken: String): Call<ResponseBody>

    @GET("user/repos")
    fun getUserRepos(@Query("access_token") accessToken: String): Call<List<GitHubRepository>>

    @GET("repos/{name}/{repo}/commits")
    fun getRepoCommits(@Path("name") name: String, @Path("repo") repo: String, @Query("access_token") accessToken: String): Call<List<GitHubCommit>>

    @GET("repos/{name}/{repo}/contents/{path}")
    fun getRepoContent(@Path("name") name: String, @Path("repo") repo: String, @Path("path") path: String,
                       @Query("access_token") accessToken: String): Call<List<GitHubRepoContent>>

    @GET("repos/{name}/{repo}/pulls")
    fun getRepoPulls(@Path("name") name: String, @Path("repo") repo: String, @Query("access_token") accessToken: String): Call<List<GitHubPullRequest>>

    @GET("repos/{owner}/{repoName}/branches")
    fun getRepoBranches(@Path("owner") owner: String, @Path("repoName") repoName: String, @Query("access_token") accessToken: String): Call<ArrayList<GitHubBranch>>

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