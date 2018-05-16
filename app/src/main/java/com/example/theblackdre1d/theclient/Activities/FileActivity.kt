package com.example.theblackdre1d.theclient.Activities

import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.theblackdre1d.theclient.Models.GitHubRepoContent
import com.example.theblackdre1d.theclient.R
import com.google.gson.Gson
import com.pddstudio.highlightjs.HighlightJsView
import kotlinx.android.synthetic.main.activity_file.*
/*
* Activity use HighlightJsView for show text or code with highlited code.
* One request on GitHub server for fetch file content.
* */
class FileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_file)

        val intent = intent
        val JSONrow = intent.getStringExtra("row")
        val userName = intent.getStringExtra("userName")
        val repoName = intent.getStringExtra("repoName")
//        val settings = application.getSharedPreferences("access_token", Context.MODE_PRIVATE)
//        val token = settings?.getString("access_token", null)
        val gson = Gson()
        val row = gson.fromJson<GitHubRepoContent>(JSONrow, GitHubRepoContent::class.java)
        val file = GetFile(userName, repoName, "master", row.path!!).execute().get()

        val codeView = codeView as HighlightJsView
        codeView.setSource(file)
    }
}
/*
* Fetching file form GitHub server - khttp library
* */
class GetFile(private val userName: String, private val repoName: String, private val branch: String = "master", private val path: String): AsyncTask<Unit, Unit, String>() {
    override fun doInBackground(vararg params: Unit?): String {
        val fileContent = khttp.get("https://raw.githubusercontent.com/$userName/$repoName/$branch/$path")
        return fileContent.text
    }
}