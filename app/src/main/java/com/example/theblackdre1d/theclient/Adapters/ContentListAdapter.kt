package com.example.theblackdre1d.theclient.Adapters

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.theblackdre1d.theclient.Models.GitHubRepoContent
import us.feras.mdv.R

class ContentListAdapter(private var activity: Activity, private var items: ArrayList<GitHubRepoContent>): BaseAdapter() {
    private class ViewHolder(row: View?) {
        var imageView: ImageView? = null
        var repoName: TextView? = null
        var repoPath: TextView? = null

        init {
            row?.let {
                imageView = row.findViewById(R.id.repositoryName)
            }
        }
    }
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getItem(position: Int): Any {
        return items.get(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return items.count()
    }
}