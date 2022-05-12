package com.dti.test.gitusers.views.users.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dti.test.gitusers.databinding.LoadingItemBinding

class LoadingStateAdapter:
    LoadStateAdapter<LoadingStateAdapter.LoaderViewHolder>(){


    override fun onBindViewHolder(holder: LoaderViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoaderViewHolder {
        return LoaderViewHolder(
            LoadingItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,false
            )

        )
    }


    class LoaderViewHolder(val binding:LoadingItemBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(loadState: LoadState) {
            if (loadState is LoadState.Loading) {
                binding.progressBar2.visibility = View.VISIBLE
            } else {
                binding.progressBar2.visibility = View.GONE
            }
        }
    }


}