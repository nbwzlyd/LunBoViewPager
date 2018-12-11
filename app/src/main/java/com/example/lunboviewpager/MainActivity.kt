package com.example.lunboviewpager

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.widget.Toast
import com.example.viewpagerlibrary.adapter.AbsCarouselFigurePagerAdapter
import com.example.viewpagerlibrary.view.view.BaseCarouselFigureViewPager
import java.util.ArrayList

class MainActivity : AppCompatActivity() {

    private var mViewPager: BaseCarouselFigureViewPager? = null
    private var mLunBoAdapter: LunBoAdapter? = null
    private var mImageList = ArrayList<Int>()
    private val mContext =this
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

        mLunBoAdapter!!.setOnItemClickListener(object :AbsCarouselFigurePagerAdapter.ItemClickListener<Int>{
            override fun onClick(position: Int, content: Int?) {
                Toast.makeText(mContext,"这是第"+position+"张 内容是"+content,Toast.LENGTH_SHORT).show()
            }
        })
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
