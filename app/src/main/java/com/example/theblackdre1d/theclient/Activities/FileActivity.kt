package com.example.theblackdre1d.theclient.Activities

import android.content.Intent
import android.net.NetworkInfo
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import com.example.theblackdre1d.theclient.Models.GitHubRepoContent
import com.example.theblackdre1d.theclient.R
import com.github.pwittchen.reactivenetwork.library.rx2.ConnectivityPredicate
import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork
import com.google.gson.Gson
import com.pddstudio.highlightjs.HighlightJsView
import com.pixplicity.easyprefs.library.Prefs
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_file.*
import org.jetbrains.anko.alert

/*
* Activity use HighlightJsView for show text or code with highlited code.
* One request on GitHub server for fetch file content.
* */
class FileActivity : AppCompatActivity() {

    lateinit var file: String
//    lateinit var codeView: HighlightJsView

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
        file = GetFile(userName, repoName, "master", row.path!!).execute().get()

        val codeView = codeView as HighlightJsView
        codeView.setSource(file)

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
    }

    private fun redirectToSettings() {
        val intent = Intent(Settings.ACTION_WIRELESS_SETTINGS)
        startActivity(intent)
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