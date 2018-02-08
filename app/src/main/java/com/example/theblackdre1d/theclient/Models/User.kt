package com.example.theblackdre1d.theclient.Models

/**
 * Created by seremtinameno on 8.2.18.
 */
data class User(
        val login:              String,
        val id:                 Int,
        val avatar_url:         String,
        val url:                String,
        val html_url:           String,
        val type :              String,
        val created_at:         String
)

data class Result (val total_count: Int, val incomplete_results: Boolean, val items: List<User>)