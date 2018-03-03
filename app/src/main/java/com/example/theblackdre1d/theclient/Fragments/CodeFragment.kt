package com.example.theblackdre1d.theclient.Fragments

import android.annotation.SuppressLint
import android.os.AsyncTask
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import com.example.theblackdre1d.theclient.Interfaces.GitHubAPI
import com.example.theblackdre1d.theclient.Models.GitHubRepoContent
import com.example.theblackdre1d.theclient.R
import kotlinx.android.synthetic.main.content_fragment.view.*
import retrofit2.Call

@SuppressLint("ValidFragment")
class CodeFragment(val userName: String, val repoName: String, val token: String): Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val rootView = inflater.inflate(R.layout.content_fragment, container, false)
        val listView = rootView.contentListView as ListView
        return rootView
    }
}

class GetRepoContent(private val userName: String, private val repoName: String, private val token: String): AsyncTask<Unit, Unit, Call<List<GitHubRepoContent>>>() {
    override fun doInBackground(vararg params: Unit?): Call<List<GitHubRepoContent>> {
        val gitHubService = GitHubAPI.create()
        return gitHubService.getRepoContent(userName, repoName, token)
    }
}