package com.example.theblackdre1d.theclient.Models
import com.squareup.moshi.Json


/**
 * Created by seremtinameno on 28.2.18.
 */

data class GitHubRepoContent(
		@Json(name = "name") val name:                  String?,
		@Json(name = "path") val path:                  String?,
		@Json(name = "sha") val sha:                    String?,
		@Json(name = "size") val size:                  Int?,
		@Json(name = "url") val url:                    String?,
		@Json(name = "html_url") val htmlUrl:           String?,
		@Json(name = "git_url") val gitUrl:             String?,
		@Json(name = "download_url") val downloadUrl:   String?,
		@Json(name = "type") val type:                  String?,
		@Json(name = "_links") val linksPullRequest:    Links?
)

data class Links(
		@Json(name = "self") val self:                  String?,
		@Json(name = "git") val git:                    String?,
		@Json(name = "html") val html:                  String?
)