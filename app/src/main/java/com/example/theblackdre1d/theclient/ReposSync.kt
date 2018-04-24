package com.example.theblackdre1d.theclient

import android.app.job.JobParameters
import android.app.job.JobService
import android.content.Context
import android.os.Build
import android.support.annotation.RequiresApi
import android.util.Log
import com.example.theblackdre1d.theclient.Interfaces.GitHubAPI
import com.example.theblackdre1d.theclient.Models.GitHubRepository

@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
class ReposSync : JobService() {
    override fun onStopJob(params: JobParameters?): Boolean {
        checkNewCommits()
        checkNewPullRequests()
        return true
    }

    private fun checkNewPullRequests() {
        Log.i("SYNC", "Syncing pull requests")
        val syncPreferencies = application.getSharedPreferences("sync", Context.MODE_PRIVATE)
        val settings = application.getSharedPreferences("access_token", Context.MODE_PRIVATE)
        val token = settings?.getString("access_token", null)
        token?.let {
            val gitHubService = GitHubAPI.create()
            val userRepos = gitHubService.getUserRepos(token) as List<*>
            userRepos.forEach { repo ->
                repo as GitHubRepository
                val pullRequests = gitHubService.getRepoPulls(token,)
            }
        }
    }

    private fun checkNewCommits() {
        Log.i("SYNC", "Syncing commits")
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onStartJob(params: JobParameters?): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}