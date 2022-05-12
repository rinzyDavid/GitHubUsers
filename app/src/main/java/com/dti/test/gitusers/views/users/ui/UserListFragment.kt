package com.dti.test.gitusers.views.users.ui

import android.os.Bundle
import android.view.*
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.paging.map
import com.dti.test.gitusers.R
import com.dti.test.gitusers.common.util.AdapterListener
import com.dti.test.gitusers.databinding.FragmentUserListBinding
import com.dti.test.gitusers.model.domain.GitUser
import com.dti.test.gitusers.views.users.adapter.LoadingStateAdapter
import com.dti.test.gitusers.views.users.adapter.PageableUserAdapter
import com.dti.test.gitusers.views.vm.UserListVm
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.disposables.CompositeDisposable

/**
 * A simple [Fragment] subclass.
 * Use the [UserListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class UserListFragment : Fragment() {

    private val userVm: UserListVm by activityViewModels()
    private val mDisposable = CompositeDisposable()
    private lateinit var binding:FragmentUserListBinding
    private lateinit var adapter: PageableUserAdapter
    private lateinit var loadingState:LoadingStateAdapter

private val listener = object:PageableUserAdapter.ClickListener{
    override fun onUserWith(username: String,isDetailsComplete:Boolean) {
        userVm.username = username
        userVm.isUserDetailsComplete = isDetailsComplete
        findNavController().navigate(R.id.action_userListFragment_to_userDetailsFragment)
    }

}

    private val favouriteListener = object: AdapterListener {
        override fun onClick(user: GitUser) {
            userVm.addFavourite(user)
            Toast.makeText(requireActivity(),"User added to favourite",Toast.LENGTH_SHORT).show()
        }

    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
         binding = FragmentUserListBinding.inflate(inflater,container,false)
        setHasOptionsMenu(true)
        loadingState = LoadingStateAdapter()
        adapter = PageableUserAdapter(listener,favouriteListener)
        binding.userList.adapter = adapter.withLoadStateFooter(loadingState)
        fetchUsers()

        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.fav_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_favourite -> {
                // navigate to settings screen
                Navigation.findNavController(binding.root).navigate(R.id.action_userListFragment_to_favouriteFragment)
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun fetchUsers(){

        mDisposable.add(
            userVm.fetchGitUsers()
                .subscribe {
                    adapter.submitData(lifecycle,it)


                    it.map {
                        println("we have data $it")
                    }
                }
        )
    }

    companion object {
        @JvmStatic
        fun newInstance() = UserListFragment()

    }

    override fun onDestroyView() {
        mDisposable.dispose()

        super.onDestroyView()
    }

}