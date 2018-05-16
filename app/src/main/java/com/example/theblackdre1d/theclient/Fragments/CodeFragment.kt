package com.example.theblackdre1d.theclient.Fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.theblackdre1d.theclient.Activities.FileActivity
import com.example.theblackdre1d.theclient.Adapters.ContentListAdapter
import com.example.theblackdre1d.theclient.Interfaces.GitHubAPI
import com.example.theblackdre1d.theclient.Models.GitHubBranch
import com.example.theblackdre1d.theclient.Models.GitHubRepoContent
import com.example.theblackdre1d.theclient.R
import com.google.gson.Gson
import kotlinx.android.synthetic.main.content_fragment.view.*

@SuppressLint("ValidFragment")
class CodeFragment(private val userName: String, private val repoName: String, private val token: String): Fragment(), AdapterView.OnItemSelectedListener {

    lateinit var table: RecyclerView
    private var branch = "master"
    private val branchesStrings = mutableListOf<String>()
    //private var homePath: String = ""
    private val previousFolders = ArrayList<String>()
    private var count = 0

    @SuppressLint("ShowToast")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val rootView = inflater.inflate(R.layout.content_fragment, container, false)
        val repoFiles = GetRepoContent(userName, repoName, token).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR).get()
        table = rootView.tableRecyclerView as RecyclerView
        val context =  container!!.context
        table.layoutManager = LinearLayoutManager(context, LinearLayout.VERTICAL, false)
        val contentAdapter = ContentListAdapter(repoFiles, { row: GitHubRepoContent -> rowClicked(row) })
        table.adapter = contentAdapter

        val backButton = rootView.backButton as Button
        backButton.setOnClickListener {
            if (count > 0) {
                val backPath = buildString()
                val previousFiles = GetRepoContent(userName, repoName, token, backPath, branch).execute().get()
                val contentAdapter = ContentListAdapter(previousFiles, { row: GitHubRepoContent -> rowClicked(row) })
                table.adapter = contentAdapter
                table.invalidate() // this refresh table intent
                count--
            }
            Toast.makeText(context, "You're in root directory", Toast.LENGTH_SHORT)
        }

        val branchPick = rootView.branchPickSpinner as Spinner
        val branches = GetRepoBranches(userName, repoName, token).execute().get()
        branches.forEach { it ->
            branchesStrings.add(it.name!!)
        }
        branchPick.onItemSelectedListener = this
        val adapter = ArrayAdapter(context, R.layout.support_simple_spinner_dropdown_item, branchesStrings)
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)
        branchPick.adapter = adapter

        return rootView
    }

    private fun buildString(): String {
        previousFolders.remove(previousFolders.last()) // remove last folder from list
        var result = ""
        previousFolders.forEach { folder ->
            result += "/$folder"
        }
        return result
    }

    private fun rowClicked(row: GitHubRepoContent) {
        if (row.type == "dir") {
            previousFolders.add(row.name!!)
            val potentialNewFiles = GetRepoContent(userName, repoName, token, row.path!!).execute().get()
            if (potentialNewFiles.isNotEmpty()) {
                count++
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

    @SuppressLint("ShowToast")
    private fun refreshContentTable(branchName: String) {
        val newFiles = GetRepoContent(userName, repoName, token, branchName = branchName).execute().get()
        val contentAdapter = ContentListAdapter(newFiles, { row: GitHubRepoContent -> rowClicked(row) })
        table.adapter = contentAdapter
        table.invalidate() // this refresh table intent
        Toast.makeText(context, "Currently on branch: ${branchName}", Toast.LENGTH_SHORT)
    }

    // Spinner selection handling
    override fun onNothingSelected(parent: AdapterView<*>?) {
        return // do nothing§
    }

    @SuppressLint("ShowToast")
    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val potentionalyNewBranch = branchesStrings[position]
        if (potentionalyNewBranch != branch) {
            // TODO: refresh table content based on branch name find how on GitHub API
            refreshContentTable(potentionalyNewBranch)
        } else {
            Toast.makeText(context, "Branch was not changed.", Toast.LENGTH_SHORT)
            return // do nothing
        }
    }
}

class GetRepoContent(private val userName: String, private val repoName: String, private val token: String,
                     private val path: String = "", private val branchName: String? = "master"): AsyncTask<Unit, Unit, List<GitHubRepoContent>>() {
    override fun doInBackground(vararg params: Unit?): List<GitHubRepoContent>? {
        Log.i("Token", token)
        val gitHubService = GitHubAPI.create()
        val respond = gitHubService.getRepoContent(userName, repoName, path, branchName!!,token).execute().body()
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

