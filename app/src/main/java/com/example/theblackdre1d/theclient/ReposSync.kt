package com.example.theblackdre1d.theclient

import android.app.job.JobParameters
import android.app.job.JobService
import android.content.Context
import android.os.Build
import android.support.annotation.RequiresApi
import android.util.Log
import com.example.theblackdre1d.theclient.Activities.GetUserRepos
import com.example.theblackdre1d.theclient.Fragments.GetRepoPulls
import com.example.theblackdre1d.theclient.Interfaces.GitHubAPI
import com.example.theblackdre1d.theclient.Models.GitHubRepository
import com.example.theblackdre1d.theclient.Models.SavedRepository
import com.github.salomonbrys.kotson.toJson
import com.google.gson.Gson
import com.pixplicity.easyprefs.library.Prefs

@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
class ReposSync : JobService() {
    override fun onStopJob(params: JobParameters?): Boolean {
        checkNewCommits()
        checkNewPullRequests()
        return true
    }

    private fun checkNewPullRequests() {
        Log.i("SYNC", "Syncing pull requests")

//        val syncPreferencies = application.getSharedPreferences("sync", Context.MODE_PRIVATE)
        val settings = application.getSharedPreferences("access_token", Context.MODE_PRIVATE)
        val token = settings?.getString("access_token", null)
        token?.let {
            val userRepos = GetUserRepos(token).execute().get()
            var savedRepositories: ArrayList<SavedRepository> = ArrayList()
            var loopCondition = true
            var count = 0
            while(loopCondition) {
                val savedRepoInJson = Prefs.getString("${userRepos!![count].name}", null)
                if (savedRepoInJson != null) {
                    val gson = Gson()
                    val savedRepo = gson.fromJson<SavedRepository>(savedRepoInJson, SavedRepository::class.java)
                    savedRepositories.add(savedRepo)
                    count++
                } else {
                    loopCondition = false
                }
            }
            val syncPreferences = application.getSharedPreferences("sync", Context.MODE_PRIVATE)
            val userName: String? = syncPreferences.getString("userName", null)
            userRepos?.forEach { repo ->
                val pullRequests = GetRepoPulls(userName!!, repo.name!!, token).execute().get()
                var savedRepo: SavedRepository? = null
                savedRepositories.forEach { savedRepository ->
                    if (repo.name == savedRepository.repositoryName) {
                        savedRepo = savedRepository
                    }
                }
                pullRequests?.let {
                    if (pullRequests.last() != savedRepo?.lastPullRequest) {
                        //TODO Create notification
                    }
                }
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