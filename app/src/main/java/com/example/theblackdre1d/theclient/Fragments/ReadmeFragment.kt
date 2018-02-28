package com.example.theblackdre1d.theclient.Fragments

import android.annotation.SuppressLint
import android.os.AsyncTask
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.theblackdre1d.theclient.R
import khttp.get
import kotlinx.android.synthetic.main.readme_fragment.view.*

@SuppressLint("ValidFragment")
class ReadmeFragment(val userName: String, val repoName: String, val branch: String? = "master"): Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val rootView = inflater.inflate(R.layout.readme_fragment, container, false)
        val stringRepo = GetREADME(userName, repoName, branch).execute().get()
        rootView.readmeTextView.text = stringRepo
        return rootView
    }
}

class GetREADME(val userName: String, val repoName: String, val branch: String? = "master"): AsyncTask<Unit, Unit, String>() {
    override fun onPreExecute() {
        super.onPreExecute()
    }

    override fun doInBackground(vararg params: Unit?): String {
        val readMe = get("https://raw.githubusercontent.com/$userName/$repoName/$branch/README.md")
        return readMe.text
    }
}