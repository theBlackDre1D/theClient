package com.example.theblackdre1d.theclient.Fragments

import android.annotation.SuppressLint
import android.os.AsyncTask
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.theblackdre1d.theclient.R
import kotlinx.android.synthetic.main.readme_fragment.*
import us.feras.mdv.MarkdownView

@SuppressLint("ValidFragment")
class ReadmeFragment(val userName: String, val repoName: String, val branch: String? = "master"): Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        // TODO find why is view NULL
//        val view = readmeMarkdownView as MarkdownView
//        val url = "https://raw.githubusercontent.com/$userName/$repoName/$branch/README.md"
//        view.loadMarkdownFile(url)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater!!.inflate(R.layout.readme_fragment, container, false)
    }
}

class GetREADME(): AsyncTask<Unit, Unit, Unit>() {
    override fun onPreExecute() {
        super.onPreExecute()
    }

    override fun doInBackground(vararg params: Unit?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onPostExecute(result: Unit?) {
        super.onPostExecute(result)
    }
}