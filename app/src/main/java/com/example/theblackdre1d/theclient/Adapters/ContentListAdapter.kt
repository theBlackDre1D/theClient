package com.example.theblackdre1d.theclient.Adapters

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.theblackdre1d.theclient.Models.GitHubRepoContent
import com.example.theblackdre1d.theclient.R

class ContentListAdapter(private var context: Context, private var items: List<GitHubRepoContent>): BaseAdapter() {
    private class ViewHolder(row: View?) {
        var imageView: ImageView? = null
        var repoName: TextView? = null
        var repoPath: TextView? = null

        init {
            imageView = row?.findViewById(R.id.avatarImageView)
            repoName = row?.findViewById(R.id.repositoryName)
            repoPath = row?.findViewById(R.id.repositoryPath)
        }
    }
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View?
        val viewHolder: ViewHolder?
        if (convertView == null) {
            val layout = LayoutInflater.from(context)
            view = layout.inflate(R.layout.repo_list_row, parent, false)
            viewHolder = ViewHolder(view)
            view.tag = viewHolder
        } else {
            view = convertView
            viewHolder = view.tag as ViewHolder
        }
        val row = getItem(position) as GitHubRepoContent
        viewHolder.repoName?.text = row.name ?: "Unknown"
        viewHolder.repoPath?.text = row.path ?: "Unknown"
        if (row.type == "dir") {
            viewHolder.imageView?.setImageResource(R.drawable.directory) ?: R.drawable.github
        } else {
            viewHolder.imageView?.setImageResource(R.drawable.file) ?: R.drawable.github
        }
        return view as View
    }

    override fun getItem(position: Int): Any {
        return items[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return items.size
    }
}