package com.example.theblackdre1d.theclient.Models
/*
* Data class for store information used in synchronization.
* */
data class SavedRepository(
        val lastCommit:         GitHubCommit?,
        val lastPullRequest:    GitHubPullRequest?,
        val repositoryName:     String?,
        val authorName:         String?
)