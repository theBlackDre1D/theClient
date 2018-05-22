package com.example.theblackdre1d.theclient.Fragments


import android.annotation.SuppressLint
import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import android.widget.LinearLayout
import com.example.theblackdre1d.theclient.Adapters.CommitsAdapter
import com.example.theblackdre1d.theclient.Interfaces.GitHubAPI
import com.example.theblackdre1d.theclient.Models.GitHubCommit
import com.example.theblackdre1d.theclient.R
import kotlinx.android.synthetic.main.commits_fragment.view.*
/*
* One request on GitHub server for commits - only last 30 commits.
* Via commits adapter they are displayed.
* */
@SuppressLint("ValidFragment")
class CommitsFragment(val userName: String, val repositoryName: String) : Fragment() {

    @SuppressLint("CommitPrefEdits")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
//        val token = Token.getToken()
        val settings = this.activity?.getSharedPreferences("access_token", Context.MODE_PRIVATE)
        val token = settings?.getString("access_token", null)
        val rootView = inflater.inflate(R.layout.commits_fragment, container, false)
        //val userName: String = userName
        //val repositoryName: String = repositoryName
        val commitsList = GetReposCommits(token!!, userName, repositoryName).execute().get()
        //val syncPreferencies = activity?.getSharedPreferences("sync", Context.MODE_PRIVATE)

        val table = rootView.commitsRecyclerView as RecyclerView
        val context = container!!.context
        table.layoutManager = LinearLayoutManager(context, LinearLayout.VERTICAL, false)
        val commitsAdapter = CommitsAdapter(commitsList)
        //val context = table.context
        val controller: LayoutAnimationController = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_from_bottom)
        table.layoutAnimation = controller
        table.scheduleLayoutAnimation()
        table.adapter = commitsAdapter
        return rootView
    }
}
/*
* AsyncTask for get commits from GitHub.
* Return list of commits objects.
* */
class GetReposCommits(private val userToken: String, private val userName: String, private val repoName: String): AsyncTask<Unit, Unit, List<GitHubCommit>>() {
    override fun doInBackground(vararg params: Unit?): List<GitHubCommit>? {
        Log.i("TOKEN", userToken)
        val gitHubService = GitHubAPI.create()
        val commits = gitHubService.getRepoCommits(userName, repoName, userToken).execute().body()
        return commits
    }
}
