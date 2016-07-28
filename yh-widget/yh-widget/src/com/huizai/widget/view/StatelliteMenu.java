package com.huizai.widget.view;

import com.huizai.widget.R;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.view.animation.Animation.AnimationListener;

/**
 */
public class StatelliteMenu extends ViewGroup implements OnClickListener{
	
	private static final int POS_LEFT_TOP = 0;
	private static final int POS_LEFT_BOTTOM = 1;
	private static final int POS_RIGHT_TOP = 2;
	private static final int POS_RIGHT_BOTTOM = 3;
	
	public enum Position{
		LEFT_TOP,LEFT_BOTTOM,RIGHT_TOP,RIGHT_BOTTOM
	}

	public enum Status{
		OPEN,CLOSE
	}
	
	private Position mPosition = Position.RIGHT_BOTTOM;
	private Status  mStatus = Status.CLOSE;
	private int radius ;
	private View MainBtn;

	private OnMenuItemClickListener mMenuItemClickListener;
	
	public interface OnMenuItemClickListener
	{
		void onClick(View view, int pos);
	}

	public void setOnMenuItemClickListener(
			OnMenuItemClickListener mMenuItemClickListener)
	{
		this.mMenuItemClickListener = mMenuItemClickListener;
	}

	public StatelliteMenu(Context context) {
		this(context,null);
	}
	
	public StatelliteMenu(Context context, AttributeSet attrs) {
		this(context, attrs , 0);
	}
	
	public StatelliteMenu(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		radius = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100, getResources().getDisplayMetrics());
		TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
				R.styleable.statellitemenu, defStyle, 0);
		
		int pos = a.getInt(R.styleable.statellitemenu_statellitemenu_position, POS_RIGHT_BOTTOM);
		switch(pos){
		case POS_LEFT_TOP:
			mPosition = Position.LEFT_TOP;
			break;
		case POS_LEFT_BOTTOM:
			mPosition = Position.LEFT_BOTTOM;
			break;
		case POS_RIGHT_TOP:
			mPosition = Position.RIGHT_TOP;
			break;
		case POS_RIGHT_BOTTOM:
			mPosition = Position.RIGHT_BOTTOM;
			break;
		}
		
		radius = (int) a.getDimension(R.styleable.statellitemenu_statellitemenu_radius, TypedValue
				.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100,
						getResources().getDisplayMetrics()));
		a.recycle();
	}
	
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
	{
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		int count = getChildCount();
		for (int i = 0; i < count; i++)
		{
			measureChild(getChildAt(i), widthMeasureSpec, heightMeasureSpec);
		}
	}

	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		
		if(changed){
			LocMainBtn();
			int count = getChildCount();
			
			for(int i=0 ;i<count-1;i++){
				View child = getChildAt(i+1);
				child.setVisibility(View.GONE);
				
				int cl = (int) (radius * Math.sin(Math.PI / 2 / (count - 2)
						* i));
				int ct = (int) (radius * Math.cos(Math.PI / 2 / (count - 2)
						* i));
				
				int cWidth = child.getMeasuredWidth();
				int cHeight = child.getMeasuredHeight();
				
				if (mPosition == Position.LEFT_BOTTOM
						|| mPosition == Position.RIGHT_BOTTOM)
				{
					ct = getMeasuredHeight() - cHeight - ct;
				}
				if (mPosition == Position.RIGHT_TOP
						|| mPosition == Position.RIGHT_BOTTOM)
				{
					cl = getMeasuredWidth() - cWidth - cl;
				}
				child.layout(cl  , ct  , cl + cWidth  , ct + cHeight );
			}
		}
	}

	private void LocMainBtn() {
		MainBtn = getChildAt(0);
		MainBtn.setOnClickListener(this);
		int l=0;
		int t=0;
		int width = MainBtn.getMeasuredWidth();
		int height = MainBtn.getMeasuredHeight();
		switch(mPosition){
		case LEFT_TOP:
			l =0;
			t =0;
			break;
		case LEFT_BOTTOM:
			l =0;
			t = getMeasuredHeight() - height;
			break;
		case RIGHT_TOP:
			l= getMeasuredWidth() - width;
			t = 0;
			break;
		case RIGHT_BOTTOM:
			l= getMeasuredWidth() - width;
			t = getMeasuredHeight() - height;
			break;
		}
		MainBtn.layout(l, t, l + width, t + width);
	}

	public void onClick(View v) {
		rotateMainBtn(v,0,360f,300);
		toggleMenu(300);
	}
	
	
	
	public void toggleMenu(int duration) {
		int count = getChildCount();
		for(int i=0;i<count -1;i++){
			final View child = getChildAt(i+1);
			child.setVisibility(View.VISIBLE);
			
			int cl = (int) (radius * Math.sin(Math.PI / 2 / (count - 2) * i));
			int ct = (int) (radius * Math.cos(Math.PI / 2 / (count - 2) * i));
			
			int xflag = 1;
			int yflag = 1;
			
			if(mPosition == Position.LEFT_TOP || mPosition == Position.LEFT_BOTTOM){
				xflag =-1;
			}else if(mPosition == Position.LEFT_TOP || mPosition == Position.RIGHT_TOP){
				yflag =-1;
			}
			
			AnimationSet animSet = new AnimationSet(true);
			Animation tranAnim = null;
			
			if(mStatus == Status.OPEN){
				tranAnim = new TranslateAnimation(0,xflag*cl,0,yflag*ct);
				child.setFocusable(false);
				child.setClickable(false);
			}else{
				tranAnim = new TranslateAnimation(xflag*cl,0,yflag*ct,0);
				child.setFocusable(true);
				child.setClickable(true);
			}
			tranAnim.setFillAfter(true);
			tranAnim.setDuration(duration);
			tranAnim.setStartOffset((i * 100) / count);
			tranAnim.setAnimationListener(new AnimationListener()
			{
				public void onAnimationStart(Animation animation)
				{
				}
				public void onAnimationRepeat(Animation animation)
				{
				}
				public void onAnimationEnd(Animation animation)
				{
					if (mStatus == Status.CLOSE)
					{
						child.setVisibility(View.GONE);
					}
				}
			});
			RotateAnimation rotateAnim = new RotateAnimation(0, 720,
					Animation.RELATIVE_TO_SELF, 0.5f,
					Animation.RELATIVE_TO_SELF, 0.5f);
			rotateAnim.setDuration(duration);
			rotateAnim.setFillAfter(true);
			
			animSet.addAnimation(rotateAnim);
			animSet.addAnimation(tranAnim);
			child.startAnimation(animSet);
			
			final int pos = i + 1;
			child.setOnClickListener(new OnClickListener(){
				public void onClick(View v) {
					if(mMenuItemClickListener != null){
						mMenuItemClickListener.onClick(child,pos);
					}
					setClickAnimation(pos -1);
					changeMenuState();
				}
			} );
		}
		changeMenuState();
	}
	
	public boolean isOpen()
	{
		return mStatus == Status.OPEN;
	}
	
	private void setClickAnimation(int position){
		int count = getChildCount();
		for(int i=0;i<count-1;i++){
			View child = getChildAt(i+1);
			if(position == i){
				child.startAnimation(scaleBigAnim(300));
			}else{
				child.startAnimation(scaleSmallAnim(300));
			}
			child.setFocusable(false);
			child.setClickable(false);
		}
	}
	
	private Animation scaleSmallAnim(int duration) {
		AnimationSet animSet = new AnimationSet(true);
		Animation scaleAnim = new ScaleAnimation(1.0f, 0.0f, 1.0f, 0.0f,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		AlphaAnimation alphaAnim = new AlphaAnimation(1f, 0.0f);
		animSet.addAnimation(scaleAnim);
		animSet.addAnimation(alphaAnim);
		animSet.setDuration(duration);
		animSet.setFillAfter(true);
		return animSet;
	}

	private Animation scaleBigAnim(int duration) {
		AnimationSet animSet = new AnimationSet(true);
		Animation scaleAnim = new ScaleAnimation(1.0f, 4.0f, 1.0f,4.0f,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		AlphaAnimation alphaAnim = new AlphaAnimation(1f, 0.0f);
		animSet.addAnimation(scaleAnim);
		animSet.addAnimation(alphaAnim);
		animSet.setDuration(duration);
		animSet.setFillAfter(true);
		return animSet;
	}

	private void changeMenuState(){
		mStatus = (mStatus==Status.OPEN)?Status.CLOSE:Status.OPEN;
	}

	private void rotateMainBtn(View v,float start,float end,int duration){
		RotateAnimation anim = new RotateAnimation(start, end,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		anim.setDuration(duration);
		anim.setFillAfter(true);
		v.startAnimation(anim);
	}

}
