# 简单的viewPager轮播

### 功能简单，不会集成N多用不到的功能，只有轮播效果
### 留着自用以及 kotlin学习

> 主要用到的知识
> 
> 泛型 、kotlin
>
##使用方法

 1.继承AbsCarouselFigurePagerAdapter< T>抽象类实现自己的具体类
 T 是泛型 ，传入你具体需要的实体类
 
 2.重写相关方法  createChildViewItem（） 生成itemView 
 addChildData 绑定数据

```
 @Override
    protected View createChildViewItem() {
    }

    @Override
    protected void addChildData(View childView, final int position) {
    
    }
```

 自此，adapter的方法我们就写完了

viewPager 的轮播逻辑已经封装好，直接使用即可
方法如下
` mLunBoAdapter.bindData(list);
        mViewPager.setCanLunBo(true);
        mViewPager.setCurrentItem(2); `

其中list 是源数据，会自动生成轮播数据
mViewPager.setCanLunBo 是否可以轮播
mViewPager.setCurrentItem 讲当前的item设置为2 ，具体原因可看代码
除此之外，还有个重要方法需要重写

```
goPageIndex()
```

调用方式如下

```
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
```
如果你需要点击事件
调用adapter 中setOnItemClickListener 方法即可，在调用之前，记得在addChildData方法中设置view的点击事件

```
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
```

```
mLunBoAdapter.setOnItemClickListener(new AbsCarouselFigurePagerAdapter.ItemClickListener<Integer>() {
            @Override
            public void onClick(int position, Integer content) {

            }
        });
```
### 重要参数和方法说明
AbsCarouselFigurePagerAdapter中
` mData 生成的轮播数据，会比原始数据多4个 `
` mRealList 原始数据源 `
` mRealPosition 逻辑上的真实位置，而不是list中的位置，在使用该参数之前，请调用getRealPosition(int originPosition)`

差不多就是这样，效果图如下：
![示例图](https://github.com/nbwzlyd/LunBoViewPager/blob/master/app/gif/code.gif)
