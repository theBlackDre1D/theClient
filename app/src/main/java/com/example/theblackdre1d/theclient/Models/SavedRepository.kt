package com.example.theblackdre1d.theclient.Models

data class SavedRepository(
        val lastCommit:         GitHubCommit?,
        val lastPullRequest:    GitHubPullRequest?,
        val repositoryName:     String?,
        val authorName:         String?
)