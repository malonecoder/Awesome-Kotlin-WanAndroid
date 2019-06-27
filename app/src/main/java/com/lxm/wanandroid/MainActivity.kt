package com.lxm.wanandroid

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.lxm.module_library.statusbar.StatusBarUtil
import com.lxm.wanandroid.ui.ArticleFragment
import kotlinx.android.synthetic.main.activity_main.*


const val PHOTO_URL = "http://cdn.duitang.com/uploads/item/201308/19/20130819160917_RAvwE.thumb.700_0.jpeg"
class MainActivity : AppCompatActivity() {

    private val mTitles by lazy {
        mutableListOf("主页", "导航", "公众号", "工具","项目")
    }
    private val mFragments: MutableList<Fragment> by lazy {
        mutableListOf<Fragment>()
    }

    private val mAdapter: MyPagerAdapter by lazy {
        MyPagerAdapter(supportFragmentManager)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        StatusBarUtil.setColor(this, resources.getColor(R.color.colorTheme), 0)
        setSupportActionBar(toolbar)
        val actionBar = supportActionBar
        actionBar?.setDisplayShowTitleEnabled(false)

        Glide.with(this@MainActivity)
            .load(R.drawable.photo)
            .transition(DrawableTransitionOptions.withCrossFade(500))
            .transform(CircleCrop())
            .into(iv_avatar)

        mFragments.let {
            it.add(ArticleFragment.getInstance())
            it.add(ArticleFragment.getInstance())
            it.add(ArticleFragment.getInstance())
            it.add(ArticleFragment.getInstance())
            it.add(ArticleFragment.getInstance())
        }

        mAdapter.setFragments(mFragments)
        mAdapter.setTitles(mTitles)
        view_pager.adapter = mAdapter
        view_pager.offscreenPageLimit=5
        tablayout.setViewPager(view_pager)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_search -> {

                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

}

class MyPagerAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {

    var fragmentList: MutableList<Fragment> = mutableListOf()
    var titleList: MutableList<String>  = mutableListOf()

    override fun getCount(): Int {
        return fragmentList.size
    }

    override fun getItem(p0: Int): Fragment {
        return fragmentList[p0]
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return titleList[position]
    }

    fun setFragments(fragment: MutableList<Fragment>) {
        fragmentList = fragment;
    }

    fun setTitles(mTitles: MutableList<String>) {
        titleList = mTitles
    }

}

