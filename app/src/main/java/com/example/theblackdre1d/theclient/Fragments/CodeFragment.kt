package com.example.theblackdre1d.theclient.Fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.Toast
import com.example.theblackdre1d.theclient.Activities.FileActivity
import com.example.theblackdre1d.theclient.Adapters.ContentListAdapter
import com.example.theblackdre1d.theclient.Interfaces.GitHubAPI
import com.example.theblackdre1d.theclient.Models.GitHubBranch
import com.example.theblackdre1d.theclient.Models.GitHubRepoContent
import com.example.theblackdre1d.theclient.R
import com.google.gson.Gson
import kotlinx.android.synthetic.main.content_fragment.view.*

@SuppressLint("ValidFragment")
class CodeFragment(val userName: String, val repoName: String, val token: String): Fragment() {

    lateinit var table: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val rootView = inflater.inflate(R.layout.content_fragment, container, false)
        val repoFiles = GetRepoContent(userName, repoName, token).execute().get()
        table = rootView.tableRecyclerView as RecyclerView
        val context =  container!!.context
        table.layoutManager = LinearLayoutManager(context, LinearLayout.VERTICAL, false)
        val contentAdapter = ContentListAdapter(repoFiles, { row: GitHubRepoContent -> rowClicked(row) })
        table.adapter = contentAdapter
        val backButton = rootView.backFloatingButton as FloatingActionButton
        backButton.setOnClickListener {
            Toast.makeText(context, "Back button working!", Toast.LENGTH_LONG).show()
        }

//        val branchPick = rootView.branchPickSpinner as Spinner
//        val branchesStrings = mutableListOf<String>()
//        val branches = GetRepoBranches(userName, repoName, token).execute().get()
//        branches.forEach { it ->
//            branchesStrings.add(it.name!!)
//        }
//        val adapter = ArrayAdapter(context, R.layout.support_simple_spinner_dropdown_item, branchesStrings)
//        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)
//        branchPick.adapter = adapter

        return rootView
    }

    private fun rowClicked(row: GitHubRepoContent) {
        if (row.type == "dir") {
            val potentialNewFiles = GetRepoContent(userName, repoName, token, row.path!!).execute().get()
            if (potentialNewFiles.isNotEmpty()) {
                val contentAdapter = ContentListAdapter(potentialNewFiles, { row: GitHubRepoContent -> rowClicked(row) })
                table.adapter = contentAdapter
                table.invalidate() // this refresh table intent
            } else {
                Toast.makeText(context, "Folder is empty", Toast.LENGTH_LONG).show()
            }
        } else {
            //otvorit subor
            val gson = Gson()
            val JSONrow = gson.toJson(row)
            val intent = Intent(context, FileActivity::class.java)
            intent.putExtra("row", JSONrow)
            intent.putExtra("userName", userName)
            intent.putExtra("repoName", repoName)
            startActivity(intent)
        }
    }
}

class GetRepoContent(private val userName: String, private val repoName: String, private val token: String, val path: String = ""): AsyncTask<Unit, Unit, List<GitHubRepoContent>>() {
    override fun doInBackground(vararg params: Unit?): List<GitHubRepoContent>? {
        val gitHubService = GitHubAPI.create()
        val respond = gitHubService.getRepoContent(userName, repoName, path,token).execute().body()
        return respond
    }
}

class GetRepoBranches(private val userName: String, private val repoName: String, private val token: String): AsyncTask<Unit, Unit, List<GitHubBranch>>() {
    override fun doInBackground(vararg params: Unit?): List<GitHubBranch>? {
        val gitHubService = GitHubAPI.create()
        val respond = gitHubService.getRepoBranches(userName, repoName, token).execute().body()
        return respond
    }
}

