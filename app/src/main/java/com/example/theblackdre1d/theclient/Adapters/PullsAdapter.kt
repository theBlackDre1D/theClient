package com.example.theblackdre1d.theclient.Adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.theblackdre1d.theclient.Models.GitHubPullRequest
import kotlinx.android.synthetic.main.pull_request_item.view.*
import com.example.theblackdre1d.theclient.R
/*
* RecyclerView adapter for pull requests.
* */
class PullsAdapter(val pulls: List<GitHubPullRequest>): RecyclerView.Adapter<PullsAdapter.PullsHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PullsHolder {
        val viewGroup = LayoutInflater.from(parent.context).inflate(R.layout.pull_request_item, parent, false)
        return PullsHolder(viewGroup)
    }

    override fun getItemCount(): Int {
        return pulls.size
    }

    override fun onBindViewHolder(holder: PullsHolder, position: Int) {
        val pullRequest: GitHubPullRequest = pulls[position]
        holder.author.text = pullRequest.user?.login
        holder.header.text = pullRequest.title
        holder.date.text = pullRequest.createdAt
    }


    class PullsHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val header = itemView.headerTextView as TextView
        val author = itemView.authorTextView as TextView
        val date = itemView.dateTextView as TextView
    }
}