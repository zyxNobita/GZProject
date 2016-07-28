package com.huizai.widget.view.payview;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Scroller;

import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by Nipuream on 2016/4/15 0015.
 */
public class AutoPopLayout extends RelativeLayout {


    private Scroller mScroller;
    private boolean isMove = false;
    private SoftInputBoard softInputBoard;

    private Context context;

    private int currentCursorIndex = 0;

    private static final int ADD = 0x45;
    private static final int DE = 0x46;

    //默认是加
    private int addOrde = ADD;


    public AutoPopLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        DecelerateInterpolator interpolator = new DecelerateInterpolator();
        mScroller = new Scroller(context,interpolator);


        post(new Runnable() {
            @Override
            public void run() {
                softInputBoard = new SoftInputBoard(AutoPopLayout.this.context);
                RelativeLayout rl = (RelativeLayout) AutoPopLayout.this.getParent();
                rl.addView(softInputBoard);
                LayoutParams params = (LayoutParams) softInputBoard.getLayoutParams();
                params.width = LayoutParams.MATCH_PARENT;
                params.height = LayoutParams.WRAP_CONTENT;
                params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                softInputBoard.setLayoutParams(params);


                softInputBoard.setOnDismissListener(new SoftInputBoard.dismissListener() {
                    @Override
                    public void dismiss() {
                        AutoPopLayout.this.startMoveAnim(AutoPopLayout.this.getScrollY(),- pixDistance,500);
                        pixDistance = 0;
                    }
                });

                softInputBoard.setOnChoosePayWayListener(new SoftInputBoard.ChoosePayWayListener() {
                    @Override
                    public void chooseWay(String value) {

                        try{
                            String preStr =  ed.getText().toString();

                            //银行帐号
                            if(!TextUtils.equals(value,"delete")){

                                //长按删除
                                if(TextUtils.equals(value,"xdelete")){
                                    ed.setText("");
                                    currentCursorIndex = 0;
                                    return;
                                }
                                addOrde = ADD;
                                currentCursorIndex = getEditTextCursorIndex(ed);
                                insertText(ed,value);

                                preStr = ed.getText().toString();

                                ed.setText(preStr);
                            }else{
                                if(!TextUtils.isEmpty(preStr)){
                                    addOrde = DE;
                                    currentCursorIndex = getEditTextCursorIndex(ed);
                                    deleteText(ed);
                                    preStr = ed.getText().toString();
                                    ed.setText(preStr);
                                }
                            }
                        }catch (Exception e){
                        }
                    }
                });
            }
        });

    }


    /**获取EditText光标所在的位置*/
    private int getEditTextCursorIndex(EditText mEditText){
        return mEditText.getSelectionStart();
    }
    /**向EditText指定光标位置插入字符串*/
    private void insertText(EditText mEditText, String mText){
        mEditText.getText().insert(getEditTextCursorIndex(mEditText), mText);
    }
    /**向EditText指定光标位置删除字符串*/
    private void deleteText(EditText mEditText){
        if(!TextUtils.isEmpty(mEditText.getText().toString())){
            mEditText.getText().delete(getEditTextCursorIndex(mEditText)-1, getEditTextCursorIndex(mEditText));
        }
    }


    private int pixDistance = 0;


    private void AutoPilled(final EditText view){

        currentCursorIndex = 0;

        view.post(new Runnable() {
            @Override
            public void run() {
//                int screenHeight = UIUtil.getScreenHeight(getActivity().getWindowManager());
                int screenHeight = BaseTools.getWindowHeigh(context);
                int softInputHeight = softInputBoard.getSoftInputBoardHeight();

                /**
                 *
                 *
                 * ---------------------------------------------------  <---- screenHeight-softInputHeight
                 * |   ---------------------------                   |
                 * |  |         输入框            |                  |
                 * |  ----------------------------  <---- bottom    |
                 * |                                               |
                 * |                                               |
                 * ---------------------------------------------------
                 *
                 *
                 */

                int bottom = 0;
                Rect rect = new Rect();
                boolean isGet = view.getGlobalVisibleRect(rect);
                if(isGet){
                    bottom = rect.bottom;
                }

                if(bottom != 0) {
                    if (bottom > (screenHeight - softInputHeight)) {
                        pixDistance = bottom - (screenHeight - softInputHeight);
                        AutoPopLayout.this.startMoveAnim(AutoPopLayout.this.getScrollY(),pixDistance,500);
                    }
                }
            }
        });
    }

    private EditText ed;
    private WeakReference<Activity> ref;

    // 隐藏系统键盘
    public void hideSoftInputMethod(final List<EditText> ets, WeakReference<Activity> ref){

        this.ref = ref;
        currentCursorIndex = 0;


        for(final EditText ed:ets){
            ed.setOnTouchListener(new OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {

//                focus = WhichFocus.BANK_CARD;
//                AutoPopLayout.this.hideSoftInputMethod(view,ref);
                    AutoPopLayout.this.ed = ed;

                    if(!softInputBoard.isShow){
                        softInputBoard.show();
                    }

                    if(! AutoPopLayout.this.isMove()){
                        if(softInputBoard.isShow){
                            AutoPilled(ed);
                        }
                    }

                    return false;
                }
            });

            ed.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }
                @Override
                public void afterTextChanged(Editable s) {
                    String value = s.toString();
                    int length = value.length();

                    int index = getEditTextCursorIndex(ed);

                    try{
                        if(currentCursorIndex < length){
                            if(addOrde == ADD){
                                ed.setSelection(currentCursorIndex+1);
                            }else{
                                ed.setSelection(currentCursorIndex-1);
                            }
                        }else{
                            ed.setSelection(length);
                        }
                    }catch (Exception e){
                    }
                }
            });

            ref.get().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

            int currentVersion = android.os.Build.VERSION.SDK_INT;
            String methodName = null;
            if(currentVersion >= 16){
                // 4.2
                methodName = "setShowSoftInputOnFocus";
            }
            else if(currentVersion >= 14){
                // 4.0
                methodName = "setSoftInputShownOnFocus";
            }

            if(methodName == null){
                ed.setInputType(InputType.TYPE_NULL);
            }
            else{
                Class<EditText> cls = EditText.class;
                Method setShowSoftInputOnFocus;
                try {
                    setShowSoftInputOnFocus = cls.getMethod(methodName, boolean.class);
                    setShowSoftInputOnFocus.setAccessible(true);
                    setShowSoftInputOnFocus.invoke(ed, false);
                } catch (NoSuchMethodException e) {
                    ed.setInputType(InputType.TYPE_NULL);
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IllegalArgumentException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }


    public void destroyAutoPopLayout(){
        if(ref != null){
            ref = null;
            this.removeAllViews();
        }
    }


    public void startMoveAnim(int startY, int dy, int duration) {
        isMove = true;
        mScroller.startScroll(0, startY, 0, dy, duration);
        invalidate();//通知UI线程的更新
    }


    @Override
    public void computeScroll() {
        //判断是否还在滚动，还在滚动为true
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            //更新界面
            postInvalidate();
            isMove = true;
        } else {
            isMove = false;
        }
        super.computeScroll();
    }


    public boolean isMove(){
        return isMove;
    }



}
