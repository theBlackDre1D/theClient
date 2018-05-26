package com.example.theblackdre1d.theclient.Adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.theblackdre1d.theclient.Models.GitHubRepoContent
import com.example.theblackdre1d.theclient.R
import kotlinx.android.synthetic.main.repo_list_row.view.*
/*
* RecyclerView adapter for files in repo.
* */
class ContentListAdapter(private val items: List<GitHubRepoContent>, val clickListener: (GitHubRepoContent) -> Unit): RecyclerView.Adapter<CodeHolder>()  {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CodeHolder {
        val viewGroup = LayoutInflater.from(parent.context).inflate(R.layout.repo_list_row, parent, false)
        return  CodeHolder(viewGroup)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: CodeHolder, position: Int) {
        val row: GitHubRepoContent = items[position]
        holder.bind(row, clickListener)
    }
}

class CodeHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    val avatarImageView = itemView.avatarImageView as ImageView
    val repositoryName = itemView.repositoryName as TextView
    val repositoryPath = itemView.repositoryPath as TextView
    fun bind(row: GitHubRepoContent, clickListener: (GitHubRepoContent) -> Unit) {
        if (row.type == "dir") {
            avatarImageView.setImageResource(R.drawable.directory)
        } else {
            avatarImageView.setImageResource(R.drawable.file)
        }
        repositoryName.text = row.name
        repositoryPath.text = row.path
        itemView.setOnClickListener {
            clickListener(row)
        }
    }
}