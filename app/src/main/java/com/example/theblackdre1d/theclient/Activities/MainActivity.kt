package com.example.theblackdre1d.theclient.Activities

import android.content.Intent
import android.graphics.Typeface
import android.media.MediaPlayer
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.StrictMode
import android.util.Log
import android.widget.Toast
import com.example.theblackdre1d.theclient.Java.VideoViewOnPrepared
import com.example.theblackdre1d.theclient.R
import khttp.get
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.*
import retrofit2.Retrofit
import khttp.post

class MainActivity : AppCompatActivity() {
    var clientID = "064b9848a992571c3dec"
    var clientSecret = "f7c894c87c650b578e9ae3efc043ce6861133b0f"
    var redirectURI = "theclient://callback"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Setting up permision for HTTP requests. This is bad way to do it better will be do it in background thread
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

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

    override fun onResume() {
        super.onResume()

        var uri: Uri? = intent.data
        uri?.let {
            if (uri.toString().startsWith(redirectURI)) {
                var code = uri.getQueryParameter("code")
                var params = mapOf("client_id" to clientID, "client_secret" to clientSecret, "code" to code)
                var response = post("https://github.com/login/oauth/access_token", params = params, headers = mapOf("accept" to "application/json"))
                var content = response.jsonObject
                print(content)
                var accessToken = content.getString("access_token")
            }
        }
    }
}
