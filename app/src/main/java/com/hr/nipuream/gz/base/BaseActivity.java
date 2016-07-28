package com.hr.nipuream.gz.base;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.DisplayMetrics;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.hr.nipuream.gz.GZApplication;
import com.hr.nipuream.gz.R;
import com.hr.nipuream.gz.controller.other.bean.UpdateBean;
import com.hr.nipuream.gz.service.UpdateVersionServer;
import com.hr.nipuream.gz.util.Logger;
import com.hr.nipuream.gz.util.Util;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;

import carbon.widget.Button;
import carbon.widget.ProgressBar;
import carbon.widget.Snackbar;

public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        GZApplication.getInstance().getEventBus().register(this);

        myHandler = new MyHandler(new WeakReference<Activity>(this));
        fragmentManager = getSupportFragmentManager();

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            Window window = getWindow();
//            // Translucent status bar
//            window.setFlags(
//                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
//                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//        }

        GZApplication.getInstance().putAct(this);
        registerGZTask();
    }

//    protected void initTitleBar(){
//        View grapView = findViewById(R.id.app_header_gasp);
//        LinearLayout ll = (LinearLayout) findViewById(R.id.header_ll);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)grapView.getLayoutParams();
//            params.height = getStatusBarHeight(this);
//            grapView.setLayoutParams(params);
//
//            ViewGroup.LayoutParams params1 = (ViewGroup.LayoutParams) ll.getLayoutParams();
//            params1.height = dip2px(this,48) + getStatusBarHeight(this);
//            ll.setLayoutParams(params1);
//        }else{
//            grapView.setVisibility(View.GONE);
//            ViewGroup.LayoutParams params1 = (ViewGroup.LayoutParams) ll.getLayoutParams();
//            params1.height = dip2px(this,48);
//            ll.setLayoutParams(params1);
//        }
//    }

    //*************************** 更新数据 *********************************************

    protected void registerGZTask(){
        try{
            registerTask();
        }catch (Exception e){
            Logger.getLogger().w("register task has error"+e.toString());
        }
    };


    @Override
    public void onClick(View v) {
        try{
            clickView(v);
        }catch (Exception e){
            Logger.getLogger().e("click has error"+e.toString());
        }

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateData(Object object){
        try{
            updateGZdata(object);
        }catch (Exception e){
            Logger.getLogger().e("update data has error"+e.toString());
        }
    }

    public void registerTask(){}

    public void clickView(View view){}

    public void updateGZdata(Object object){}

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        myHandler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }


    //***********************************  全局的Handler ************************************************
    private static MyHandler myHandler;


    private static class MyHandler extends Handler {

        private WeakReference<Activity> ref;

        public MyHandler(WeakReference<Activity> ref){
            this.ref = ref;
        }

        @Override
        public void handleMessage(Message msg) {
            ((BaseActivity)ref.get()).handleMessage(msg);
        }
    }


    public static Handler getMyHandler(){
        if(myHandler == null)
            return null;
        return myHandler;
    }

    public void handleMessage(Message msg){}



    //********************************************* 添加Fragment ************************************************************

    private FragmentManager fragmentManager;


    protected void addFragment(Fragment fragment, boolean addToBackStack, int containerId,boolean isAdd) {
        String backStackName = fragment.getClass().getName();
        boolean  fragmentPopped = fragmentManager.popBackStackImmediate(backStackName, 0);
        if (!fragmentPopped) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            if(!isAdd)
                transaction.replace(containerId, fragment, backStackName)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            else
                transaction.add(containerId,fragment,backStackName)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            if (addToBackStack)
                transaction.addToBackStack(backStackName);
            transaction.commit();
        }
    }



    //****************************************  全局的Util  *******************************************************************

    @SuppressWarnings("unchecked")
    public static <T extends View> T get(View view, int id) {
        SparseArray<View> viewHolder = (SparseArray<View>) view.getTag();
        if (viewHolder == null) {
            viewHolder = new SparseArray<View>();
            view.setTag(viewHolder);
        }
        View childView = viewHolder.get(id);
        if (childView == null) {
            childView = view.findViewById(id);
            viewHolder.put(id, childView);
        }
        return (T) childView;
    }

    public int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    public int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }


    /**
     * 获取系统状态栏的高度
     * @param context
     * @return
     */
    public static int getStatusBarHeight(Context context) {
        Class<?> c = null;
        Object obj = null;
        Field field = null;
        int x = 0, statusBarHeight = 0;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            statusBarHeight = context.getResources().getDimensionPixelSize(x);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return statusBarHeight;
    }


    /**
     * 获取屏幕的高度
     * @param wm
     * @return
     */
    public static int getScreenHeight(WindowManager wm) {
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.heightPixels;
    }

    /**
     * 获取屏幕的宽度
     * @param wm
     * @return
     */
    public static int getScreenWidth(WindowManager wm){
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }


    /**
     * 隐藏输入法
     * @param et
     */
    public void hideSoftInput(EditText et){
        ((InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE)).
                hideSoftInputFromWindow(et.getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
    }


    /**
     * 强制显示系统软键盘
     * @param et
     */
    public void showSoftInput(EditText et){
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(et,InputMethodManager.SHOW_FORCED);
    }

    public void showToast(String title){

        final Snackbar snackbar = new Snackbar(this, title, "我知道了", getResources().getInteger(R.integer.carbon_snackbarDuration));
        snackbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snackbar.dismiss();
            }
        });
        snackbar.setStyle(Snackbar.Style.Floating);
        snackbar.show((ViewGroup) getWindow().getDecorView().getRootView());
        snackbar.setOnDismissListener(new Snackbar.OnDismissListener() {
            @Override
            public void onDismiss() {
            }
        });
    }

    //****************************************************************************************************************************


    //********************************************************** 显示dialog *******************************************************************

    public static final int DOWN_LOAD_APPLICATION=0x75;
    /**
     * 是否下载app?
     */
    public void doNewVersionUpdate( final Handler mHandler, UpdateBean updateBean) {

        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Window window = dialog.getWindow();
        window.setContentView(R.layout.app_update_layout);
        window.setBackgroundDrawable(new ColorDrawable(0));
        WindowManager.LayoutParams lp = window.getAttributes();
        window.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
        lp.width = getScreenWidth(getWindowManager()) - dip2px(this,50);
        window.setAttributes(lp);
        dialog.show();

//        ImageView exit = (ImageView)window.findViewById(R.id.app_update_exit);
        Button exit = (Button)window.findViewById(R.id.app_update_exit);
        Button submit = (Button) window.findViewById(R.id.app_update_ok);
        TextView messageContent = (TextView)window.findViewById(R.id.update_content);

        //版本比较
        if(Util.getVerName().equals(updateBean.getVersionIndex())){
            messageContent.setText("已经是最新版本");
            submit.setVisibility(View.GONE);
            exit.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
        }else{
            StringBuffer sb = new StringBuffer();
            sb.append("发现新版本 : "
                    + updateBean.getVersionId() + "\n");
            //			sb.append("更新内容:" + "\n\n");
            String info = updateBean.getUpgradecontent();

            //"优化界面交互  \n 提高用户体验 \n 等其它服务"
//            info = info.substring(1, info.length()-1);
//            info = info.trim();
//            info = info.replace("\\n", "&");
//            String[]  infos  = info.split("&");
//            for(int i=0;i<infos.length;i++){
//                sb.append(i+1).append(".").append(infos[i]).append("\n");
//            }

            messageContent.setText(Html.fromHtml(info));
            submit.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    dialog.dismiss();
                    if(Util.isServiceRunning(UpdateVersionServer.class.getName())){
                        showToast(getString(R.string.downloading));
                    }else{
                        mHandler.obtainMessage(DOWN_LOAD_APPLICATION, true).sendToTarget();
                    }
                }
            });

            exit.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    dialog.dismiss();
                    mHandler.obtainMessage(DOWN_LOAD_APPLICATION,false).sendToTarget();
                }
            });
        }
    }

    //-------------------------------------------------------------------------------------------------------------

    //系统过渡动画-------------------------------------------------------------------
    private  Dialog loadDialog;

    public synchronized void PopLoadDialog(Activity act) {

        if(loadDialog != null){
            if(loadDialog.isShowing())
                return;
        }

        loadDialog = new Dialog(act, R.style.loaddialog);
        Window window = loadDialog.getWindow();
        window.setContentView(R.layout.wait_for_data);
        window.setBackgroundDrawable(new ColorDrawable(0));

        WindowManager.LayoutParams lp = window.getAttributes();
        window.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
        lp.width = getScreenWidth(act.getWindowManager()) - 50;
        window.setAttributes(lp);
        lp.height = getScreenHeight(act.getWindowManager())/4;
        window.setAttributes(lp);

//        ImageView imageView = (ImageView)loadDialog.findViewById(R.id.wait_anim);

        ProgressBar progressBar = (ProgressBar)loadDialog.findViewById(R.id.app_progress);
        progressBar.setVisibility(View.VISIBLE);

//        imageView.setBackgroundResource(R.drawable.wait_data);
//        loadDialog.setOnShowListener(new DialogInterface.OnShowListener() {
//            public void onShow(DialogInterface dialog) {
//                try {
//                    ImageView anIv = (ImageView)loadDialog.findViewById(R.id.wait_anim);
//                    AnimationDrawable ad = (AnimationDrawable)anIv.getBackground();
//                    if(ad != null){
//                        ad.start();
//                    }
//                } catch (Exception e) {
//                    // TODO: handle exception
//                }
//            }
//        });

        loadDialog.setCancelable(true);
        loadDialog.setCanceledOnTouchOutside(true);

        loadDialog.show();
    }


    public synchronized void dismissLoadDialog(){
        if(loadDialog != null){
            loadDialog.cancel();
        }
    }

    public boolean loadDialogisShow(){
        if(loadDialog != null){
            return loadDialog.isShowing();
        }
        return false;
    }



}
