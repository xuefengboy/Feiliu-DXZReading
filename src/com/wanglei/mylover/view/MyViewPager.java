package com.wanglei.mylover.view;

import android.content.Context;
import android.graphics.PointF;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.Toast;
/**
 *  自定义ViewPager
 * @author xuefengz
 *
 */
public class MyViewPager extends ViewPager {
    /** 触摸时按下的点 **/
    PointF downP = new PointF();
    /** 触摸时当前的点 **/
    PointF curP = new PointF();
    OnSingleTouchListener onSingleTouchListener;
    float x_temp01 = 0.0f;
    float y_temp01 = 0.0f;
    float y_temp02 = 0.0f;
    public MyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyViewPager(Context context) {
        super(context);
    }
    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {
        // 当拦截触摸事件到达此位置的时候，返回true，
        // 说明将onTouch拦截在此控件，进而执行此控件的onTouchEvent
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent arg0) {
        // 每次进行onTouch事件都记录当前的按下的坐标
        curP.x = arg0.getX();
        curP.y = arg0.getY();

        if (arg0.getAction() == MotionEvent.ACTION_DOWN) {
            // 记录按下时候的坐标
            // 切记不可用 downP = curP ，这样在改变curP的时候，downP也会改变
            downP.x = arg0.getX();
            downP.y = arg0.getY();
            x_temp01 = curP.x;
            y_temp01 = curP.y;
            // 此句代码是为了通知他的父ViewPager现在进行的是本控件的操作，不要对我的操作进行干扰
            getParent().requestDisallowInterceptTouchEvent(true);
        }

        if (arg0.getAction() == MotionEvent.ACTION_MOVE) {
            // 此句代码是为了通知他的父ViewPager现在进行的是本控件的操作，不要对我的操作进行干扰
        	if(getCurrentItem()==0&&arg0.getX() - x_temp01>0){//
        		getParent().requestDisallowInterceptTouchEvent(false);//直接给父View
        	}else {
        		getParent().requestDisallowInterceptTouchEvent(true);
			}
        	
        }

        if (arg0.getAction() == MotionEvent.ACTION_UP) {
        	y_temp02 = curP.y;
//        	if(y_temp02-y_temp01>20){//向下滑动
//        		//Toast.makeText(getContext(), text, duration)
//        	}else if(y_temp01-y_temp02>20){//向上
//        		
//        	}
            // 在up时判断是否按下和松手的坐标为一个点
            // 如果是一个点，将执行点击事件，这是我自己写的点击事件，而不是onclick
            if (downP.x == curP.x && downP.y == curP.y) {
            	
                onSingleTouch();
                return true;
            }
        }

        return super.onTouchEvent(arg0);
    }

    /**
     * 单击
     */
    public void onSingleTouch() {
        if (onSingleTouchListener != null) {
            onSingleTouchListener.onSingleTouch();
        }
    }
    /**
     * 创建点击事件接口
     */
    public interface OnSingleTouchListener {
        public void onSingleTouch();
    }
    public void setOnSingleTouchListener(OnSingleTouchListener onSingleTouchListener) {
        this.onSingleTouchListener = onSingleTouchListener;
    }
   
    @Override
    public int getCurrentItem() {
    	return super.getCurrentItem();
    }
    
}
