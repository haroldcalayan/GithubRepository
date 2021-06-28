package com.haroldcalayan.githubrepository.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.haroldcalayan.githubrepository.BR
import com.haroldcalayan.githubrepository.R
import com.haroldcalayan.githubrepository.data.model.Item
import com.haroldcalayan.githubrepository.databinding.AdapterRepositoryItemBinding

class RepositoryAdapter(private var data: List<Item>, private var listener: RepositoryAdapterListener) : RecyclerView.Adapter<RepositoryAdapter.RepositoryAdapterViewHolder>() {

    interface RepositoryAdapterListener {
        fun onItemClick(item: Item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryAdapterViewHolder {
        val binding: AdapterRepositoryItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.adapter_repository_item,
            parent,
            false
        )
        return RepositoryAdapterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RepositoryAdapterViewHolder, position: Int) {
        holder.bind(data[position])
        holder.itemView.setOnClickListener { listener.onItemClick(data[position]) }
    }

    override fun getItemCount() = data.size

    fun updateData(data: List<Item>) {
        this.data = data
        notifyDataSetChanged()
    }

    class RepositoryAdapterViewHolder(private val binding: AdapterRepositoryItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Item) {
            binding.setVariable(BR.item, item)
            binding.executePendingBindings()
        }
    }
}