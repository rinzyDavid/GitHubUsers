package com.dti.test.gitusers.views.users.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.dti.test.gitusers.R
import com.dti.test.gitusers.databinding.FragmentUserDetailsBinding
import com.dti.test.gitusers.model.domain.ResultData
import com.dti.test.gitusers.views.vm.UserListVm
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserDetailsFragment : Fragment() {

    private val userVm: UserListVm by activityViewModels()
    private lateinit var binding:FragmentUserDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentUserDetailsBinding.inflate(inflater,container,false)
        binding.progressBar.visibility = View.VISIBLE
        binding.lifecycleOwner = requireActivity()
        userVm.fetchUserDetails().observe(viewLifecycleOwner) { result ->

            when (result) {
             is ResultData.Success->{
                 userVm.user = result.value
                  binding.vm = userVm
                 println("selected user ${result.value}")
              }
                is ResultData.Failure->{
                   showToast(result.error.message?:"")
                }

            }

            binding.progressBar.visibility = View.GONE
        }

        binding.imageButton.setOnClickListener {
            userVm.addFavourite()
            showToast("User added to favourite")
            binding.imageButton.setImageResource(R.drawable.ic_favorite_on)
        }
        return binding.root
    }


    private fun showToast(message:String){
        Toast.makeText(requireActivity(),message,Toast.LENGTH_SHORT).show()
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) = UserDetailsFragment()

    }
}