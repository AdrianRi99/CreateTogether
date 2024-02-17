package com.example.createtogether.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.createtogether.R
import com.example.createtogether.db.models.TextContent


class TextsFoundAdapter(
    private val textContentItemClickInterface: TextContentItemClickInterface
) : RecyclerView.Adapter<TextsFoundAdapter.TextsFoundViewHolder>() {

    inner class TextsFoundViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textTitle: TextView = itemView.findViewById(R.id.tvTextTitle)
        val creatorName: TextView = itemView.findViewById(R.id.tvCreatorName)
    }

    //Differ for better performance
    private val diffCallback = object : DiffUtil.ItemCallback<TextContent>() {
        override fun areItemsTheSame(oldItem: TextContent, newItem: TextContent): Boolean {
            return oldItem.textId == newItem.textId  //here the bitmap or avg speed could be different, but it is generally the same
        }

        override fun areContentsTheSame(oldItem: TextContent, newItem: TextContent): Boolean {
            return oldItem.hashCode() == newItem.hashCode()  //here everything has to be same
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    fun updateListOfTextsFound(list: List<TextContent>) = differ.submitList(list)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TextsFoundViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.row_item_textcontent,
            parent, false
        )
        return TextsFoundViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TextsFoundViewHolder, position: Int) {
        val textContent = differ.currentList[position]

        holder.textTitle.text = textContent.textTitle
        holder.creatorName.text = "by ${textContent.creator}"

        holder.itemView.setOnClickListener {
            textContentItemClickInterface.onClick(textContent)
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}

interface TextContentItemClickInterface {
    fun onClick(textContent: TextContent)
}
