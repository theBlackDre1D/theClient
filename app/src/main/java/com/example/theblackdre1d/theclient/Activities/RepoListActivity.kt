package com.example.theblackdre1d.theclient.Activities

import android.content.Context
import android.content.SharedPreferences
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.example.theblackdre1d.theclient.Adapters.RepoListAdapter
import com.example.theblackdre1d.theclient.Interfaces.GitHubAPI
import com.example.theblackdre1d.theclient.Models.GitHubRepository
import com.example.theblackdre1d.theclient.Models.Repository
import com.example.theblackdre1d.theclient.R
import com.example.theblackdre1d.theclient.Token
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_profile.*

class RepoListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        // UI objects
        val profilePicture = circularImageView as ImageView
        val userName = name as TextView
        val repositoriesTable = RepositoryTable as RecyclerView
        val createdAt = createdAtTextView as TextView
        repositoriesTable.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
        val repositoriesList = ArrayList<Repository>()

        // Shared preferences initialization
        val sharedPreferences: SharedPreferences = application.getSharedPreferences("access_token", Context.MODE_PRIVATE)
        val userToken: String? = sharedPreferences.getString("access_token",null)

        val testToken = Token.getToken()
        // ==== Obtaining information from GitHub ====
            // Obtain user details
        val gitUserDetails = GetUserInfo(testToken).execute().get()
        userName.text = gitUserDetails["userName"] as String
        createdAt.text = gitUserDetails["createdAt"] as String
        Picasso.with(applicationContext).load(gitUserDetails["avatarURL"] as String).into(profilePicture)

        // ==== Obtain user repos ===
        val gitHubUserRepos = GetUserRepos(testToken).execute().get()
//        gitHubUserRepos?.let {
        if (gitHubUserRepos != null) {
            for (repo in gitHubUserRepos) {
                var description: String
                val nameOfRepo = repo.name
                val language = repo.language
                if (repo.description == null) {
                    description = "No description provided"
                } else {
                    description = repo.description as String
                }
                val repository = Repository(nameOfRepo!!, description, language!!, gitUserDetails["userName"] as String)
                repositoriesList.add(repository)
            }
        }
//        }

        // ==== Creating table ====
        val repositoriesAdapter = RepoListAdapter(repositoriesList)
        repositoriesTable.adapter = repositoriesAdapter
    }
}

// =====================================================================================================================

// AsyncTask class -> better to declare it in separate file
class GetUserRepos(private val userToken: String?): AsyncTask<Unit, Unit, List<GitHubRepository>?>() {
    override fun doInBackground(vararg params: Unit?): List<GitHubRepository>? {
        val gitHubService = GitHubAPI.create()
        val gitRespond = gitHubService.getUserRepos(userToken!!).execute().body()
        return gitRespond
    }
}

class GetUserInfo(private val token: String): AsyncTask<Unit, Unit, HashMap<String, Any>>() {
    override fun doInBackground(vararg params: Unit?): HashMap<String, Any>? {
        val gitHubService = GitHubAPI.create()
        val gitRespond = gitHubService.getUser(token).execute().body()
        Log.d("Respond", "User from github: ${gitRespond.toString()}")
        val userDetails = hashMapOf<String, Any>()
        gitRespond?.let {
            userDetails["userName"] = gitRespond.login
            userDetails.put("avatarURL", gitRespond.avatar_url)
            userDetails["createdAt"] = gitRespond.created_at
        }
        return userDetails
    }
}