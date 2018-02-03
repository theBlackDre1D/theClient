package com.example.theblackdre1d.theclient.Activities

import android.content.Context
import android.content.SharedPreferences
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.ImageView
import android.widget.LinearLayout
import com.example.theblackdre1d.theclient.Adapters.RepositoryAdapter
import com.example.theblackdre1d.theclient.Models.Repository
import com.example.theblackdre1d.theclient.R
import com.squareup.picasso.Picasso
import khttp.get
import kotlinx.android.synthetic.main.activity_profile.*
import org.jetbrains.anko.doAsync

class ProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        val sharedPreferences: SharedPreferences = application.getSharedPreferences("access_token", Context.MODE_PRIVATE)
        val userToken: String? = sharedPreferences.getString("access_token",null)

        doAsync {
            val reposJSON = get("https://api.github.com/users/theblackdre1d/repos")
        }
        val profilePicture = circularImageView as ImageView
        Picasso.with(applicationContext).load("https://avatars1.githubusercontent.com/u/15018356?v=4").into(profilePicture)
        val repositoriesTable = RepositoryTable as RecyclerView
        repositoriesTable.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
        val repositoriesList = ArrayList<Repository>()
        repositoriesList.add(Repository("Repo 1", "This is just test repo 1"))
        repositoriesList.add(Repository("Repo 2", "This is just test repo 2"))
        repositoriesList.add(Repository("Repo 3", "This is just test repo 3"))
        repositoriesList.add(Repository("Repo 4", "This is just test repo 4"))
        repositoriesList.add(Repository("Repo 5", "This is just test repo 5"))

        val repositoriesAdapter = RepositoryAdapter(repositoriesList)
        repositoriesTable.adapter = repositoriesAdapter
    }
}
class GetUserRepos(val sharedPred: SharedPreferences, val userToken: String?): AsyncTask<Unit, Unit, Unit>() {
    override fun doInBackground(vararg params: Unit?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        val url = "https://api.github.com/user/repos?access_token=${userToken}"
    }
}