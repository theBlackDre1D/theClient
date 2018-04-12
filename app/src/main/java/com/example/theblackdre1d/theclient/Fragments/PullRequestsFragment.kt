package com.example.theblackdre1d.theclient.Fragments

import android.annotation.SuppressLint
import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.example.theblackdre1d.theclient.Adapters.PullsAdapter
import com.example.theblackdre1d.theclient.Interfaces.GitHubAPI
import com.example.theblackdre1d.theclient.Models.GitHubPullRequest
import com.example.theblackdre1d.theclient.R
import kotlinx.android.synthetic.main.pull_requests_fragment.view.*

@SuppressLint("ValidFragment")
class PullRequestsFragment(private val userName: String, val repoName: String): Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.pull_requests_fragment, container, false)
        val settings = this.activity?.getSharedPreferences("access_token", Context.MODE_PRIVATE)
        val token = settings?.getString("access_token", null)
        if (token != null) {
            val pullsList = GetRepoPulls(userName, repoName, token).execute().get()
            if (pullsList!!.isNotEmpty()) { // TODO Debug if this will work
                val table = rootView.pullsRecyclerView as RecyclerView
                val content = container!!.context
                table.layoutManager = LinearLayoutManager(content, LinearLayout.VERTICAL, false)
                val pullsAdapter = PullsAdapter(pullsList)
                table.adapter = pullsAdapter
            }
            else {
                rootView.noPullsTextView.visibility = View.VISIBLE
                rootView.pullsImageView.visibility = View.VISIBLE
            }
        }

        return rootView
    }
}

class GetRepoPulls(val userName: String, val repoName: String, val token: String): AsyncTask<Unit, Unit, List<GitHubPullRequest>?>() {
    override fun doInBackground(vararg params: Unit?): List<GitHubPullRequest>? {
        val gitHubService = GitHubAPI.create()
        val pulls = gitHubService.getRepoPulls(userName, repoName, token).execute().body()
        return pulls
    }
}