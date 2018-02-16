package com.example.theblackdre1d.theclient.Fragments


import android.annotation.SuppressLint
import android.os.AsyncTask
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.example.theblackdre1d.theclient.Adapters.CommitsAdapter
import com.example.theblackdre1d.theclient.Interfaces.GitHubAPI
import com.example.theblackdre1d.theclient.Models.GitHubCommit
import com.example.theblackdre1d.theclient.R
import com.example.theblackdre1d.theclient.Token
import kotlinx.android.synthetic.main.commits_fragment.view.*

@SuppressLint("ValidFragment")
class CommitsFragment(val userName: String, val repositoryName: String) : Fragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val token = Token.getToken()
        val rootView = inflater!!.inflate(R.layout.commits_fragment, container, false)
        val userName: String = userName
        val repositoryName: String = repositoryName
        val commitsList = GetReposCommits(token, userName, repositoryName).execute().get()
        val table = rootView.commitsRecyclerView as RecyclerView
        val context = container!!.context
        table.layoutManager = LinearLayoutManager(context, LinearLayout.VERTICAL, false)
        val commitsAdapter = CommitsAdapter(commitsList)
        table.adapter = commitsAdapter
        return rootView
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}

class GetReposCommits(private val userToken: String, private val userName: String, private val repoName: String): AsyncTask<Unit, Unit, List<GitHubCommit>>() {
    override fun doInBackground(vararg params: Unit?): List<GitHubCommit>? {
        val gitHubService = GitHubAPI.create()
        val commits = gitHubService.getRepoCommits(userName, repoName, userToken).execute().body()
        return commits
    }
}
