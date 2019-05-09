package com.feyolny.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;

/**
 * Created by feyolny on 2019/5/8.
 * Describtion:带动画的卡片容器
 */

public class AnimatedCardContainer extends ViewGroup {

    /**
     * 上下文
     */
    private Context mContext;

    /**
     * 容器的宽高
     */
    private int containerHeight, containerWidth;

    /**
     * 前台布局，后台布局
     */
    private View frontView, backView;


    /**
     * 前台布局的宽高
     */
    private int frontViewWidth, frontViewHeight;

    /**
     * 后台布局的宽高
     */
    private int backViewWidth, backViewHeight;

    /**
     * 自定义的容器适配器
     */
    private ContainerAdapter containerAdapter;

    /**
     * 滚动辅助器
     */
    private Scroller mScroller;

    /**
     * 是否在滚动
     */

    private boolean isScrolling = false;

    /**
     * 动画的时长
     */
    private int duration;


    public AnimatedCardContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
        initContainer(context, attrs);
    }

    private void initContainer(Context context, AttributeSet attrs) {
        mContext = context;

        mScroller = new Scroller(mContext);

        //获取属性
        TypedArray typedArray = mContext.obtainStyledAttributes(attrs, R.styleable.AnimatedCardContainer);

        //获取前台view
        int front = typedArray.getResourceId(R.styleable.AnimatedCardContainer_frontView, 0);
        if (front == 0) {
            throw new IllegalArgumentException("AnimatedCardContainer must have a front view");
        } else {
            frontView = LayoutInflater.from(mContext).inflate(front, this, false);
            addView(frontView);
        }

        //获取后台view,如果没有抛出异常提示
        int bottom = typedArray.getResourceId(R.styleable.AnimatedCardContainer_backView, 0);
        if (bottom == 0) {
            throw new IllegalArgumentException("AnimatedCardContainer must have amust have back view");
        } else {
            backView = LayoutInflater.from(mContext).inflate(bottom, this, false);
            addView(backView);
        }

        //获取动画时长,默认1s
        duration = typedArray.getInt(R.styleable.AnimatedCardContainer_duration, 1000);

        //使用完毕，循环回收
        typedArray.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        //循化测量子View
        //容器的宽高取子View中的最大值
        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            measureChild(view, widthMeasureSpec, heightMeasureSpec);
            containerWidth = containerWidth > view.getMeasuredWidth() ? containerWidth : view.getMeasuredWidth();
            containerHeight = containerHeight > view.getMeasuredHeight() ? containerHeight : view.getMeasuredHeight();
        }

        setMeasuredDimension(containerWidth, containerHeight);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //测量完成后，获取测量后前台、后台的宽高
        frontViewWidth = frontView.getMeasuredWidth();
        frontViewHeight = frontView.getMeasuredHeight();

        backViewWidth = backView.getMeasuredWidth();
        backViewHeight = backView.getMeasuredHeight();
    }

    @Override
    protected void onLayout(boolean b, int left, int top, int right, int bottom) {


        if (frontView != null) {
            //摆放前台View
            frontView.layout(0, 0, frontViewWidth, frontViewHeight);
            //前台View数据的绑定
            if (containerAdapter != null)
                containerAdapter.bindFrontView(frontView, this);
        }

        if (backView != null) {
            //摆放后台View
            backView.layout(0, containerHeight, backViewWidth, containerHeight + backViewHeight);
            if (containerAdapter != null) {
                //后台View数据的绑定
                containerAdapter.bindBackView(backView, this);
                //设置backView点击关闭，非滑动关闭
                setBackViewClickClose();
            }
        }

    }

    /**
     * 设置backView点击关闭，非滑动关闭
     */
    private void setBackViewClickClose() {

        //当后台View显示时
        //进行滑动和点击事件的判断，点击关闭，滑动不做任何动作
        backView.setOnTouchListener(new OnTouchListener() {

            float downX = 0, downY = 0, moveX = 0, moveY = 0;

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        downX = motionEvent.getX();
                        downY = motionEvent.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        moveX += motionEvent.getX() - downX;
                        moveY += motionEvent.getY() - downY;
                        break;
                    case MotionEvent.ACTION_UP:
                        if (Math.abs(moveX) < 10 && Math.abs(moveY) < 10) {
                            close();
                        }
                        moveX = 0;
                        moveY = 0;
                        break;

                }
                return true;
            }
        });

    }

    /**
     * 进行适配器绑定
     *
     * @param containerAdapter
     */
    public void setContainerAdapter(ContainerAdapter containerAdapter) {
        this.containerAdapter = containerAdapter;
    }

    /**
     * 展示backView
     */
    public void open() {
        if (!isScrolling) {
            mScroller.startScroll(0, 0, 0, containerHeight, duration);
            invalidate();
        }

    }

    /**
     * 关闭backView
     */
    public void close() {
        if (!isScrolling) {
            mScroller.startScroll(0, containerHeight, mScroller.getCurrX(), -containerHeight, duration);
            invalidate();
        }
    }

    @Override
    public void computeScroll() {
        // 先判断mScroller滚动是否完成
        if (mScroller.computeScrollOffset()) {
            // 这里调用View的scrollTo()完成实际的滚动
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            // 必须调用该方法，否则不能看到滚动效果
            invalidate();
            isScrolling = true;
        } else {
            isScrolling = false;
        }
    }
}
