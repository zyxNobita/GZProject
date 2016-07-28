package com.huizai.widget.view.payview;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Scroller;

import com.huizai.widget.R;
public class PayView extends RelativeLayout {
	private Scroller mScroller;
	private int mScreenHeigh = 0;
	private int mScreenWidth = 0;
	private int downY = 0;
	private int moveY = 0;
	private int scrollY = 0;
	private int upY = 0;
	private Boolean isMoving = false;
	private int viewHeight = 0;
	public boolean isShow = false;
	public boolean mEnabled = true;
	public boolean mOutsideTouchable = true;
	private int mDuration = 800;
	
	public interface ChoosePayWayListener{
		void chooseWay(int pos);
	}
	
	public ChoosePayWayListener payListener;
	
	public void setOnChoosePayWayListener(ChoosePayWayListener payListener){
		this.payListener = payListener;
	}
	
	private final static String TAG = "LoginView";
	public PayView(Context context) {
		super(context);
		init(context);
	}

	public PayView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public PayView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	private View ZFB,WEIXIN;

	private void init(Context context) {
		setDescendantFocusability(FOCUS_AFTER_DESCENDANTS);
		setFocusable(true);
		mScroller = new Scroller(context);
		mScreenHeigh = BaseTools.getWindowHeigh(context);
		mScreenWidth = BaseTools.getWindowWidth(context);
		this.setBackgroundColor(Color.argb(0, 0, 0, 0));
		final View view = LayoutInflater.from(context).inflate(R.layout.view_login,null);
		ZFB = view.findViewById(R.id.ll_login_qq);
		ZFB.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(payListener != null){
					payListener.chooseWay(0);
				}
			}
		});
		WEIXIN = view.findViewById(R.id.ll_login_sina);
		WEIXIN.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(payListener != null){
					payListener.chooseWay(1);
				}
			}
		});
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
		addView(view, params);
		this.setBackgroundColor(Color.argb(0, 0, 0, 0));
		view.post(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				viewHeight = view.getHeight();
			}
		});
		PayView.this.scrollTo(0, mScreenHeigh);
		ImageView btn_close = (ImageView)view.findViewById(R.id.btn_close);
		btn_close.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dismiss();
			}
		});
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		if(!mEnabled){
			return false;
		}
		return super.onInterceptTouchEvent(ev);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			downY = (int) event.getY();
			Log.d(TAG, "downY = " + downY);
			if(isShow){
				return true;
			}
			break;
		case MotionEvent.ACTION_MOVE:
			moveY = (int) event.getY();
			scrollY = moveY - downY;
			if (scrollY > 0) {
				if(isShow){
					scrollTo(0, -Math.abs(scrollY));
				}
			}else{
				if(mScreenHeigh - this.getTop() <= viewHeight && !isShow){
					scrollTo(0, Math.abs(viewHeight - scrollY));
				}
			}
			break;
		case MotionEvent.ACTION_UP:
			upY = (int) event.getY();
			if(isShow){
				if( this.getScrollY() <= -(viewHeight /2)){
					startMoveAnim(this.getScrollY(),-(viewHeight - this.getScrollY()), mDuration);
					isShow = false;
					Log.d("isShow", "false");
				} else {
					startMoveAnim(this.getScrollY(), -this.getScrollY(), mDuration);
					isShow = true;
					Log.d("isShow", "true");
				}
			}
			Log.d("this.getScrollY()", ""+this.getScrollY());
			changed();
			break;
		case MotionEvent.ACTION_OUTSIDE:
			Log.d(TAG, "ACTION_OUTSIDE");
			break;
		default:
			break;
		}
		return super.onTouchEvent(event);
	}
	
	public void startMoveAnim(int startY, int dy, int duration) {
		isMoving = true;
		mScroller.startScroll(0, startY, 0, dy, duration);
		invalidate();
	}
	
	@Override
	public void computeScroll() {
		if (mScroller.computeScrollOffset()) {
			scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
			postInvalidate();
			isMoving = true;
		} else {
			isMoving = false;
		}
		super.computeScroll();
	}
	
	public void show(){
		if(!isShow && !isMoving){
			PayView.this.startMoveAnim(-viewHeight,   viewHeight, mDuration);
			isShow = true;
			Log.d("isShow", "true");
			changed();
		}
	}
	
	public void dismiss(){
		if(isShow && !isMoving){
			PayView.this.startMoveAnim(0, -viewHeight, mDuration);
			isShow = false;
			Log.d("isShow", "false");
			changed();
		}
	}
	
	public boolean isShow(){
		return isShow;
	}
	
	public boolean isSlidingEnabled() {
		return mEnabled;
	}
	
	public void setSlidingEnabled(boolean enabled) {
		mEnabled = enabled;
	}
	
	public void setOnStatusListener(onStatusListener listener){
		this.statusListener = listener;
	}
	
    public void setOutsideTouchable(boolean touchable) {
        mOutsideTouchable = touchable;
    }
	public void changed(){
		if(statusListener != null){
			if(isShow){
				statusListener.onShow();
			}else{
				statusListener.onDismiss();
			}
		}
	}
	
	public onStatusListener statusListener;
	
	public interface onStatusListener{
		public void onShow();
		public void onDismiss();
	}
	
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		// TODO Auto-generated method stub
		super.onLayout(changed, l, t, r, b);
	}
}
