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


class TextsSavedAdapter(
    private val textContentSavedItemClickInterface: TextContentSavedItemClickInterface
) : RecyclerView.Adapter<TextsSavedAdapter.TextsSavedViewHolder>() {

    inner class TextsSavedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textTitle: TextView = itemView.findViewById(R.id.tvTextTitle)
        val category: TextView = itemView.findViewById(R.id.tvCategory)
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

    fun updateListOfSavedTexts(list: List<TextContent>) = differ.submitList(list)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TextsSavedViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.row_item_textcontent_saved,
            parent, false
        )
        return TextsSavedViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TextsSavedViewHolder, position: Int) {
        val textContent = differ.currentList[position]

        holder.textTitle.text = textContent.textTitle
        holder.category.text = textContent.category

        holder.itemView.setOnClickListener {
            textContentSavedItemClickInterface.onClick(textContent)
        }

        holder.itemView.setOnLongClickListener {
            textContentSavedItemClickInterface.onLongClick(holder.itemView, textContent)
            return@setOnLongClickListener true
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}

interface TextContentSavedItemClickInterface {
    fun onClick(textContent: TextContent)

    fun onLongClick(view: View, textContent: TextContent)

}
