package com.example.theblackdre1d.theclient.Adapters

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.theblackdre1d.theclient.Models.GitHubCommit
import kotlinx.android.synthetic.main.commit_item.view.*

class CommitsAdapter(val commits: ArrayList<GitHubCommit>): RecyclerView.Adapter<CommitsAdapter.CommitsHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): CommitsHolder {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getItemCount(): Int {
        return commits.size
    }

    override fun onBindViewHolder(holder: CommitsHolder?, position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
    class CommitsHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val message = itemView.messageTextView as TextView
        val author = itemView.authorTextView as TextView
        val date = itemView.dateTextView as TextView
    }
}

