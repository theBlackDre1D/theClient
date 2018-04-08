package com.example.theblackdre1d.theclient.Activities

import android.app.Fragment
import android.support.design.widget.TabLayout
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.support.v7.widget.Toolbar
import android.util.Log
import com.example.theblackdre1d.theclient.Adapters.PullsAdapter
import com.example.theblackdre1d.theclient.Adapters.ViewPagerAdapater
import com.example.theblackdre1d.theclient.Fragments.CodeFragment
import com.example.theblackdre1d.theclient.Fragments.CommitsFragment
import com.example.theblackdre1d.theclient.Fragments.PullRequestsFragment
import com.example.theblackdre1d.theclient.Fragments.ReadmeFragment
import com.example.theblackdre1d.theclient.R
import com.example.theblackdre1d.theclient.Token
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

        val toolbar = toolbar as Toolbar
        setSupportActionBar(toolbar)
        val viewPager = viewPager as ViewPager
        setupViewPager(viewPager)
        val tabLayout = tabs as TabLayout
        tabLayout.setupWithViewPager(viewPager)
    }

    private fun setupViewPager(viewPager: ViewPager) {
        val adapter = ViewPagerAdapater(supportFragmentManager)
        adapter.addFragment(CommitsFragment(author, repoName), "Commits")
        adapter.addFragment(ReadmeFragment(author, repoName), "README")
        adapter.addFragment(CodeFragment(author, repoName, Token.getToken()), "Codes")
        adapter.addFragment(PullRequestsFragment(author, repoName), "Pull Requests")
        viewPager.adapter = adapter
    }
}
