package com.example.theblackdre1d.theclient.Adapters

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.theblackdre1d.theclient.Activities.RepositoryActivity
import com.example.theblackdre1d.theclient.Models.Repository
import com.example.theblackdre1d.theclient.R
import kotlinx.android.synthetic.main.repository_layout.view.*
import org.jetbrains.anko.image
import org.jetbrains.anko.imageResource

/*
* RecyclerView adapter for show repository after log in.
* */
class RepoListAdapter(private val repositoryList: ArrayList<Repository>) : RecyclerView.Adapter<RepoListAdapter.ViewHolder>() {
    override fun getItemCount(): Int {
        return repositoryList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val repository: Repository = repositoryList[position]
        holder.bind(repository)
        holder.itemView.setOnClickListener {
            Log.d("CLICKED: ", "You clicked at me! :)")
            val intent = Intent(holder.itemView.context, RepositoryActivity::class.java)
            intent.putExtra("author", repository.account)
            intent.putExtra("repoName", repository.name)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewGroup = LayoutInflater.from(parent.context).inflate(R.layout.repository_layout, parent, false)
        return ViewHolder(viewGroup)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val headerTextView = itemView.repoName as TextView
        val subheaderTextView = itemView.repoDescription as TextView
        val languageTextView = itemView.languageTextView as TextView
        val heart = itemView.heartImageView as ImageView

        fun bind(row: Repository) {
            headerTextView.text = row.name
            subheaderTextView.text = row.description
            languageTextView.text = row.language
            heart.setImageResource(R.drawable.empty_heart)
            heart.setOnClickListener {
//                clickListener(row, heart)
                if (!row.favorite!!) {
                    heart.setImageResource(R.drawable.red_heart)
                    row.favorite = true
                    Toast.makeText(itemView.context, "${row.name} marked as favorite!", Toast.LENGTH_SHORT).show()
                } else {
                    heart.setImageResource(R.drawable.empty_heart)
                    Toast.makeText(itemView.context, "${row.name} unmarked as favorite!", Toast.LENGTH_SHORT).show()
                    row.favorite = false
                }
            }

        }
//        val accountTextView = itemView.accountHiddenTextView as TextView
    }
}