package com.example.theblackdre1d.theclient.Adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
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
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val vievGroup = LayoutInflater.from(parent?.context).inflate(R.layout.repository_layout, parent, false)
        return ViewHolder(vievGroup)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val headerTextView = itemView.firstTextView as TextView
        val subheaderTextView = itemView.secondTextView as TextView

    }
}