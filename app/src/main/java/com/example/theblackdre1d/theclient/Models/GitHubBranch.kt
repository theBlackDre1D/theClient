package com.example.theblackdre1d.theclient.Models
import com.squareup.moshi.Json



data class GitHubBranch(
    @Json(name = "name") val name:          String?,
    @Json(name = "commit") val commit:      Commit?
)

data class CommitBranch(
    @Json(name = "sha") val sha:            String?,
    @Json(name = "url") val url:            String?
)