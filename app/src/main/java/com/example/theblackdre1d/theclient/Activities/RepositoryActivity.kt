package com.example.theblackdre1d.theclient.Activities

import android.content.Context
import android.support.design.widget.TabLayout
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.support.v7.widget.Toolbar
import com.example.theblackdre1d.theclient.Adapters.ViewPagerAdapater
import com.example.theblackdre1d.theclient.Fragments.CodeFragment
import com.example.theblackdre1d.theclient.Fragments.CommitsFragment
import com.example.theblackdre1d.theclient.Fragments.PullRequestsFragment
import com.example.theblackdre1d.theclient.Fragments.ReadmeFragment
import com.example.theblackdre1d.theclient.R
import kotlinx.android.synthetic.main.activity_repository.*

class RepositoryActivity : AppCompatActivity() {
    var author = ""
    var repoName = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repository)
        // UI elements
        val intent = intent
        author = intent.getStringExtra("author")
        repoName = intent.getStringExtra("repoName")
        //val toolbar = toolbar as Toolbar

        val toolbar = toolbar as Toolbar
        toolbar.title = repoName
        setSupportActionBar(toolbar)
        val viewPager = viewPager as ViewPager
        setupViewPager(viewPager)
        val tabLayout = tabs as TabLayout
        tabLayout.setupWithViewPager(viewPager)
    }

    private fun setupViewPager(viewPager: ViewPager) {
        val settings = application.getSharedPreferences("access_token", Context.MODE_PRIVATE)
        val token = settings?.getString("access_token", null)
        val adapter = ViewPagerAdapater(supportFragmentManager)
        adapter.addFragment(CommitsFragment(author, repoName), "Commits")
        adapter.addFragment(ReadmeFragment(author, repoName), "README")
        adapter.addFragment(CodeFragment(author, repoName, token!!), "Codes")
        adapter.addFragment(PullRequestsFragment(author, repoName), "Pull Requests")
        viewPager.adapter = adapter
    }
}
