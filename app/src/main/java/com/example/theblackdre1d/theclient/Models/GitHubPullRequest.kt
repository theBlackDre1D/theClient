package com.example.theblackdre1d.theclient.Models
import com.squareup.moshi.Json

data class GitHubPullRequest (
        @Json(name = "url") val url:                                    String?,
        @Json(name = "id") val id:                                      Int?,
        @Json(name = "html_url") val htmlUrl:                           String?,
        @Json(name = "diff_url") val diffUrl:                           String?,
        @Json(name = "patch_url") val patchUrl:                         String?,
        @Json(name = "issue_url") val issueUrl:                         String?,
        @Json(name = "number") val number:                              Int?,
        @Json(name = "state") val state:                                String?,
        @Json(name = "locked") val locked:                              Boolean?,
        @Json(name = "title") val title:                                String?,
        @Json(name = "user") val user:                                  UserPullRequests?,
        @Json(name = "body") val body:                                  String?,
        @Json(name = "created_at") val createdAt:                       String?,
        @Json(name = "updated_at") val updatedAt:                       String?,
        @Json(name = "merge_commit_sha") val mergeCommitSha:            String?,
        @Json(name = "assignees") val assignees:                        List<Any?>?,
        @Json(name = "requested_reviewers") val requestedReviewers:     List<Any?>?,
        @Json(name = "requested_teams") val requestedTeams:             List<Any?>?,
        @Json(name = "labels") val labels:                              List<Any?>?,
        @Json(name = "commits_url") val commitsUrl:                     String?,
        @Json(name = "review_comments_url") val reviewCommentsUrl:      String?,
        @Json(name = "review_comment_url") val reviewCommentUrl:        String?,
        @Json(name = "comments_url") val commentsUrl:                   String?,
        @Json(name = "statuses_url") val statusesUrl:                   String?,
        @Json(name = "head") val head:                                  Head?,
        @Json(name = "base") val base:                                  Base?,
        @Json(name = "_links") val linksPullRequest:                    LinksPullRequest?,
        @Json(name = "author_association") val authorAssociation:       String?
)

data class LinksPullRequest(
		@Json(name = "self") val self:                                  Self?,
		@Json(name = "html") val html:                                  Html?,
		@Json(name = "issue") val issue:                                Issue?,
		@Json(name = "comments") val comments:                          Comments?,
		@Json(name = "review_comments") val reviewComments:             ReviewComments?,
		@Json(name = "review_comment") val reviewComment:               ReviewComment?,
		@Json(name = "commits") val commits:                            Commits?,
		@Json(name = "statuses") val statuses:                          Statuses?
)

data class ReviewComment(
		@Json(name = "href") val href:                                  String?
)

data class Self(
		@Json(name = "href") val href:                                  String?
)

data class Html(
		@Json(name = "href") val href:                                  String?
)

data class Issue(
		@Json(name = "href") val href:                                  String?
)

data class Comments(
		@Json(name = "href") val href:                                  String?
)

data class Commits(
		@Json(name = "href") val href:                                  String?
)

data class Statuses(
		@Json(name = "href") val href:                                  String?
)

data class ReviewComments(
		@Json(name = "href") val href:                                  String?
)

data class Head(
		@Json(name = "label") val label:                                String?,
		@Json(name = "ref") val ref:                                    String?,
		@Json(name = "sha") val sha:                                    String?,
		@Json(name = "user") val user:                                  UserPullRequests?,
		@Json(name = "repo") val repo:                                  Repo?
)

data class Repo(
		@Json(name = "id") val id:                                      Int?,
		@Json(name = "name") val name:                                  String?,
		@Json(name = "full_name") val fullName:                         String?,
		@Json(name = "owner") val owner:                                Owner?,
		@Json(name = "private") val private:                            Boolean?,
		@Json(name = "html_url") val htmlUrl:                           String?,
		@Json(name = "description") val description:                    String?,
		@Json(name = "fork") val fork:                                  Boolean?,
		@Json(name = "url") val url:                                    String?,
		@Json(name = "forks_url") val forksUrl:                         String?,
		@Json(name = "keys_url") val keysUrl:                           String?,
		@Json(name = "collaborators_url") val collaboratorsUrl:         String?,
		@Json(name = "teams_url") val teamsUrl:                         String?,
		@Json(name = "hooks_url") val hooksUrl:                         String?,
		@Json(name = "issue_events_url") val issueEventsUrl:            String?,
		@Json(name = "events_url") val eventsUrl:                       String?,
		@Json(name = "assignees_url") val assigneesUrl:                 String?,
		@Json(name = "branches_url") val branchesUrl:                   String?,
		@Json(name = "tags_url") val tagsUrl:                           String?,
		@Json(name = "blobs_url") val blobsUrl:                         String?,
		@Json(name = "git_tags_url") val gitTagsUrl:                    String?,
		@Json(name = "git_refs_url") val gitRefsUrl:                    String?,
		@Json(name = "trees_url") val treesUrl:                         String?,
		@Json(name = "statuses_url") val statusesUrl:                   String?,
		@Json(name = "languages_url") val languagesUrl:                 String?,
		@Json(name = "stargazers_url") val stargazersUrl:               String?,
		@Json(name = "contributors_url") val contributorsUrl:           String?,
		@Json(name = "subscribers_url") val subscribersUrl:             String?,
		@Json(name = "subscription_url") val subscriptionUrl:           String?,
		@Json(name = "commits_url") val commitsUrl:                     String?,
		@Json(name = "git_commits_url") val gitCommitsUrl:              String?,
		@Json(name = "comments_url") val commentsUrl:                   String?,
		@Json(name = "issue_comment_url") val issueCommentUrl:          String?,
		@Json(name = "contents_url") val contentsUrl:                   String?,
		@Json(name = "compare_url") val compareUrl:                     String?,
		@Json(name = "merges_url") val mergesUrl:                       String?,
		@Json(name = "archive_url") val archiveUrl:                     String?,
		@Json(name = "downloads_url") val downloadsUrl:                 String?,
		@Json(name = "issues_url") val issuesUrl:                       String?,
		@Json(name = "pulls_url") val pullsUrl:                         String?,
		@Json(name = "milestones_url") val milestonesUrl:               String?,
		@Json(name = "notifications_url") val notificationsUrl:         String?,
		@Json(name = "labels_url") val labelsUrl:                       String?,
		@Json(name = "releases_url") val releasesUrl:                   String?,
		@Json(name = "deployments_url") val deploymentsUrl:             String?,
		@Json(name = "created_at") val createdAt:                       String?,
		@Json(name = "updated_at") val updatedAt:                       String?,
		@Json(name = "pushed_at") val pushedAt:                         String?,
		@Json(name = "git_url") val gitUrl:                             String?,
		@Json(name = "ssh_url") val sshUrl:                             String?,
		@Json(name = "clone_url") val cloneUrl:                         String?,
		@Json(name = "svn_url") val svnUrl:                             String?,
		@Json(name = "homepage") val homepage:                          String?,
		@Json(name = "size") val size:                                  Int?,
		@Json(name = "stargazers_count") val stargazersCount:           Int?,
		@Json(name = "watchers_count") val watchersCount:               Int?,
		@Json(name = "language") val language:                          String?,
		@Json(name = "has_issues") val hasIssues:                       Boolean?,
		@Json(name = "has_projects") val hasProjects:                   Boolean?,
		@Json(name = "has_downloads") val hasDownloads:                 Boolean?,
		@Json(name = "has_wiki") val hasWiki:                           Boolean?,
		@Json(name = "has_pages") val hasPages:                         Boolean?,
		@Json(name = "forks_count") val forksCount:                     Int?,
		@Json(name = "archived") val archived:                          Boolean?,
		@Json(name = "open_issues_count") val openIssuesCount:          Int?,
		@Json(name = "forks") val forks:                                Int?,
		@Json(name = "open_issues") val openIssues:                     Int?,
		@Json(name = "watchers") val watchers:                          Int?,
		@Json(name = "default_branch") val defaultBranch:               String?
)
data class UserPullRequests(
        @Json(name = "login") val login:                                String?,
        @Json(name = "id") val id:                                      Int?,
        @Json(name = "avatar_url") val avatarUrl:                       String?,
        @Json(name = "gravatar_id") val gravatarId:                     String?,
        @Json(name = "url") val url:                                    String?,
        @Json(name = "html_url") val htmlUrl:                           String?,
        @Json(name = "followers_url") val followersUrl:                 String?,
        @Json(name = "following_url") val followingUrl:                 String?,
        @Json(name = "gists_url") val gistsUrl:                         String?,
        @Json(name = "starred_url") val starredUrl:                     String?,
        @Json(name = "subscriptions_url") val subscriptionsUrl:         String?,
        @Json(name = "organizations_url") val organizationsUrl:         String?,
        @Json(name = "repos_url") val reposUrl:                         String?,
        @Json(name = "events_url") val eventsUrl:                       String?,
        @Json(name = "received_events_url") val receivedEventsUrl:      String?,
        @Json(name = "type") val type:                                  String?,
        @Json(name = "site_admin") val siteAdmin:                       Boolean?
)
data class Base(
        @Json(name = "label") val label:                                String?,
        @Json(name = "ref") val ref:                                    String?,
        @Json(name = "sha") val sha:                                    String?,
        @Json(name = "user") val user:                                  UserPullRequests?,
        @Json(name = "repo") val repo:                                  Repo?
)

//data class Owner(
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

//data class User(
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

//data class User(
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



//data class Repo(
//		@Json(name = "id") val id: Int?,
//		@Json(name = "name") val name: String?,
//		@Json(name = "full_name") val fullName: String?,
//		@Json(name = "owner") val owner: Owner?,
//		@Json(name = "private") val private: Boolean?,
//		@Json(name = "html_url") val htmlUrl: String?,
//		@Json(name = "description") val description: String?,
//		@Json(name = "fork") val fork: Boolean?,
//		@Json(name = "url") val url: String?,
//		@Json(name = "forks_url") val forksUrl: String?,
//		@Json(name = "keys_url") val keysUrl: String?,
//		@Json(name = "collaborators_url") val collaboratorsUrl: String?,
//		@Json(name = "teams_url") val teamsUrl: String?,
//		@Json(name = "hooks_url") val hooksUrl: String?,
//		@Json(name = "issue_events_url") val issueEventsUrl: String?,
//		@Json(name = "events_url") val eventsUrl: String?,
//		@Json(name = "assignees_url") val assigneesUrl: String?,
//		@Json(name = "branches_url") val branchesUrl: String?,
//		@Json(name = "tags_url") val tagsUrl: String?,
//		@Json(name = "blobs_url") val blobsUrl: String?,
//		@Json(name = "git_tags_url") val gitTagsUrl: String?,
//		@Json(name = "git_refs_url") val gitRefsUrl: String?,
//		@Json(name = "trees_url") val treesUrl: String?,
//		@Json(name = "statuses_url") val statusesUrl: String?,
//		@Json(name = "languages_url") val languagesUrl: String?,
//		@Json(name = "stargazers_url") val stargazersUrl: String?,
//		@Json(name = "contributors_url") val contributorsUrl: String?,
//		@Json(name = "subscribers_url") val subscribersUrl: String?,
//		@Json(name = "subscription_url") val subscriptionUrl: String?,
//		@Json(name = "commits_url") val commitsUrl: String?,
//		@Json(name = "git_commits_url") val gitCommitsUrl: String?,
//		@Json(name = "comments_url") val commentsUrl: String?,
//		@Json(name = "issue_comment_url") val issueCommentUrl: String?,
//		@Json(name = "contents_url") val contentsUrl: String?,
//		@Json(name = "compare_url") val compareUrl: String?,
//		@Json(name = "merges_url") val mergesUrl: String?,
//		@Json(name = "archive_url") val archiveUrl: String?,
//		@Json(name = "downloads_url") val downloadsUrl: String?,
//		@Json(name = "issues_url") val issuesUrl: String?,
//		@Json(name = "pulls_url") val pullsUrl: String?,
//		@Json(name = "milestones_url") val milestonesUrl: String?,
//		@Json(name = "notifications_url") val notificationsUrl: String?,
//		@Json(name = "labels_url") val labelsUrl: String?,
//		@Json(name = "releases_url") val releasesUrl: String?,
//		@Json(name = "deployments_url") val deploymentsUrl: String?,
//		@Json(name = "created_at") val createdAt: String?,
//		@Json(name = "updated_at") val updatedAt: String?,
//		@Json(name = "pushed_at") val pushedAt: String?,
//		@Json(name = "git_url") val gitUrl: String?,
//		@Json(name = "ssh_url") val sshUrl: String?,
//		@Json(name = "clone_url") val cloneUrl: String?,
//		@Json(name = "svn_url") val svnUrl: String?,
//		@Json(name = "homepage") val homepage: String?,
//		@Json(name = "size") val size: Int?,
//		@Json(name = "stargazers_count") val stargazersCount: Int?,
//		@Json(name = "watchers_count") val watchersCount: Int?,
//		@Json(name = "language") val language: String?,
//		@Json(name = "has_issues") val hasIssues: Boolean?,
//		@Json(name = "has_projects") val hasProjects: Boolean?,
//		@Json(name = "has_downloads") val hasDownloads: Boolean?,
//		@Json(name = "has_wiki") val hasWiki: Boolean?,
//		@Json(name = "has_pages") val hasPages: Boolean?,
//		@Json(name = "forks_count") val forksCount: Int?,
//		@Json(name = "archived") val archived: Boolean?,
//		@Json(name = "open_issues_count") val openIssuesCount: Int?,
//		@Json(name = "forks") val forks: Int?,
//		@Json(name = "open_issues") val openIssues: Int?,
//		@Json(name = "watchers") val watchers: Int?,
//		@Json(name = "default_branch") val defaultBranch: String?
//)

//data class Owner(
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

