package com.navigation;

import android.content.Context;
import android.graphics.PointF;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class ChildViewPager extends ViewPager{
    /** ����ʱ���µĵ� **/
    PointF downP = new PointF();
    /** ����ʱ��ǰ�ĵ� **/
    PointF curP = new PointF(); 
    public OnSingleTouchListener onSingleTouchListener;
    public ChildViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public ChildViewPager(Context context) {
        super(context);
    }
    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {
        //�����ش����¼������λ�õ�ʱ�򣬷���true��
        //˵����onTouch�����ڴ˿ؼ�������ִ�д˿ؼ���onTouchEvent
        return true;
    }
    @Override
    public boolean onTouchEvent(MotionEvent arg0) {
        //ÿ�ν���onTouch�¼�����¼��ǰ�İ��µ�����
        curP.x = arg0.getX();
        curP.y = arg0.getY();

        if(arg0.getAction() == MotionEvent.ACTION_DOWN){
            //��¼����ʱ�������
            //�мǲ����� downP = curP �������ڸı�curP��ʱ��downPҲ��ı�
            downP.x = arg0.getX();
            downP.y = arg0.getY();
            //�˾������Ϊ��֪ͨ���ĸ�ViewPager���ڽ��е��Ǳ��ؼ��Ĳ�������Ҫ���ҵĲ������и���
            getParent().requestDisallowInterceptTouchEvent(true);
        } 
        if(arg0.getAction() == MotionEvent.ACTION_MOVE){
            //�˾������Ϊ��֪ͨ���ĸ�ViewPager���ڽ��е��Ǳ��ؼ��Ĳ�������Ҫ���ҵĲ������и���
            getParent().requestDisallowInterceptTouchEvent(true);
        }

        if(arg0.getAction() == MotionEvent.ACTION_UP){
            //��upʱ�ж��Ƿ��º����ֵ�����Ϊһ����
            //�����һ���㣬��ִ�е���¼����������Լ�д�ĵ���¼���������onclick
 
            if(Math.abs(downP.x-curP.x)<5&&Math.abs(downP.y-curP.y)<5){
            	onSingleTouch();
            	return true;
            }
        }
        return super.onTouchEvent(arg0);
    }
    /**
     * ����
     */
    public void onSingleTouch() {
        if (onSingleTouchListener!= null) {
            onSingleTouchListener.onSingleTouch();
        }
    }
    public void setOnSingleTouchListener(OnSingleTouchListener onSingleTouchListener) {
        this.onSingleTouchListener = onSingleTouchListener;
    }
}


