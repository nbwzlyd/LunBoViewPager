package com.example.lunboviewpager

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.view.ViewPager
import com.example.viewpagerlibrary.view.view.BaseCarouselFigureViewPager
import java.util.ArrayList

class MainActivity : AppCompatActivity() {

    private var mViewPager: BaseCarouselFigureViewPager? = null
    private var mLunBoAdapter: LunBoAdapter? = null
    private var mImageList = ArrayList<Int>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    private fun init() {
        mViewPager = findViewById(R.id.viewPager)
        mLunBoAdapter = LunBoAdapter(this)
        mViewPager!!.adapter = mLunBoAdapter
        for (index in 1..3) {
            mImageList.add(R.mipmap.ic_launcher)
        }
        mLunBoAdapter!!.bindData(mImageList)
        mViewPager!!.setCanLunBo(true)
        mViewPager!!.currentItem=2
        mViewPager!!.addOnPageChangeListener(PageChangeListener())
    }

    inner class PageChangeListener : ViewPager.OnPageChangeListener {

        override fun onPageSelected(position: Int) {
        }

        override fun onPageScrollStateChanged(state: Int) {
            if (state== ViewPager.SCROLL_STATE_IDLE){
                mViewPager!!.goPageIndex()
            }
        }

        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        }
    }
}
