package com.example.theblackdre1d.theclient.Activities

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Typeface
import android.media.MediaPlayer
import android.net.Uri
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.StrictMode
import android.support.annotation.UiThread
import android.support.v4.content.ContextCompat.startActivity
import android.util.Log
import android.util.Log.d
import android.widget.Toast
import com.example.theblackdre1d.theclient.Java.VideoViewOnPrepared
import com.example.theblackdre1d.theclient.R
import com.github.salomonbrys.kotson.toJson
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.*
import khttp.post
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.runBlocking
import kotlinx.coroutines.experimental.android.UI
import okhttp3.*
import org.jetbrains.anko.coroutines.experimental.bg
import org.jetbrains.anko.design.snackbar
import java.io.IOException

class MainActivity : AppCompatActivity() {
    var clientID = "064b9848a992571c3dec"
    var clientSecret = "f7c894c87c650b578e9ae3efc043ce6861133b0f"
    var redirectURI = "theclient://callback"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //TODO: Find out best practice to disable strict mode
        //Setting up permission for HTTP requests. This is bad way to do it better will be do it in background thread

//        doAsync {
//            val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
//            StrictMode.setThreadPolicy(policy)
//            uiThread {
//                toast("Welcome!")
//            }
//        }
        //Setting up font
        val font: Typeface = Typeface.createFromAsset(assets,"fonts/Atlantic Bentley.ttf")
        theClientText.setTypeface(font)

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
//        val sharedPref2 = this.getPreferences(Context.MODE_PRIVATE)
        val sharedPreferences: SharedPreferences = application.getSharedPreferences("access_token", Context.MODE_PRIVATE)
        val uri: Uri? = intent.data
//        doAsync {
//            uri?.let {
//                if (uri.toString().startsWith(redirectURI)) {
//                    var code = uri.getQueryParameter("code")
//                    var params = mapOf("client_id" to clientID, "client_secret" to clientSecret, "code" to code)
//                    var response = post("https://github.com/login/oauth/access_token", params = params, headers = mapOf("accept" to "application/json"))
//                    var content = response.jsonObject
//                    var accessToken = content.getString("access_token")
//                    var editor = sharedPreferences.edit().apply {
//                        putString("access_token",accessToken)
//                        commit()
//                    }
//                }
//            }
//        }
        ObtainAccessToken(uri, sharedPreferences).execute()

        val intent = Intent(this, ProfileActivity::class.java)
        startActivity(intent)
//        uri?.let {
//            var code = uri.getQueryParameter("code")
//            var client = OkHttpClient()
//            val httpURL: HttpUrl.Builder = HttpUrl.parse("https://github.com/login/oauth/access_token")!!.newBuilder()
//            httpURL.addQueryParameter("client_id", clientID)
//            httpURL.addQueryParameter("client_secret", clientSecret)
//            httpURL.addQueryParameter("code", code)
//            val requestURL = httpURL.build().toString()
//            var requestAccessToken = Request.Builder()
//                    .url(requestURL)
//                    .build()
//            client.newCall(requestAccessToken).enqueue(object: Callback {
//                override fun onFailure(call: Call?, e: IOException?) {
//                    alert ( "Something is wrong!", "While reaching API something went wrong" ) {
//                        yesButton {  }
//                    }.show()
//                }
//
//                override fun onResponse(call: Call?, response: Response?) {
//                    response?.let {
//                        if (response.isSuccessful) {
//                            val content = response.body()!!.string().toJson()
//                            val accessToken = content
//                            runOnUiThread(object : Runnable {
//                                override fun run() {
//                                    val intent = Intent(MainActivity(), ProfileActivity::class.java)
//                                    startActivity(intent)
//                                }
//                            })
//                        }
//                    }
//                }
//            })
//        }
    }
}

class ObtainAccessToken(content: Uri?, sharedPref: SharedPreferences): AsyncTask<Unit, Unit, Unit>() {
    val uri = content
    val sharedPref = sharedPref
    override fun doInBackground(vararg params: Unit?) {
        var clientID = "064b9848a992571c3dec"
        var clientSecret = "f7c894c87c650b578e9ae3efc043ce6861133b0f"
        var redirectURI = "theclient://callback"

        doAsync {
            uri?.let {
                if (uri.toString().startsWith(redirectURI)) {
//                    val sharedPreferences: SharedPreferences = application.getSharedPreferences("access_token", Context.MODE_PRIVATE)
                    var code = uri.getQueryParameter("code")
                    var params = mapOf("client_id" to clientID, "client_secret" to clientSecret, "code" to code)
                    var response = post("https://github.com/login/oauth/access_token", params = params, headers = mapOf("accept" to "application/json"))
                    var content = response.jsonObject
                    var accessToken = content.getString("access_token")
                    var editor = sharedPref.edit().apply {
                        putString("access_token",accessToken)
                        commit()
                    }
                }
            }
        }
    }
    override fun onPostExecute(result: Unit?) {
        super.onPostExecute(result)
//        toast("I got the Access token for you!")
//        val intent = Intent(MainActivity(), ProfileActivity::class.java)
//        startActivity(intent)
    }
}