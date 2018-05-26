package com.example.theblackdre1d.theclient.Activities

import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.AsyncTask
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.support.annotation.RequiresApi
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import android.widget.*
import com.example.theblackdre1d.theclient.Adapters.RepoListAdapter
import com.example.theblackdre1d.theclient.Fragments.GetRepoPulls
import com.example.theblackdre1d.theclient.Fragments.GetReposCommits
import com.example.theblackdre1d.theclient.Interfaces.GitHubAPI
import com.example.theblackdre1d.theclient.Models.*
import com.example.theblackdre1d.theclient.R
import com.example.theblackdre1d.theclient.ReposSync
import com.github.pwittchen.reactivenetwork.library.rx2.ConnectivityPredicate
import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork
import com.google.gson.Gson
import com.pixplicity.easyprefs.library.Prefs
import com.squareup.picasso.Picasso
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import khttp.get
import kotlinx.android.synthetic.main.activity_repo_list.*
import kotlinx.android.synthetic.main.repository_layout.*
import kotlinx.coroutines.experimental.launch
import org.jetbrains.anko.*
import java.util.*
import kotlin.collections.ArrayList

/*
* Class handle fetching and showing user repos
* */
class RepoListActivity : AppCompatActivity() {

    private var gitHubUserRepos: List<GitHubRepository>? = null
    private lateinit var userToken: String
    private val favoritesRepos = ArrayList<String>()
    /*
    * Checking internet connection - if connected OK else show alert and redirect to network settings.
    * Initialization of UI elements.
    * Request on GitHub server for fetch user information and display profile picture with Picasso library.
    * Request on GitHub server for user's repositories and display them using RepoListAdapter.kt.
    * Setting information for sync using saveInformationForSync().
    * */
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repo_list)
//        val progressBar = progressBar as ProgressBar
//        progressBar.visibility = View.GONE
        //scheduleSync()

        ReactiveNetwork.observeNetworkConnectivity(applicationContext)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(ConnectivityPredicate.hasState(NetworkInfo.State.DISCONNECTED))
                .subscribe {
                    alert("You have to be connected to proceed") {
                        title = "Not connected!"
                        positiveButton("Go to settings") {
                            redirectToSettings()
                        }
                        negativeButton("Cancel") {
                            // do nothing
                            Prefs.putBoolean("notified", true)
                        }
                    }.show()
                }
        // Shared preferences initialization
        val sharedPreferences: SharedPreferences = application.getSharedPreferences("access_token", Context.MODE_PRIVATE)
        userToken = sharedPreferences.getString("access_token",null)
        gitHubUserRepos = GetUserRepos(userToken).execute().get()

//        gitHubUserRepos?.let {
//            launch {
//                saveInformationForSync(gitHubUserRepos!!, userToken)
//            }
//        }

        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        networkInfo?.let {
            Prefs.putBoolean("skip", false)
            // UI objects
            val profilePicture = circularImageView as ImageView
            val userName = name as TextView
            val repositoriesTable = RepositoryTable as RecyclerView
            val createdAt = createdAtTextView as TextView
            repositoriesTable.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
            val repositoriesList = ArrayList<Repository>()

            // ==== Obtaining information from GitHub ====
            // Obtain user details
            var name = Prefs.getString("userName", null)
            var avatarUrl = Prefs.getString("avatarUrl", null)
            var createdDate = Prefs.getString("createdAt", null)
            if (name == null || avatarUrl == null || createdDate == null) {
                val gitUserDetails = GetUserInfo(userToken).execute().get()
                if (gitUserDetails.login == "") {
                    Toast.makeText(applicationContext, "You have to re-login.", Toast.LENGTH_SHORT).show()
                    Prefs.putBoolean("skip", false)
                    Prefs.putBoolean("skipScheduleSync", false)
                    val sharedPreferences: SharedPreferences = application.getSharedPreferences("access_token", Context.MODE_PRIVATE)
                    sharedPreferences.edit().remove("access_token").apply()
                    nullUserDetails()
                    super.onBackPressed()
                }
                name = gitUserDetails.login
                avatarUrl = gitUserDetails.avatarUrl
                createdDate = gitUserDetails.createdAt
                try {
                    gitUserDetails?.let {
                        userName.text = name
                        createdAt.text = createdDate
                        Picasso.with(applicationContext).load(avatarUrl).into(profilePicture)
                        Prefs.putString("userName", name)
                        Prefs.putString("avatarUrl", avatarUrl)
                        Prefs.putString("createdAt", createdDate)
                    }
                } catch (exception: Exception) {
                    Toast.makeText(applicationContext, "Error while loading user information", Toast.LENGTH_SHORT).show()
                }
            } else {
                userName.text = name
                createdAt.text = createdDate
                Picasso.with(applicationContext).load(avatarUrl).into(profilePicture)
            }

            // ==== Obtain user repos ===

            if (gitHubUserRepos != null) {
                for (repo in gitHubUserRepos!!) {
                    var description: String?
                    val nameOfRepo = repo.name
                    val language = repo.language
                    if (repo.description == null) {
                        description = "No description provided"
                    } else {
                        description = repo.description as String
                    }
                    val repository = Repository(nameOfRepo ?: "No-name", description, language ?: "-", name)
                    repositoriesList.add(repository)
                }
            } else {
                Toast.makeText(applicationContext, "You have to re-login.", Toast.LENGTH_SHORT).show()
                Prefs.putBoolean("skip", false)
                Prefs.putBoolean("skipScheduleSync", false)
                val sharedPreferences: SharedPreferences = application.getSharedPreferences("access_token", Context.MODE_PRIVATE)
                sharedPreferences.edit().remove("access_token").apply()
                nullUserDetails()
                super.onBackPressed()
            }
            // ==== Creating table ====
            val repositoriesAdapter = RepoListAdapter(repositoriesList)
            val context = repositoriesTable.context
            val controller: LayoutAnimationController = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_from_right)
            repositoriesTable.layoutAnimation = controller
            repositoriesTable.scheduleLayoutAnimation()
            repositoriesTable.adapter = repositoriesAdapter
        }
    }

    /*
    * One input parameter - list of users's repositories.
    * Saving last commit and last pull request for every repository. Object saved in JSON format in SharedPreferencies.
    *
    * */
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
    /*
    * User is redirected to network settings after this method was called.
    * */
    private fun redirectToSettings() {
        val intent = Intent(Settings.ACTION_WIRELESS_SETTINGS)
        startActivity(intent)
    }
    /*
    * Closing application.
    * */
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onBackPressed() {
        moveTaskToBack(true)
    }

    /*
    * Setting up sync in background thread using AsyncTask.
    * */
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun scheduleSync() {
        val componentName = ComponentName(this, ReposSync::class.java)
        val scheduler = getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
        SetupSync(componentName, scheduler).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR).get()

    }
    /*
    * Logging out user - delete token from SharedPreferencies and redirect to MainActivity.kt
    * */
    fun logOut(view: View) {
        alert("Do you wanna log off?") {
            title = "Log off"
            yesButton {
                Prefs.putBoolean("skip", false)
                Prefs.putBoolean("skipScheduleSync", false)
                val sharedPreferences: SharedPreferences = application.getSharedPreferences("access_token", Context.MODE_PRIVATE)
                sharedPreferences.edit().remove("access_token").apply()
                nullUserDetails()
                super.onBackPressed()
            }
            noButton {
                toast("Nothing happened.")
            }
        }.show()
        Prefs.putBoolean("skip", false)
    }

    private fun addToFavorites(row: Repository, imageView: ImageView) {
        favoritesRepos.add(row.name)
        imageView.setImageResource(R.drawable.red_heart)
    }
    private fun nullUserDetails() {
        Prefs.putString("userName", null)
        Prefs.putString("avatarUrl", null)
        Prefs.putString("createdAt", null)
    }
}
// =====================================================================================================================

// AsyncTask class -> better to declare it in separate file
/*
* Request on GitHub server for fetch user repos - Retrofit2 library and Moshi parser.
* Returning List of repositories objects
* */
class GetUserRepos(private val userToken: String?): AsyncTask<Unit, Unit, List<GitHubRepository>?>() {
    override fun doInBackground(vararg params: Unit?): List<GitHubRepository>? {
        Log.i("TOKEN", userToken)
        val gitHubService = GitHubAPI.create()
        val gitRespond = gitHubService.getUserRepos(userToken!!).execute().body()
        return gitRespond
    }
}
/*
* Request on GitHub server for fetch user information - khttp library.
* Return object SimpleUser with 3 atributes - user name (login), avatar url and creation date
* */
class GetUserInfo(private val token: String?): AsyncTask<Unit, Unit, SimpleUser>() {
    override fun doInBackground(vararg params: Unit?): SimpleUser {
        Log.i("TOKEN", token)
        try {
            //val (request, response, result) = "https://api.github.com/user?access_token=$token".httpGet().responseString() // result is Result<String, FuelError>
            val response = get("https://api.github.com/user?access_token=$token")
            val JSONresponse = response.jsonObject
            val avatarURL = JSONresponse.getString("avatar_url")
            val userName = JSONresponse.getString("login")
            val createdAt = JSONresponse.getString("created_at")

            val user = SimpleUser(userName, avatarURL, createdAt)
            return user
        } catch (exception: Exception) {
            Log.i("ERROR", "${exception.stackTrace}")
        }
        return SimpleUser("", "", "")
    }
}
/*
* AsyncTask class for setup synchronization. Must be in background thread because on main it caused blocking main thread and frames skipping
* */
class SetupSync(private val component: ComponentName, private val scheduler: JobScheduler): AsyncTask<Unit, Unit, Unit>() {
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun doInBackground(vararg params: Unit?) {
        val info = JobInfo.Builder(123, component)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                .setPersisted(true)
                .setPeriodic(15 * 60 * 1000)
                .build()
        //val scheduler = getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
        val resultCode = scheduler.schedule(info)
        if (resultCode == JobScheduler.RESULT_SUCCESS) {
            Log.i("SUCCESS", "Job scheduled")
        } else {
            Log.i("NOT SUCCESS", "Job scheduling failed")
        }
    }
}
