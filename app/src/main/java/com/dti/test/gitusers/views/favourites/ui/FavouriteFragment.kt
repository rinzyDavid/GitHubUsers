package com.dti.test.gitusers.views.favourites.ui

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.dti.test.gitusers.R
import com.dti.test.gitusers.common.util.AdapterListener
import com.dti.test.gitusers.databinding.FragmentFavouriteBinding
import com.dti.test.gitusers.model.domain.GitUser
import com.dti.test.gitusers.views.favourites.adapter.FavouriteAdapter
import com.dti.test.gitusers.views.vm.UserListVm
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavouriteFragment : Fragment() {

    private lateinit var binding:FragmentFavouriteBinding
    private val userVm: UserListVm by activityViewModels()
    private lateinit var adapter: FavouriteAdapter

    private val listener = object:AdapterListener{
        override fun onClick(user: GitUser) {
            userVm.removeFavourite(user)
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavouriteBinding.inflate(inflater,container,false)
        // Inflate the layout for this fragment
        setHasOptionsMenu(true)
        userVm.fetchFavourites().observe(viewLifecycleOwner){
            adapter = FavouriteAdapter(it,listener)
            binding.userList.adapter = adapter
        }

        return binding.root
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.app_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_clear -> {
                // navigate to settings screen
                userVm.removeAllFavourites()

                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            FavouriteFragment()
    }
}