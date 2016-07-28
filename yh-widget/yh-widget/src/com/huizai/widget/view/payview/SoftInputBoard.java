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
import android.widget.TextView;

import com.huizai.widget.R;

public class SoftInputBoard extends RelativeLayout implements View.OnClickListener{
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

	private boolean userIsLongPress = false;

	@Override
	public void onClick(View v) {

		String value = "";

		int id = v.getId();
		if(id == R.id.tv0){
			value = "0";
		}else if(id == R.id.tv1){
			value = "1";
		}else if(id == R.id.tv2){
			value = "2";
		}else if(id == R.id.tv3){
			value = "3";
		}else if(id == R.id.tv4){
			value = "4";
		}else if(id == R.id.tv5){
			value = "5";
		}else if(id == R.id.tv6){
			value = "6";
		}else if(id == R.id.tv7){
			value = "7";
		}else if(id == R.id.tv8){
			value = "8";
		}else if(id == R.id.tv9){
			value = "9";
		}else if(id == R.id.iv_delete){
			value = "delete";
		}else if(id == R.id.tvx){
			value = "X";
		}

		if(payListener != null){
			payListener.chooseWay(value);
		}

	}

	public interface ChoosePayWayListener{
		void chooseWay(String value);
	}

	public ChoosePayWayListener payListener;

	public void setOnChoosePayWayListener(ChoosePayWayListener payListener){
		this.payListener = payListener;
	}

	private final static String TAG = "LoginView";
	public SoftInputBoard(Context context) {
		super(context);
		init(context);
	}

	public SoftInputBoard(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public SoftInputBoard(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	private void init(Context context) {
		setDescendantFocusability(FOCUS_AFTER_DESCENDANTS);
		setFocusable(true);
		mScroller = new Scroller(context);
		mScreenHeigh = BaseTools.getWindowHeigh(context);
		mScreenWidth = BaseTools.getWindowWidth(context);
		this.setBackgroundColor(Color.argb(0, 0, 0, 0));
		final View view = LayoutInflater.from(context).inflate(R.layout.view_soft_input,null);

		TextView tv0 = (TextView)view.findViewById(R.id.tv0);
		TextView tv1 = (TextView)view.findViewById(R.id.tv1);
		TextView tv2 = (TextView)view.findViewById(R.id.tv2);
		TextView tv3 = (TextView)view.findViewById(R.id.tv3);
		TextView tv4 = (TextView)view.findViewById(R.id.tv4);
		TextView tv5 = (TextView)view.findViewById(R.id.tv5);
		TextView tv6 = (TextView)view.findViewById(R.id.tv6);
		TextView tv7 = (TextView)view.findViewById(R.id.tv7);
		TextView tv8 = (TextView)view.findViewById(R.id.tv8);
		TextView tv9 = (TextView)view.findViewById(R.id.tv9);
		TextView tvx = (TextView)view.findViewById(R.id.tvx);

		final RelativeLayout ivDelete = (RelativeLayout)view.findViewById(R.id.iv_delete);
		ivDelete.setClickable(true);

		tv0.setOnClickListener(this);
		tv1.setOnClickListener(this);
		tv2.setOnClickListener(this);
		tv3.setOnClickListener(this);
		tv4.setOnClickListener(this);
		tv5.setOnClickListener(this);
		tv6.setOnClickListener(this);
		tv7.setOnClickListener(this);
		tv8.setOnClickListener(this);
		tv9.setOnClickListener(this);
		tvx.setOnClickListener(this);

		ivDelete.setOnClickListener(this);
		ivDelete.setOnLongClickListener(new OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				if(payListener != null){
					payListener.chooseWay("xdelete");
				}
				return false;
			}
		});


//		ivDelete.setOnTouchListener(new OnTouchListener() {
//			@Override
//			public boolean onTouch(View v, MotionEvent event) {
//				try{
//					TimeUnit.MILLISECONDS.sleep(200);
//				}catch (Exception e){
//				}
//
//				if(ivDelete.isPressed()){
//					postDelayed(new Runnable() {
//						@Override
//						public void run() {
//							if(payListener != null){
//								payListener.chooseWay("delete");
//							}
//						}
//					},500);
//				}
//				return false;
//			}
//		});



//		View ZFB = view.findViewById(R.id.ll_login_qq);
//		ZFB.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				if(payListener != null){
//					payListener.chooseWay(0);
//				}
//			}
//		});
//		View WEIXIN = view.findViewById(R.id.ll_login_sina);
//		WEIXIN.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				if(payListener != null){
//					payListener.chooseWay(1);
//				}
//			}
//		});
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
		SoftInputBoard.this.scrollTo(0, mScreenHeigh);
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

//	@Override
//	public boolean onTouchEvent(MotionEvent event) {
//		// TODO Auto-generated method stub
//		switch (event.getAction()) {
//		case MotionEvent.ACTION_DOWN:
//			downY = (int) event.getY();
//			Log.d(TAG, "downY = " + downY);
//			if(isShow){
//				return true;
//			}
//			break;
//		case MotionEvent.ACTION_MOVE:
//			moveY = (int) event.getY();
//			scrollY = moveY - downY;
//			if (scrollY > 0) {
//				if(isShow){
//					scrollTo(0, -Math.abs(scrollY));
//				}
//			}else{
//				if(mScreenHeigh - this.getTop() <= viewHeight && !isShow){
//					scrollTo(0, Math.abs(viewHeight - scrollY));
//				}
//			}
//			break;
//		case MotionEvent.ACTION_UP:
//			upY = (int) event.getY();
//			if(isShow){
//				if( this.getScrollY() <= -(viewHeight /2)){
//					startMoveAnim(this.getScrollY(),-(viewHeight - this.getScrollY()), mDuration);
//					isShow = false;
//					Log.d("isShow", "false");
//				} else {
//					startMoveAnim(this.getScrollY(), -this.getScrollY(), mDuration);
//					isShow = true;
//					Log.d("isShow", "true");
//				}
//			}
//			Log.d("this.getScrollY()", ""+this.getScrollY());
//			changed();
//			break;
//		case MotionEvent.ACTION_OUTSIDE:
//			Log.d(TAG, "ACTION_OUTSIDE");
//			break;
//		default:
//			break;
//		}
//		return super.onTouchEvent(event);
//	}

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
			SoftInputBoard.this.startMoveAnim(-viewHeight,   viewHeight, mDuration);
			isShow = true;
			Log.d("isShow", "true");
			changed();
		}
	}


	public int getSoftInputBoardHeight(){
		return viewHeight;
	}

	public void dismiss(){
		if(isShow && !isMoving){
			SoftInputBoard.this.startMoveAnim(0, -viewHeight, mDuration);
			isShow = false;
			Log.d("isShow", "false");
			changed();
			if(l!=null){
				l.dismiss();
			}
		}
	}

	public interface dismissListener{
		void dismiss();
	}

	private dismissListener l;

	public void setOnDismissListener(dismissListener l){
		this.l = l;
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
