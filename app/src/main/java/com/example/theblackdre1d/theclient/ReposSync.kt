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
                        Log.i("NOTIFICATION", "creating notifications")
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