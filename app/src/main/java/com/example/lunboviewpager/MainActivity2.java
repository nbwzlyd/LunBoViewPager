package com.example.lunboviewpager;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.viewpagerlibrary.view.view.BaseCarouselFigureViewPager;

import java.util.ArrayList;

//java 版本
public class MainActivity2 extends AppCompatActivity {
    private BaseCarouselFigureViewPager mViewPager;
    private LunBoAdapter mLunBoAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }
    private void initView(){
        mViewPager = findViewById(R.id.viewPager);
        mLunBoAdapter = new LunBoAdapter(this);
        mViewPager.setAdapter(mLunBoAdapter);
        ArrayList<Integer> list = new ArrayList<>();
        for (int i =0 ;i<3;i++){
            list.add(R.drawable.ic_launcher_background);
        }
        mLunBoAdapter.bindData(list);
        mViewPager.setCanLunBo(true);
        mViewPager.setCurrentItem(2);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                    if (state==ViewPager.SCROLL_STATE_IDLE){
                        mViewPager.goPageIndex();
                    }
            }
        });
    }
}
