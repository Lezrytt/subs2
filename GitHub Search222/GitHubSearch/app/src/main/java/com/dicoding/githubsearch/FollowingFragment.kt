package com.dicoding.githubsearch

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.githubsearch.databinding.FragmentFollowingBinding

class FollowingFragment : Fragment() {

    private lateinit var detailViewModel: DetailViewModel
    private var _binding: FragmentFollowingBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFollowingBinding.inflate(inflater, container, false)
//        binding.rvFollowing.layoutManager = LinearLayoutManager(requireContext()) //nope too
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvFollowing.layoutManager = LinearLayoutManager(requireContext())

        val user = arguments?.getString(DetailActivity.EXTRA_USER)

        detailViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(DetailViewModel::class.java)

        if (user != null) {
            detailViewModel.findFollowing(user)
        }
        detailViewModel.listFollowing.observe(viewLifecycleOwner, {
                listFollowing -> setFollowingData(listFollowing)
        })
        binding.tvName.text = "Following"

        binding.rvFollowing.layoutManager = LinearLayoutManager(context)
        binding.rvFollowing.setHasFixedSize(true)
    }

    private fun setFollowingData(listFollowing: List<FollowingResponseItem>) {
        val listUser: ArrayList<User> = ArrayList()
        listUser.clear()
        for (user in listFollowing) {
            val userList = User(user.login, user.avatarUrl)
            listUser.add(userList)
        }
        val adapter = ListUserAdapter(listUser)
        binding.rvFollowing.adapter = adapter
//        binding.rvFollowing.layoutManager = LinearLayoutManager(requireContext()) //nope
        adapter.setOnItemClickCallback(object : ListUserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: User) {
                showSelectedUser(data)
            }
        })
    }

    private fun showSelectedUser(user: User) {
        val moveWithObjectIntent = Intent(activity, DetailActivity::class.java)
        moveWithObjectIntent.putExtra(DetailActivity.EXTRA_USER, user)
        startActivity(moveWithObjectIntent)
    }

    companion object {
        var TAG = "FollowingFragment"
        const val EXTRA_USER = "extra_user"
        @JvmStatic
        fun newInstance(string: String) =
            FollowingFragment().apply {
                arguments = Bundle().apply {
                    putString(EXTRA_USER, string)
                }
            }
    }
}