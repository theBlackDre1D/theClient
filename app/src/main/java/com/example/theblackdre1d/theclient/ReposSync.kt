package com.example.theblackdre1d.theclient

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.job.JobParameters
import android.app.job.JobService
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import android.support.annotation.RequiresApi
import android.util.Log
import com.example.theblackdre1d.theclient.Activities.GetUserRepos
import com.example.theblackdre1d.theclient.Activities.RepoListActivity
import com.example.theblackdre1d.theclient.Fragments.GetRepoPulls
import com.example.theblackdre1d.theclient.Fragments.GetReposCommits
import com.example.theblackdre1d.theclient.Interfaces.GitHubAPI
import com.example.theblackdre1d.theclient.Models.GitHubRepository
import com.example.theblackdre1d.theclient.Models.SavedRepository
import com.github.salomonbrys.kotson.toJson
import com.google.gson.Gson
import com.pixplicity.easyprefs.library.Prefs

@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
class ReposSync : JobService() {

    override fun onStopJob(params: JobParameters?): Boolean {
        Log.i("CANCELED", "Job was canceled before finish")
        return true
    }

    private fun checkNewPullRequests() {
        Log.i("SYNC", "Syncing pull requests")
        val settings = application.getSharedPreferences("access_token", Context.MODE_PRIVATE)
        val token = settings?.getString("access_token", null)
        token?.let {
            val userRepos = GetUserRepos(token).execute().get()
            var savedRepositories: ArrayList<SavedRepository> = ArrayList()
            var count = 0
            while(count < userRepos!!.size) {
                val repoName = userRepos[count].name
                val savedRepoInJson = Prefs.getString("${userRepos[count].name}", null)
                if (savedRepoInJson != null) {
                    val gson = Gson()
                    val savedRepo = gson.fromJson<SavedRepository>(savedRepoInJson, SavedRepository::class.java)
                    savedRepositories.add(savedRepo)
                    count++
                }
            }
            //val syncPreferences = application.getSharedPreferences("sync", Context.MODE_PRIVATE)
            val userName = Prefs.getString("userName", null)
            userRepos.forEach { repo ->
                //TODO FInd why ap crash here
                val pullRequests = GetRepoPulls(userName!!, repo.name!!, token).execute().get()
                if (pullRequests!!.isNotEmpty()) {
                    var savedRepo: SavedRepository? = null
                    savedRepositories.forEach { savedRepository ->
                        if (repo.name == savedRepository.repositoryName) {
                            savedRepo = savedRepository
                        }
                    }
                    if (pullRequests.last() != savedRepo?.lastPullRequest) {
                        //TODO Create notification
                        Log.i("PULLS", "creating notifications")
                        createNotification(
                                "New pull request in ${repo.name}",
                                "You have new pull request in you repository!"
                        )
                    }
                }
            }
        }
    }

    private fun checkNewCommits() {
        Log.i("SYNC", "Syncing commits")
        val settings = application.getSharedPreferences("access_token", Context.MODE_PRIVATE)
        val token = settings?.getString("access_token", null)
        token?.let {
            val userRepos = GetUserRepos(token).execute().get()
            var savedRepositories: ArrayList<SavedRepository> = ArrayList()
            var count = 0
            while(count < userRepos!!.size) {
                val repoName = userRepos[count].name //only for debug purposes
                val savedRepoInJson = Prefs.getString("${userRepos[count].name}", null)
                if (savedRepoInJson != null) {
                    val gson = Gson()
                    val savedRepo = gson.fromJson<SavedRepository>(savedRepoInJson, SavedRepository::class.java)
                    savedRepositories.add(savedRepo)
                    count++
                }
            }
            val userName = Prefs.getString("userName", null)
            userRepos.forEach { repo ->
                val repoCommits = GetReposCommits(token, userName, repo.name!!).execute().get()
                if (repoCommits.isNotEmpty()) {
                    var savedRepo: SavedRepository? = null
                    savedRepositories.forEach { savedRepository ->
                        if (repo.name == savedRepository.repositoryName) {
                            savedRepo = savedRepository
                        }
                    }
                    val pontentionalyNewCommit = repoCommits.first().commit?.message
                    val currentLastCommit = savedRepo?.lastCommit?.commit?.message
                    if (repoCommits.first() != savedRepo?.lastCommit) {
                        Log.i("COMMITS", "creating notifications")
                        //TODO: create notification
                        createNotification(
                                "New commit/s in ${repo.name}",
                                "You have new commit/s in your repository!"
                        )
                    }
                }
            }
        }
    }

    @SuppressLint("PrivateResource")
    private fun createNotification(title: String, text: String) {
        val notificationManager: NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notificationChannel: NotificationChannel
        val builder: Notification.Builder
        val channelID = "com.example.theblackdre1d.theclient"

        val intent = Intent(this, RepoListActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationChannel = NotificationChannel(channelID, text, NotificationManager.IMPORTANCE_HIGH)
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.GREEN
            notificationChannel.setShowBadge(true)
            notificationChannel.enableVibration(true)
            notificationChannel.setShowBadge(true)

            notificationManager.createNotificationChannel(notificationChannel)

            builder = Notification.Builder(this, channelID)
                    .setContentTitle(title)
                    .setContentText(text)
                    .setSmallIcon(R.drawable.notification_icon_background)
                    .setLargeIcon(BitmapFactory.decodeResource(this.resources, R.drawable.github))
                    .setContentIntent(pendingIntent)
        } else {
            builder = Notification.Builder(this)
                    .setContentTitle(title)
                    .setContentText(text)
                    .setSmallIcon(R.drawable.navigation_empty_icon)
                    .setLargeIcon(BitmapFactory.decodeResource(this.resources, R.drawable.github))
                    .setContentIntent(pendingIntent)
        }
        notificationManager.notify(1234, builder.build())
    }

    override fun onStartJob(params: JobParameters?): Boolean {
        Log.i("SYNC", "Started background sync")
        checkNewCommits()
        checkNewPullRequests()
        jobFinished(params, false)
        return true
    }
}