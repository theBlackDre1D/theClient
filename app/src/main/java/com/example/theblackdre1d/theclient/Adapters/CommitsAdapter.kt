package com.example.theblackdre1d.theclient.Adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.example.theblackdre1d.theclient.Models.GitHubCommit
import com.example.theblackdre1d.theclient.R
import kotlinx.android.synthetic.main.commit_item.view.*

class CommitsAdapter(private val commits: List<GitHubCommit>): RecyclerView.Adapter<CommitsAdapter.CommitsHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommitsHolder {
        val viewGroup = LayoutInflater.from(parent.context).inflate(R.layout.commit_item, parent, false)
        return  CommitsHolder(viewGroup)
    }

    override fun onBindViewHolder(holder: CommitsHolder, position: Int) {
        val commit: GitHubCommit = commits[position]
        holder.message.text = commit.commit?.message ?: "Error while unwrapping commit's content"
        holder.author.text = commit.commit?.committer?.name ?: "Error while unwrapping author's name"
        holder.date.text = commit.commit?.committer?.date ?: "Error while unwrapping date of commit"
        holder.itemView.setOnClickListener {
            val toast = Toast.makeText(holder.itemView.context, "Au, that hurts!", Toast.LENGTH_SHORT)
            toast.show()
        }
    }

    override fun getItemCount(): Int {
        return commits.size
    }

    class CommitsHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val message = itemView.messageTextView as TextView
        val author = itemView.authorTextView as TextView
        val date = itemView.dateTextView as TextView
    }
}

