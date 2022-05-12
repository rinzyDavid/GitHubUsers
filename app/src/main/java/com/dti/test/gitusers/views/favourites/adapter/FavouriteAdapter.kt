package com.dti.test.gitusers.views.favourites.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dti.test.gitusers.common.util.AdapterListener
import com.dti.test.gitusers.databinding.FavListItemBinding
import com.dti.test.gitusers.databinding.UserListItemBinding
import com.dti.test.gitusers.model.domain.GitUser

class FavouriteAdapter(
    private val data:List<GitUser>,
    private val listener:AdapterListener
):RecyclerView.Adapter<FavouriteAdapter.CustomViewHolder>() {




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {

        return CustomViewHolder(
            FavListItemBinding.inflate(
                LayoutInflater.from(
                    parent.context
                )
            ,parent,false
            )
        )
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {

        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
       return data.size
    }



    inner class CustomViewHolder(
        private val binding: FavListItemBinding
    ): RecyclerView.ViewHolder(binding.root){

        fun bind(user: GitUser){
            binding.user = user

            binding.imageButton2.setOnClickListener {
                listener.onClick(user)
            }
        }
    }


}