package com.dicoding.githubsearch

import android.content.Intent
import android.os.Bundle
import android.os.UserHandle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.githubsearch.databinding.FragmentFollowerBinding


class FollowerFragment : Fragment() {

    private lateinit var adapter: ListUserAdapter
    private lateinit var detailViewModel: DetailViewModel
    private lateinit var binding: FragmentFollowerBinding
//    private var _binding: FragmentFollowerBinding? = null
//    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFollowerBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val user = arguments?.getString(DetailActivity.EXTRA_USER)

        detailViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(DetailViewModel::class.java)
        if (user != null) {
            detailViewModel.findFollowers(user)
        }

        detailViewModel.listFollowers.observe(viewLifecycleOwner, {
            listFollowers -> setFollowersData(listFollowers)
        })

        detailViewModel.getFollowers().observe(viewLifecycleOwner) {
            adapter.setData(it)
            showLoading(false)
        }
        binding.tvCategoryName.text = user

//        binding.rvFollower.layoutManager = LinearLayoutManager(context)
//        binding.rvFollower.setHasFixedSize(true)

    }
    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }



    private fun setFollowersData(listFollowers: List<FollowersResponseItem>) {
        val listUser: ArrayList<User> = ArrayList()
        for (user in listFollowers) {
            val userList = User(user.login, user.avatarUrl)
            listUser.add(userList)
        }
        val adapter = ListUserAdapter(listUser)
        binding.rvFollower.layoutManager = LinearLayoutManager(context)
        binding.rvFollower.setHasFixedSize(true)
        binding.rvFollower.adapter = adapter
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
        detailViewModel = DetailViewModel()
    }

    companion object {
        const val EXTRA_USER ="extra_user"
        private const val TAG ="FollowerFragment"
        @JvmStatic
        fun newInstance(string: String) =
            FollowerFragment().apply {
                arguments = Bundle().apply {
                    putString(EXTRA_USER, string)
                }
            }
    }
}