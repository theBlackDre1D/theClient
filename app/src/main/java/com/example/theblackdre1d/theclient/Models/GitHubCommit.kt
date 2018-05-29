package com.example.theblackdre1d.theclient.Models
import com.squareup.moshi.Json
import io.realm.RealmModel

/*
* Data class for store information from GitHub server about commits.
* */
data class GitHubCommit (
		@Json(name = "sha") val sha: 							String?,
		@Json(name = "commit") val commit: 						Commit?,
		@Json(name = "url") val url: 							String?,
		@Json(name = "html_url") val htmlUrl: 					String?,
		@Json(name = "comments_url") val commentsUrl: 			String?,
		@Json(name = "author") val author: 						Owner?,
		@Json(name = "committer") val committer: 				Owner?,
		@Json(name = "parents") val parents: 					List<Parent?>?
) : RealmModel

data class Parent (
		@Json(name = "sha") val sha: 							String?,
		@Json(name = "url") val url: 							String?,
		@Json(name = "html_url") val htmlUrl: 					String?
)

data class Commit (
		@Json(name = "author") val author: 						Author?,
		@Json(name = "committer") val committer: 				Committer?,
		@Json(name = "message") val message: 					String?,
		@Json(name = "tree") val tree: 							Tree?,
		@Json(name = "url") val url: 							String?,
		@Json(name = "comment_count") val commentCount: 		Int?,
		@Json(name = "verification") val verification: 			Verification?
)

data class Author (
		@Json(name = "name") val name: 							String?,
		@Json(name = "email") val email: 						String?,
		@Json(name = "date") val date: 							String?
)

data class Tree(
		@Json(name = "sha") val sha: 							String?,
		@Json(name = "url") val url: 							String?
)

data class Committer (
		@Json(name = "name") val name: 							String?,
		@Json(name = "email") val email: 						String?,
		@Json(name = "date") val date: 							String?
)

data class Verification (
		@Json(name = "verified") val verified: 					Boolean?,
		@Json(name = "reason") val reason: 						String?,
		@Json(name = "signature") val signature: 				Any?,
		@Json(name = "payload") val payload: 					Any?
)

//data class Author (
//		@Json(name = "login") val login: String?,
//		@Json(name = "id") val id: Int?,
//		@Json(name = "avatar_url") val avatarUrl: String?,
//		@Json(name = "gravatar_id") val gravatarId: String?,
//		@Json(name = "url") val url: String?,
//		@Json(name = "html_url") val htmlUrl: String?,
//		@Json(name = "followers_url") val followersUrl: String?,
//		@Json(name = "following_url") val followingUrl: String?,
//		@Json(name = "gists_url") val gistsUrl: String?,
//		@Json(name = "starred_url") val starredUrl: String?,
//		@Json(name = "subscriptions_url") val subscriptionsUrl: String?,
//		@Json(name = "organizations_url") val organizationsUrl: String?,
//		@Json(name = "repos_url") val reposUrl: String?,
//		@Json(name = "events_url") val eventsUrl: String?,
//		@Json(name = "received_events_url") val receivedEventsUrl: String?,
//		@Json(name = "type") val type: String?,
//		@Json(name = "site_admin") val siteAdmin: Boolean?
//)

//data class Committer (
//		@Json(name = "login") val login: String?,
//		@Json(name = "id") val id: Int?,
//		@Json(name = "avatar_url") val avatarUrl: String?,
//		@Json(name = "gravatar_id") val gravatarId: String?,
//		@Json(name = "url") val url: String?,
//		@Json(name = "html_url") val htmlUrl: String?,
//		@Json(name = "followers_url") val followersUrl: String?,
//		@Json(name = "following_url") val followingUrl: String?,
//		@Json(name = "gists_url") val gistsUrl: String?,
//		@Json(name = "starred_url") val starredUrl: String?,
//		@Json(name = "subscriptions_url") val subscriptionsUrl: String?,
//		@Json(name = "organizations_url") val organizationsUrl: String?,
//		@Json(name = "repos_url") val reposUrl: String?,
//		@Json(name = "events_url") val eventsUrl: String?,
//		@Json(name = "received_events_url") val receivedEventsUrl: String?,
//		@Json(name = "type") val type: String?,
//		@Json(name = "site_admin") val siteAdmin: Boolean?
//)