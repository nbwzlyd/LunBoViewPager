package com.example.lunboviewpager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.viewpagerlibrary.adapter.AbsCarouselFigurePagerAdapter;

public class LunBoAdapter extends AbsCarouselFigurePagerAdapter<Integer> {
    private LayoutInflater mLayoutInflater;
    public LunBoAdapter(Context context) {
        super(context);
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    protected View createChildViewItem() {

        return  mLayoutInflater.inflate(R.layout.item_view,null);
        //or      ImageView imageView = new ImageView(mContext);
        //        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        //        return  imageView;


    }

    @Override
    protected void addChildData(View childView, final int position) {
        TextView textView = childView.findViewById(R.id.textView);
        final int realPosition = getRealPosition(position);
        textView.setText("第"+realPosition+"张");
        childView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mItemClickListener!=null){
                    mItemClickListener.onClick(realPosition,mRealList.get(realPosition));
                }
            }
        });
        //or  childView.setBackgroundResource(mData.get(position));
    }

    @Override
    public float getPageWidth(int position) {
        return 0.8f;
    }
}
