package com.dicoding.githubsearch

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.dicoding.githubsearch.databinding.ActivityDetailBinding
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var detailViewModel: DetailViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val user = intent.getParcelableExtra<User>(EXTRA_USER) as User

        detailViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(DetailViewModel::class.java)

        user.User?.let { detailViewModel.findUserDetail(it) }

        detailViewModel.detailUser.observe(this, {
            detailUser -> setDetailData(detailUser)
        })

        binding.tvUserName.text = user.User
        Glide.with(this@DetailActivity)
            .load(user.Photo)
            .into(binding.imgDetailPhoto)

        val sectionsPagerAdapter = SectionsPagerAdapter(this, user.User.toString())

        binding.viewPager.adapter = sectionsPagerAdapter

        TabLayoutMediator(binding.tabs, /*viewPager*/binding.viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
        supportActionBar?.elevation = 0f

    }

    private fun setDetailData(detailUser: DetailResponse) {
        binding.tvFollowing.text = detailUser.following.toString()
        binding.tvFollowers.text = detailUser.followers.toString()

        if (detailUser.name == null) {
            binding.tvFullName.text = detailUser.login
        } else {
            binding.tvFullName.text = detailUser.name
        }

        binding.tvCompany.text = detailUser.company

        val mFollowingFragment = FollowingFragment()
        val mFollowersFragment = FollowerFragment()
        val mBundle = Bundle()
        val mBundle2 = Bundle()
        mBundle.putString(FollowerFragment.EXTRA_USER, detailUser.login)
        mBundle2.putString(FollowingFragment.EXTRA_USER, detailUser.login)
        mFollowingFragment.arguments = mBundle
        mFollowersFragment.arguments = mBundle2

        Log.e(ContentValues.TAG, "showSelectedUser: ${detailUser.login}")

    }

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
        const val EXTRA_USER = "extra_user"
    }
}