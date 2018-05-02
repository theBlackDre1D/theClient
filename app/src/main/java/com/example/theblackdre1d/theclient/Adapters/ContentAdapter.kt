package com.example.theblackdre1d.theclient.Adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.theblackdre1d.theclient.Models.GitHubRepoContent
import com.example.theblackdre1d.theclient.R
import kotlinx.android.synthetic.main.repo_list_row.view.*

class ContentListAdapter(private val items: List<GitHubRepoContent>): RecyclerView.Adapter<CodeHolder>()  {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CodeHolder {
        val viewGroup = LayoutInflater.from(parent.context).inflate(R.layout.repo_list_row, parent, false)
        return  CodeHolder(viewGroup)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: CodeHolder, position: Int) {
        val row: GitHubRepoContent = items[position]
        if (row.type == "dir") {
            holder.avatarImageView.setImageResource(R.drawable.directory)
        } else {
            holder.avatarImageView.setImageResource(R.drawable.file)
        }
        holder.repositoryName.text = row.name
        holder.repositoryPath.text = row.path
        holder.itemView.setOnClickListener {
            val toast = Toast.makeText(holder.itemView.context, "Au, that hurts!", Toast.LENGTH_SHORT)
            toast.show()
        }
    }
}

class CodeHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    val avatarImageView = itemView.avatarImageView as ImageView
    val repositoryName = itemView.repositoryName as TextView
    val repositoryPath = itemView.repositoryPath as TextView
}