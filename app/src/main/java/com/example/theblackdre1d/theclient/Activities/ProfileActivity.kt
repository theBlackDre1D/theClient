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
import com.example.theblackdre1d.theclient.Adapters.RepositoryAdapter
import com.example.theblackdre1d.theclient.Interfaces.GitHubAPI
import com.example.theblackdre1d.theclient.Models.Repository
import com.example.theblackdre1d.theclient.R
import com.squareup.picasso.Picasso
import khttp.get
import kotlinx.android.synthetic.main.activity_profile.*
import org.json.JSONArray

class ProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        // UI objects
        val profilePicture = circularImageView as ImageView
        val userName = name as TextView
        // Shared preferences initialization
        val sharedPreferences: SharedPreferences = application.getSharedPreferences("access_token", Context.MODE_PRIVATE)
        val userToken: String? = sharedPreferences.getString("access_token",null)
        val testToken = "4d01a496b33f79738b0b5ef0b36e498a86980891"

        // ==== Obtaining information from GitHub ====
        // Obtain user details
        val gitUserDetails = GetUserInfo(testToken).execute().get()
        // ==== Obtain user repos ===
        val gitHubResponse = GetUserRepos(testToken).execute().get()
        val responseAsArray = gitHubResponse as JSONArray






        // ==== Only for TEST purpose ====
        Picasso.with(applicationContext).load("https://avatars1.githubusercontent.com/u/15018356?v=4").into(profilePicture)
        val repositoriesTable = RepositoryTable as RecyclerView
        repositoriesTable.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
        val repositoriesList = ArrayList<Repository>()

        // ==== Testing repositories ====
        for (i in 1..25) {
            repositoriesList.add(Repository("Repo $i", "This is just test repo $i"))
        }

        // ==== Creating table ====
        val repositoriesAdapter = RepositoryAdapter(repositoriesList)
        repositoriesTable.adapter = repositoriesAdapter
    }
}

// =====================================================================================================================

// AsyncTask class -> better to declare it in separate file
class GetUserRepos(private val userToken: String?): AsyncTask<Unit, Unit, JSONArray>() {
    override fun doInBackground(vararg params: Unit?): JSONArray? {
        val url = "https://api.github.com/user/repos?access_token=${userToken}"
        val response = get(url)
        return response.jsonArray
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
        }
        return userDetails
    }
}