package com.dicoding.githubuserv10.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.dicoding.githubuserv10.R
import com.dicoding.githubuserv10.databinding.ActivityDetailBinding
import com.dicoding.githubuserv10.ui.adapter.SectionPagerAdapter
import com.dicoding.githubuserv10.ui.model.DetailViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var viewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Detail User"

        val username = intent.getStringExtra(EXTRA_USERNAME)
        val id = intent.getIntExtra(EXTRA_ID,0)
        val avatar_url = intent.getStringExtra((EXTRA_URL))

        val bundle = Bundle()
        bundle.putString(EXTRA_USERNAME, username)


        viewModel = ViewModelProvider(this)[DetailViewModel::class.java]

        username?.let { viewModel.setDetail(it) }
        viewModel.getDetail().observe(this) {
            if (it != null) {
                binding.apply {
                    tvName.text = it.name
                    tvUsername.text = it.login
                    tvFollowers.text = getString(R.string.follow_count, it.followers)
                    tvFollowing.text = getString(R.string.follow_count, it.following)
                    Glide.with(this@DetailActivity)
                        .load(it.avatar_url)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .centerCrop()
                        .into(ivProfile)

                    progressBar.visibility = View.GONE
                }
            }
        }

        var isFavorite = false
        CoroutineScope(Dispatchers.IO).launch {
            val count = viewModel.checkUser(id)
            withContext(Dispatchers.Main){
                if (count != null){
                    isFavorite = count > 0
                    binding.fabFavorite.setImageResource(
                        if (isFavorite) R.drawable.ic_favorite_white
                        else R.drawable.ic_favoritebd_white
                    )
                }
            }
        }

        binding.fabFavorite.setOnClickListener {
            isFavorite = !isFavorite
            if (isFavorite) {
                CoroutineScope(Dispatchers.IO).launch {
                    viewModel.addToFavorite(username, id, avatar_url)
                }
                binding.fabFavorite.setImageResource(R.drawable.ic_favorite_white)
            } else {
                CoroutineScope(Dispatchers.IO).launch {
                    viewModel.removeFromFavorite(id)
                }
                binding.fabFavorite.setImageResource(R.drawable.ic_favoritebd_white)
            }
        }

        val sectionPagerAdapter = SectionPagerAdapter(this, supportFragmentManager, bundle)
        binding.apply {

            viewPager.adapter = sectionPagerAdapter
            tabs.setupWithViewPager(viewPager)
        }
    }
    companion object {
        const val EXTRA_USERNAME = "extra_username"
        const val EXTRA_ID = "extra_id"
        const val EXTRA_URL = "extra_url"
    }
}