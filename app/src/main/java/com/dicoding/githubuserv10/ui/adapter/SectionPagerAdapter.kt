@file:Suppress("DEPRECATION")

package com.dicoding.githubuserv10.ui.adapter

import android.content.Context
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.dicoding.githubuserv10.R
import com.dicoding.githubuserv10.ui.FollowersFragment
import com.dicoding.githubuserv10.ui.FollowingFragment

@Suppress("DEPRECATION")
class SectionPagerAdapter(private val mCtx: Context, fm: FragmentManager, data: Bundle) :
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private var fragmentBundle: Bundle

    init {
        fragmentBundle = data
    }

    @StringRes
    private val TAB_TITLES = intArrayOf(R.string.tab_1, R.string.tab_2)


    override fun getItem(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = FollowersFragment()
            1 -> fragment = FollowingFragment()
        }
        fragment?.arguments = this.fragmentBundle
        return fragment as Fragment
    }

    override fun getCount(): Int = 2


    override fun getPageTitle(position: Int): CharSequence {
        return mCtx.resources.getString(TAB_TITLES[position])
    }

}