package com.example.theblackdre1d.theclient.Activities

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Typeface
import android.net.Uri
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.theblackdre1d.theclient.Java.VideoViewOnPrepared
import com.example.theblackdre1d.theclient.R
import com.example.theblackdre1d.theclient.Token
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.*
import khttp.post

class MainActivity : AppCompatActivity() {
    var clientID = Token.clientID
    var redirectURI = "theclient://callback"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val sharedPreferences: SharedPreferences = application.getSharedPreferences("access_token", Context.MODE_PRIVATE)
        val token: String? = sharedPreferences.getString("access_token",null)
        token?.let {
            val intent = Intent(this, RepoListActivity::class.java)
            startActivity(intent)
        }

        //Setting up font
        val font: Typeface = Typeface.createFromAsset(assets,"fonts/Atlantic Bentley.ttf")
        theClientText.typeface = font

        //Setting up looping background video
        val uri: Uri = Uri.parse("android.resource://"+packageName+"/"+ R.raw.backgorund_movie)
        backgroundVideo.setVideoURI(uri)
        backgroundVideo.start()
        val videoLooping = VideoViewOnPrepared(backgroundVideo)

        logInButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/login/oauth/authorize?client_id=${clientID}&scope=repo&redirect_uri=${redirectURI}"))
            startActivity(intent)
        }
    }

    @SuppressLint("CommitPrefEdits")
   override fun onResume() {
        super.onResume()
        val sharedPreferences: SharedPreferences = application.getSharedPreferences("access_token", Context.MODE_PRIVATE)
        val uri: Uri? = intent.data

        ObtainAccessToken(uri, sharedPreferences).execute()
        val intent = Intent(this, RepoListActivity::class.java)
        startActivity(intent)
    }
}

class ObtainAccessToken(val uri: Uri?, val sharedPref: SharedPreferences): AsyncTask<Unit, Unit, Unit>() {
    @SuppressLint("ApplySharedPref")
    override fun doInBackground(vararg params: Unit?) {
        val clientID = Token.clientID
        val clientSecret = Token.clientSecret
        val redirectURI = "theclient://callback"

        doAsync {
            uri?.let {
                if (uri.toString().startsWith(redirectURI)) {
//                    val sharedPreferences: SharedPreferences = application.getSharedPreferences("access_token", Context.MODE_PRIVATE)
                    val code = uri.getQueryParameter("code")
                    val params = mapOf("client_id" to clientID, "client_secret" to clientSecret, "code" to code)
                    val response = post("https://github.com/login/oauth/access_token", params = params, headers = mapOf("accept" to "application/json"))
                    val content = response.jsonObject
                    val accessToken = content.getString("access_token")
                    Log.d("Token", accessToken)
                    var editor = sharedPref.edit().apply {
                        putString("access_token",accessToken)
                        commit()
                    }
                }
            }
        }
    }
}