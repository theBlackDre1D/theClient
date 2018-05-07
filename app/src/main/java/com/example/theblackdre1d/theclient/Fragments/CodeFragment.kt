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
import android.widget.ListView
import android.widget.Toast
import com.example.theblackdre1d.theclient.Adapters.ContentListAdapter
import com.example.theblackdre1d.theclient.Interfaces.GitHubAPI
import com.example.theblackdre1d.theclient.Models.GitHubRepoContent
import com.example.theblackdre1d.theclient.R
import com.example.theblackdre1d.theclient.Token
import com.pixplicity.easyprefs.library.Prefs
import kotlinx.android.synthetic.main.content_fragment.view.*

@SuppressLint("ValidFragment")
class CodeFragment(val userName: String, val repoName: String, val token: String): Fragment() {
    
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val rootView = inflater.inflate(R.layout.content_fragment, container, false)
        val repoFiles = GetRepoContent(userName, repoName, token).execute().get()
        val table = rootView.tableRecyclerView as RecyclerView
        val context =  container!!.context
        table.layoutManager = LinearLayoutManager(context, LinearLayout.VERTICAL, false)
        val contentAdapter = ContentListAdapter(repoFiles, { row: GitHubRepoContent -> rowClicked(row) })
        table.adapter = contentAdapter
        return rootView
    }

    private fun rowClicked(row: GitHubRepoContent) {
        Toast.makeText(context, "Clicked row: ${row.name}", Toast.LENGTH_LONG).show()
    }
}

class GetRepoContent(private val userName: String, private val repoName: String, private val token: String): AsyncTask<Unit, Unit, List<GitHubRepoContent>>() {
    override fun doInBackground(vararg params: Unit?): List<GitHubRepoContent>? {
        val gitHubService = GitHubAPI.create()
        val respond = gitHubService.getRepoContent(userName, repoName, "",token).execute().body()
        return respond
    }
}