package com.example.theblackdre1d.theclient.Fragments

import android.os.AsyncTask
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.theblackdre1d.theclient.Interfaces.GitHubAPI
import com.example.theblackdre1d.theclient.Models.GitHubPullRequests
import com.example.theblackdre1d.theclient.R

class PullRequestsFragment(): Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.pull_requests_fragment, container, false)

        return rootView
    }
}

class GetRepoPulls(val userName: String, val repoName: String, val token: String): AsyncTask<Unit, Unit, List<GitHubPullRequests>?>() {
    override fun doInBackground(vararg params: Unit?): List<GitHubPullRequests>? {
        val gitHubService = GitHubAPI.create()
        val pulls = gitHubService.getRepoPulls(userName, repoName, token).execute().body()
        return pulls
    }
}