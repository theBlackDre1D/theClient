package com.example.theblackdre1d.theclient.Activities

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.os.AsyncTask
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.support.annotation.RequiresApi
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.example.theblackdre1d.theclient.Adapters.RepoListAdapter
import com.example.theblackdre1d.theclient.Fragments.GetRepoPulls
import com.example.theblackdre1d.theclient.Fragments.GetReposCommits
import com.example.theblackdre1d.theclient.Interfaces.GitHubAPI
import com.example.theblackdre1d.theclient.Models.*
import com.example.theblackdre1d.theclient.R
import com.example.theblackdre1d.theclient.R.id.testButton
import com.google.gson.Gson
import com.pixplicity.easyprefs.library.Prefs
import com.squareup.picasso.Picasso
import khttp.get
import kotlinx.android.synthetic.main.activity_repo_list.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.yesButton

class RepoListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repo_list)
        val conectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = conectivityManager.activeNetworkInfo
//        networkInfo?.let {
//
//        }

        if (networkInfo == null) {
            alert("You have to be connected to proceed") {
                yesButton({
                    redirectToSettings()
                })
            }.show()
        }
    }

    @SuppressLint("CommitPrefEdits")
    override fun onResume() {
        super.onResume()
        val conectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = conectivityManager.activeNetworkInfo
        networkInfo?.let {
            Prefs.putBoolean("skip", false)
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
            val syncPreferences = application.getSharedPreferences("sync", Context.MODE_PRIVATE)
            val prefsEditor = syncPreferences.edit()
            // ==== Obtaining information from GitHub ====
            // Obtain user details
            val gitUserDetails = GetUserInfo(userToken).execute().get()

            userName.text = gitUserDetails["userName"] as String
            createdAt.text = gitUserDetails["createdAt"] as String
            Picasso.with(applicationContext).load(gitUserDetails["avatarURL"] as String).into(profilePicture)
            Prefs.putString("userName", gitUserDetails["userName"] as String)
            // ==== Obtain user repos ===
            val gitHubUserRepos = GetUserRepos(userToken).execute().get()
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
                doAsync {
                    saveInformationForSync(gitHubUserRepos, userToken!!)
                }
            }
            // ==== Creating table ====
            val repositoriesAdapter = RepoListAdapter(repositoriesList)
            repositoriesTable.adapter = repositoriesAdapter
        }

        if (networkInfo == null) {
            alert("You have to be connected to proceed") {
                yesButton{
                    redirectToSettings()
                }
            }.show()
        }

        val testButton = testButton as Button
        testButton.setOnClickListener {
            val settings = application.getSharedPreferences("access_token", Context.MODE_PRIVATE)
            val token = settings?.getString("access_token", null)
            token?.let {
                val userRepos = GetUserRepos(token).execute().get()
                var savedRepositories: ArrayList<SavedRepository> = ArrayList()
                var count = 0
                while(count < userRepos!!.size) {
                    val repoName = userRepos[count].name //only for debug purposes
                    val savedRepoInJson = Prefs.getString("${userRepos[count].name}", null)
                    if (savedRepoInJson != null) {
                        val gson = Gson()
                        val savedRepo = gson.fromJson<SavedRepository>(savedRepoInJson, SavedRepository::class.java)
                        savedRepositories.add(savedRepo)
                        count++
                    }
                }
                val userName = Prefs.getString("userName", null)
                userRepos.forEach { repo ->
                    val repoCommits = GetReposCommits(token, userName, repo.name!!).execute().get()
                    if (repoCommits.isNotEmpty()) {
                        var savedRepo: SavedRepository? = null
                        savedRepositories.forEach { savedRepository ->
                            if (repo.name == savedRepository.repositoryName) {
                                savedRepo = savedRepository
                            }
                        }
                        val pontentionalyNewCommit = repoCommits.first().commit?.message
                        val currentLastCommit = savedRepo?.lastCommit?.commit?.message
                        if (repoCommits.first() != savedRepo?.lastCommit) {
                            Log.i("COMMITS", "creating notifications")
                            //TODO: create notification
                        }
                    }
                }
            }
        }
    }

    private fun saveInformationForSync(repositories: List<GitHubRepository>, token: String) {
        val userName = Prefs.getString("userName", null)
        val gson = Gson()
        val jsonRepoList = gson.toJson(repositories)
        Prefs.putString("repositories", jsonRepoList)

        repositories.forEach { repo ->
            val commits = GetReposCommits(token, userName, repo.name!!).execute().get()//gitHubService.getRepoCommits(Prefs.getString("userName", null), repo.name!!, token).execute().body()
            //val commitsCount = commits.size
            val pullRequests = GetRepoPulls(userName, repo.name, token).execute().get()
            var lastPull: GitHubPullRequest? = null
            if (pullRequests?.isNotEmpty()!!) {
                lastPull = pullRequests.last()
            }
            var lastCommit: GitHubCommit? = null
            if (commits.isNotEmpty()) {
                lastCommit = commits.first() // first commit is last commit from time perspective
            }
            val savingRepo = SavedRepository(
                    lastCommit,
                    lastPull,
                    repo.name,
                    userName)
            val jsonRepo = gson.toJson(savingRepo)
            Prefs.putString("${repo.name}", jsonRepo)
        }
    }

    private fun redirectToSettings() {
        val intent = Intent(Settings.ACTION_WIRELESS_SETTINGS)
        startActivity(intent)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onBackPressed() {
        moveTaskToBack(true)
//        alert("Do you wanna log off?") {
//            title = "Log off"
//            yesButton {
//                Prefs.putBoolean("skip", false)
//                val sharedPreferences: SharedPreferences = application.getSharedPreferences("access_token", Context.MODE_PRIVATE)
//                sharedPreferences.edit().remove("access_token").apply()
//                super.onBackPressed()
//            }
//            noButton {
//                toast("Nothing happened.")
//            }
//        }.show()
//        Prefs.putBoolean("skip", false)
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

class GetUserInfo(private val token: String?): AsyncTask<Unit, Unit, HashMap<String, Any>>() {
    override fun doInBackground(vararg params: Unit?): HashMap<String, Any> {
        val response = get("https://api.github.com/user?access_token=$token")
        val JSONresponse = response.jsonObject
        val avaterURL = JSONresponse.getString("avatar_url")
        val userName = JSONresponse.getString("login")
        val createdAt = JSONresponse.getString("created_at")
        val userDetails = hashMapOf<String, Any>()
        userDetails.put("avatarURL", avaterURL)
        userDetails.put("userName", userName)
        userDetails.put("createdAt", createdAt)
        return userDetails
    }
}