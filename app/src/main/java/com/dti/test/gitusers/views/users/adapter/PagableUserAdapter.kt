package com.dti.test.gitusers.views.users.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.dti.test.gitusers.R
import com.dti.test.gitusers.common.util.AdapterListener
import com.dti.test.gitusers.databinding.UserListItemBinding
import com.dti.test.gitusers.model.domain.GitUser

class PageableUserAdapter(
    private val listener:ClickListener,
    private val favouriteListener:AdapterListener
) :  PagingDataAdapter<GitUser, PageableUserAdapter.CustomViewHolder>(
    COMPARATOR
)  {

    interface ClickListener{
        fun onUserWith(username: String,isDetailsComplete:Boolean)
    }




    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {

        getItem(position)?.let {user->
            holder.bind(user)

            holder.itemView.setOnClickListener { listener.onUserWith(user.username!!,user.isDataComplete!!) }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {

        return CustomViewHolder(
            UserListItemBinding.inflate(
                LayoutInflater.from(parent.context)
            ,parent,false
            )
        )
    }





   inner class CustomViewHolder(
        private val binding:UserListItemBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(user: GitUser){
            binding.user = user

            binding.imageButton2.setOnClickListener {
                favouriteListener.onClick(user)
                binding.imageButton2.setImageResource(R.drawable.ic_favorite_on)
            }

        }
    }


    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<GitUser>() {
            override fun areItemsTheSame(oldItem: GitUser, newItem: GitUser): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: GitUser, newItem: GitUser): Boolean {
                return oldItem == newItem
            }
        }
    }


}