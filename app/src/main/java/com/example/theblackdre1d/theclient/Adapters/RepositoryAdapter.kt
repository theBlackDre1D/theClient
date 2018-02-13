package com.example.theblackdre1d.theclient.Adapters

import android.content.Intent
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.theblackdre1d.theclient.Activities.RepositoryActivity
import com.example.theblackdre1d.theclient.Models.Repository
import com.example.theblackdre1d.theclient.R
import kotlinx.android.synthetic.main.repository_layout.view.*

class RepositoryAdapter(val repositoryList: ArrayList<Repository>) : RecyclerView.Adapter<RepositoryAdapter.ViewHolder>() {
    override fun getItemCount(): Int {
        return repositoryList.size
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        val repository: Repository = repositoryList[position]
        holder?.let {
            holder.headerTextView.text = repository.name
            holder.subheaderTextView.text = repository.description
            holder.languageTextView.text = repository.language
            holder.itemView.setOnClickListener {
                Log.d("CLICKED: ", "You clicked at me! :)")
                val intent = Intent(holder.itemView.context, RepositoryActivity::class.java)
                holder.itemView.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val viewGroup = LayoutInflater.from(parent?.context).inflate(R.layout.repository_layout, parent, false)
        return ViewHolder(viewGroup)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val headerTextView = itemView.repoName as TextView
        val subheaderTextView = itemView.repoDescription as TextView
        val languageTextView = itemView.languageTextView as TextView
    }
}