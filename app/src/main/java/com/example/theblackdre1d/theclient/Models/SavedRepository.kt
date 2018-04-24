package com.example.theblackdre1d.theclient.Models

data class SavedRepository(
        val commitsCount:       Int?,
        val lastPullRequest:    GitHubPullRequest?,
        val repositoryName:     String?,
        val authorName:         String?
)