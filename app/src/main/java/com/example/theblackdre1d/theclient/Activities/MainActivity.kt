package com.example.theblackdre1d.theclient.Activities

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Typeface
import android.net.ConnectivityManager
import android.net.Uri
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import com.example.theblackdre1d.theclient.Java.VideoViewOnPrepared
import com.example.theblackdre1d.theclient.R
import com.example.theblackdre1d.theclient.Token
import com.pixplicity.easyprefs.library.Prefs
import kotlinx.android.synthetic.main.activity_main.*
import khttp.post
import org.jetbrains.anko.alert
import org.jetbrains.anko.startActivityForResult
import org.jetbrains.anko.yesButton

class MainActivity : AppCompatActivity() {
    var clientID = Token.clientID
    var redirectURI = "theclient://callback"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        val sharedPreferences: SharedPreferences = application.getSharedPreferences("access_token", Context.MODE_PRIVATE)
//        val token: String? = sharedPreferences.getString("access_token",null)

        Prefs.Builder()
                .setContext(this)
                .setMode(Context.MODE_PRIVATE)
                .setPrefsName("loginPrefs")
                .setUseDefaultSharedPreference(true)
                .build()

//        if (token != null) {
//            val intent = Intent(this, RepoListActivity::class.java)
//            startActivity(intent)
//        }

        //Setting up font
        val font: Typeface = Typeface.createFromAsset(assets,"fonts/Atlantic Bentley.ttf")
        theClientText.typeface = font

        logInButton.setOnClickListener {
            val conectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = conectivityManager.activeNetworkInfo
            networkInfo?.let {
                if (networkInfo.isConnected) {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/login/oauth/authorize?client_id=${clientID}&scope=repo&redirect_uri=${redirectURI}"))
                    startActivity(intent)
                }
            }
            if (networkInfo == null) {
                alert("You have to be connected to proceed") {
                    yesButton {
                        val intent = Intent()
                        intent.action = Settings.ACTION_SETTINGS
                        val uri = Uri.fromParts("package", packageResourcePath, null)
                        intent.data = uri
                        startActivityForResult(intent, 0)
                    }
                }.show()
            }
        }
    }

    @SuppressLint("CommitPrefEdits")
   override fun onResume() {
        super.onResume()
        val sharedPreferences: SharedPreferences = application.getSharedPreferences("access_token", Context.MODE_PRIVATE)
        val token: String? = sharedPreferences.getString("access_token",null)
        token?.let {
            val intent = Intent(this, RepoListActivity::class.java)
            startActivity(intent)
        }

        //Setting up looping background video
        val uri: Uri = Uri.parse("android.resource://"+packageName+"/"+ R.raw.backgorund_movie)
        backgroundVideo.setVideoURI(uri)
        backgroundVideo.start()
        val videoLooping = VideoViewOnPrepared(backgroundVideo)
        val skipMainActivity = Prefs.getBoolean("skip", false)
        if (skipMainActivity) {
//            skipMainActivity = true
            val sharedPreferences: SharedPreferences = application.getSharedPreferences("access_token", Context.MODE_PRIVATE)
            val uri: Uri? = intent.data
            val tokenObtained: Boolean = ObtainAccessToken(uri, sharedPreferences).execute().get()
            if (tokenObtained) {
                val intent = Intent(this, RepoListActivity::class.java)
                startActivity(intent)
            }
        }
    }

    override fun onPause() {
        super.onPause()
        Prefs.putBoolean("skip", true)
    }
}

class ObtainAccessToken(val uri: Uri?, var sharedPref: SharedPreferences): AsyncTask<Unit, Unit, Boolean>() {
    @SuppressLint("ApplySharedPref", "CommitPrefEdits")
    override fun doInBackground(vararg params: Unit?): Boolean {
        val clientID = Token.clientID
        val clientSecret = Token.clientSecret
        val redirectURI = "theclient://callback"

        uri?.let {
            if (uri.toString().startsWith(redirectURI)) {
                val code = uri.getQueryParameter("code")
                val params = mapOf("client_id" to clientID, "client_secret" to clientSecret, "code" to code)
                val response = post("https://github.com/login/oauth/access_token", params = params, headers = mapOf("accept" to "application/json"))
                val content = response.jsonObject
                val accessToken = content.getString("access_token")
                Log.i("Token", accessToken)
              //  Log.d("Token", accessToken)
               // Prefs.putString("access_token",accessToken)
                sharedPref.edit().apply {
                    putString("access_token",accessToken)
                    commit()
                }
                return true
            }
        }
        return false
    }
}