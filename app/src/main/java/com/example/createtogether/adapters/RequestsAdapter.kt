package com.example.createtogether.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.createtogether.R
import com.example.createtogether.db.models.Request
import com.example.createtogether.db.models.TextContent
import com.example.createtogether.ui.viewmodels.ViewModel


class RequestsAdapter(
    private val requestItemClickInterface: RequestItemClickInterface
) : RecyclerView.Adapter<RequestsAdapter.RequestViewHolder>() {


    inner class RequestViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTextTitle: TextView = itemView.findViewById(R.id.tvTextTitle)
        val tvRequestByName: TextView = itemView.findViewById(R.id.tvRequestByName)
        val tvRequestStatus: TextView = itemView.findViewById(R.id.tvRequestStatus)

    }

    //Differ for better performance
    private val diffCallback = object : DiffUtil.ItemCallback<Request>() {
        override fun areItemsTheSame(oldItem: Request, newItem: Request): Boolean {
            return oldItem.requestId == newItem.requestId  //here the bitmap or avg speed could be different, but it is generally the same
        }

        override fun areContentsTheSame(oldItem: Request, newItem: Request): Boolean {
            return oldItem.hashCode() == newItem.hashCode()  //here everything has to be same
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    fun updateListOfRequests(list: List<Request>) = differ.submitList(list)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RequestViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.row_item_request,
            parent, false
        )
        return RequestViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RequestViewHolder, position: Int) {
        val request = differ.currentList[position]

        holder.tvTextTitle.text = request.modifiedTextTitle
        holder.tvRequestByName.text = request.requestCreator
        holder.tvRequestStatus.text = request.requestStatus

        holder.itemView.setOnClickListener {
            requestItemClickInterface.onClick(request)
        }

    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}

interface RequestItemClickInterface {
    fun onClick(request: Request)

//    fun onLongClick(view: View, textContent: TextContent)

}
