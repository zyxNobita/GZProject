package com.huizai.widget.view.wheelview.view.lib;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import com.huizai.widget.R;
import com.huizai.widget.view.wheelview.view.adapter.WheelAdapter;
import com.huizai.widget.view.wheelview.view.listener.OnItemSelectedListener;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;

/**
 * 3d婊氳疆鎺т欢
 */
public class WheelView extends View {

    public enum ACTION {
        // 鐐瑰嚮锛屾粦缈�(婊戝埌灏藉ご)锛屾嫋鎷戒簨浠�
        CLICK, FLING, DAGGLE
    }
    Context context;

    Handler handler;
    private GestureDetector gestureDetector;
    OnItemSelectedListener onItemSelectedListener;

    // Timer mTimer;
    ScheduledExecutorService mExecutor = Executors.newSingleThreadScheduledExecutor();
    private ScheduledFuture<?> mFuture;

    Paint paintOuterText;
    Paint paintCenterText;
    Paint paintIndicator;

    WheelAdapter adapter;

    private String label;//闄勫姞鍗曚綅
    int textSize;
    int maxTextWidth;
    int maxTextHeight;
    float itemHeight;//姣忚楂樺害

    int textColorOut;
    int textColorCenter;
    int dividerColor;

    // 鏉＄洰闂磋窛鍊嶆暟
    static final float lineSpacingMultiplier = 1.4F;
    boolean isLoop;

    // 绗竴鏉＄嚎Y鍧愭爣鍊�
    float firstLineY;
    //绗簩鏉＄嚎Y鍧愭爣
    float secondLineY;
    //涓棿Y鍧愭爣
    float centerY;

    //婊氬姩鎬婚珮搴鍊�
    int totalScrollY;
    //鍒濆鍖栭粯璁ら�変腑绗嚑涓�
    int initPosition;
    //閫変腑鐨処tem鏄鍑犱釜
    private int selectedItem;
    int preCurrentIndex;
    //婊氬姩鍋忕Щ鍊�,鐢ㄤ簬璁板綍婊氬姩浜嗗灏戜釜item
    int change;

    // 鏄剧ず鍑犱釜鏉＄洰
    int itemsVisible = 11;

    int measuredHeight;
    int measuredWidth;

    // 鍗婂渾鍛ㄩ暱
    int halfCircumference;
    // 鍗婂緞
    int radius;

    private int mOffset = 0;
    private float previousY = 0;
    long startTime = 0;

    // 淇敼杩欎釜鍊煎彲浠ユ敼鍙樻粦琛岄�熷害
    private static final int VELOCITYFLING = 5;
    int widthMeasureSpec;

    private int mGravity = Gravity.CENTER;
    private int drawCenterContentStart = 0;//涓棿閫変腑鏂囧瓧寮�濮嬬粯鍒朵綅缃�
    private int drawOutContentStart = 0;//闈炰腑闂存枃瀛楀紑濮嬬粯鍒朵綅缃�
    private static final float SCALECONTENT = 0.8F;//闈炰腑闂存枃瀛楀垯鐢ㄦ鎺у埗楂樺害锛屽帇鎵佸舰鎴�3d閿欒
    private static final float CENTERCONTENTOFFSET = 6;//涓棿鏂囧瓧鏂囧瓧灞呬腑闇�瑕佹鍋忕Щ鍊�
    private static final String GETPICKERVIEWTEXT = "getPickerViewText";//鍙嶅皠鐨勬柟娉曞悕

    public WheelView(Context context) {
        this(context, null);
    }

    public WheelView(Context context, AttributeSet attrs) {
        super(context, attrs);
        textColorOut = getResources().getColor(R.color.pickerview_wheelview_textcolor_out);
        textColorCenter = getResources().getColor(R.color.pickerview_wheelview_textcolor_center);
        dividerColor = getResources().getColor(R.color.pickerview_wheelview_textcolor_divider);

        if(attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs,R.styleable.wheelview,0,0);
            mGravity = a.getInt(R.styleable.wheelview_gravity, Gravity.CENTER);
            textColorOut = a.getColor(R.styleable.wheelview_textColorOut,textColorOut);
            textColorCenter = a.getColor(R.styleable.wheelview_textColorCenter,textColorCenter);
            dividerColor = a.getColor(R.styleable.wheelview_dividerColor,dividerColor);
        }
        initLoopView(context);
    }

    private void initLoopView(Context context) {
        this.context = context;
        handler = new MessageHandler(this);
        gestureDetector = new GestureDetector(context, new LoopViewGestureListener(this));
        gestureDetector.setIsLongpressEnabled(false);

        isLoop = true;
        textSize = 0;

        totalScrollY = 0;
        initPosition = -1;

        initPaints();

        setTextSize(16F);
    }

    private void initPaints() {
        paintOuterText = new Paint();
        paintOuterText.setColor(textColorOut);
        paintOuterText.setAntiAlias(true);
        paintOuterText.setTypeface(Typeface.MONOSPACE);
        paintOuterText.setTextSize(textSize);

        paintCenterText = new Paint();
        paintCenterText.setColor(textColorCenter);
        paintCenterText.setAntiAlias(true);
        paintCenterText.setTextScaleX(1.1F);
        paintCenterText.setTypeface(Typeface.MONOSPACE);
        paintCenterText.setTextSize(textSize);

        paintIndicator = new Paint();
        paintIndicator.setColor(dividerColor);
        paintIndicator.setAntiAlias(true);

        if (android.os.Build.VERSION.SDK_INT >= 11) {
            setLayerType(LAYER_TYPE_SOFTWARE, null);
        }
    }

    private void remeasure() {
        if (adapter == null) {
            return;
        }

        measureTextWidthHeight();

        //鏈�澶ext鐨勯珮搴︿箻闂磋窛鍊嶆暟寰楀埌 鍙鏂囧瓧瀹為檯鐨勬�婚珮搴︼紝鍗婂渾鐨勫懆闀�
        halfCircumference = (int) (itemHeight * (itemsVisible - 1)) ;
        //鏁翠釜鍦嗙殑鍛ㄩ暱闄や互PI寰楀埌鐩村緞锛岃繖涓洿寰勭敤浣滄帶浠剁殑鎬婚珮搴�
        measuredHeight = (int) ((halfCircumference * 2) / Math.PI);
        //姹傚嚭鍗婂緞
        radius = (int) (halfCircumference / Math.PI);
        //鎺т欢瀹藉害锛岃繖閲屾敮鎸亀eight
        measuredWidth = MeasureSpec.getSize(widthMeasureSpec);
        //璁＄畻涓ゆ潯妯嚎鍜屾帶浠朵腑闂寸偣鐨刌浣嶇疆
        firstLineY = (measuredHeight - itemHeight) / 2.0F;
        secondLineY = (measuredHeight + itemHeight) / 2.0F;
        centerY = (measuredHeight + maxTextHeight) / 2.0F - CENTERCONTENTOFFSET;
        //鍒濆鍖栨樉绀虹殑item鐨刾osition锛屾牴鎹槸鍚oop
        if (initPosition == -1) {
            if (isLoop) {
                initPosition = (adapter.getItemsCount() + 1) / 2;
            } else {
                initPosition = 0;
            }
        }

        preCurrentIndex = initPosition;
    }

    /**
     * 璁＄畻鏈�澶en鐨凾ext鐨勫楂樺害
     */
    private void measureTextWidthHeight() {
        Rect rect = new Rect();
        for (int i = 0; i < adapter.getItemsCount(); i++) {
            String s1 = getContentText(adapter.getItem(i));
            paintCenterText.getTextBounds(s1, 0, s1.length(), rect);
            int textWidth = rect.width();
            if (textWidth > maxTextWidth) {
                maxTextWidth = textWidth;
            }
            paintCenterText.getTextBounds("\u661F\u671F", 0, 2, rect); // 鏄熸湡
            int textHeight = rect.height();
            if (textHeight > maxTextHeight) {
                maxTextHeight = textHeight;
            }
        }
        itemHeight = lineSpacingMultiplier * maxTextHeight;
    }

    void smoothScroll(ACTION action) {
        cancelFuture();
        if (action== ACTION.FLING||action== ACTION.DAGGLE) {
            mOffset = (int) ((totalScrollY%itemHeight + itemHeight) % itemHeight);
            if ((float) mOffset > itemHeight / 2.0F) {
                mOffset = (int) (itemHeight - (float) mOffset);
            } else {
                mOffset = -mOffset;
            }
        }
        //鍋滄鐨勬椂鍊欙紝浣嶇疆鏈夊亸绉伙紝涓嶆槸鍏ㄩ儴閮借兘姝ｇ‘鍋滄鍒颁腑闂翠綅缃殑锛岃繖閲屾妸鏂囧瓧浣嶇疆鎸洖涓棿鍘�
        mFuture = mExecutor.scheduleWithFixedDelay(new SmoothScrollTimerTask(this, mOffset), 0, 10, TimeUnit.MILLISECONDS);
    }

    protected final void scrollBy(float velocityY) {
        cancelFuture();

        mFuture = mExecutor.scheduleWithFixedDelay(new InertiaTimerTask(this, velocityY), 0, VELOCITYFLING, TimeUnit.MILLISECONDS);
    }

    public void cancelFuture() {
        if (mFuture!=null&&!mFuture.isCancelled()) {
            mFuture.cancel(true);
            mFuture = null;
        }
    }

    /**
     * 璁剧疆鏄惁寰幆婊氬姩
     * @param cyclic
     */
    public final void setCyclic(boolean cyclic) {
        isLoop = cyclic;
    }

    public final void setTextSize(float size) {
        if (size > 0.0F) {
            textSize = (int) (context.getResources().getDisplayMetrics().density * size);
            paintOuterText.setTextSize(textSize);
            paintCenterText.setTextSize(textSize);
        }
    }

    public final void setCurrentItem(int currentItem) {
        this.initPosition = currentItem;
        totalScrollY = 0;//鍥炲綊椤堕儴锛屼笉鐒堕噸璁緎etCurrentItem鐨勮瘽浣嶇疆浼氬亸绉荤殑锛屽氨浼氭樉绀哄嚭涓嶅浣嶇疆鐨勬暟鎹�
        invalidate();
    }

    public final void setOnItemSelectedListener(OnItemSelectedListener OnItemSelectedListener) {
        this.onItemSelectedListener = OnItemSelectedListener;
    }

    public final void setAdapter(WheelAdapter adapter) {
        this.adapter = adapter;
        remeasure();
        invalidate();
    }

    public final WheelAdapter getAdapter(){
        return adapter;
    }

    public final int getCurrentItem() {
        return selectedItem;
    }

    protected final void onItemSelected() {
        if (onItemSelectedListener != null) {
            postDelayed(new OnItemSelectedRunnable(this), 200L);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (adapter == null) {
            return;
        }
        //鍙鐨刬tem鏁扮粍
        Object visibles[] = new Object[itemsVisible];
        //婊氬姩鐨刌鍊奸珮搴﹂櫎鍘绘瘡琛孖tem鐨勯珮搴︼紝寰楀埌婊氬姩浜嗗灏戜釜item锛屽嵆change鏁�
        change = (int) (totalScrollY / itemHeight);
        try {
            //婊氬姩涓疄闄呯殑棰勯�変腑鐨刬tem(鍗崇粡杩囦簡涓棿浣嶇疆鐨刬tem) 锛� 婊戝姩鍓嶇殑浣嶇疆 锛� 婊戝姩鐩稿浣嶇疆
            preCurrentIndex = initPosition + change % adapter.getItemsCount();
        }catch (ArithmeticException e){
            System.out.println("鍑洪敊浜嗭紒adapter.getItemsCount() == 0锛岃仈鍔ㄦ暟鎹笉鍖归厤");
        }
        if (!isLoop) {//涓嶅惊鐜殑鎯呭喌
            if (preCurrentIndex < 0) {
                preCurrentIndex = 0;
            }
            if (preCurrentIndex > adapter.getItemsCount() - 1) {
                preCurrentIndex = adapter.getItemsCount() - 1;
            }
        } else {//寰幆
            if (preCurrentIndex < 0) {//涓句釜渚嬪瓙锛氬鏋滄�绘暟鏄�5锛宲reCurrentIndex 锛� 锛�1锛岄偅涔坧reCurrentIndex鎸夊惊鐜潵璇达紝鍏跺疄鏄�0鐨勪笂闈紝涔熷氨鏄�4鐨勪綅缃�
                preCurrentIndex = adapter.getItemsCount() + preCurrentIndex;
            }
            if (preCurrentIndex > adapter.getItemsCount() - 1) {//鍚岀悊涓婇潰,鑷繁鑴戣ˉ涓�涓�
                preCurrentIndex = preCurrentIndex - adapter.getItemsCount();
            }
        }

        //璺熸粴鍔ㄦ祦鐣呭害鏈夊叧锛屾�绘粦鍔ㄨ窛绂讳笌姣忎釜item楂樺害鍙栦綑锛屽嵆骞朵笉鏄竴鏍兼牸鐨勬粴鍔紝姣忎釜item涓嶄竴瀹氭粴鍒板搴擱ect閲岀殑锛岃繖涓猧tem瀵瑰簲鏍煎瓙鐨勫亸绉诲��
        int itemHeightOffset = (int) (totalScrollY % itemHeight);
        // 璁剧疆鏁扮粍涓瘡涓厓绱犵殑鍊�
        int counter = 0;
        while (counter < itemsVisible) {
            int index = preCurrentIndex - (itemsVisible / 2 - counter);//绱㈠紩鍊硷紝鍗冲綋鍓嶅湪鎺т欢涓棿鐨刬tem鐪嬩綔鏁版嵁婧愮殑涓棿锛岃绠楀嚭鐩稿婧愭暟鎹簮鐨刬ndex鍊�

            //鍒ゆ柇鏄惁寰幆锛屽鏋滄槸寰幆鏁版嵁婧愪篃浣跨敤鐩稿寰幆鐨刾osition鑾峰彇瀵瑰簲鐨刬tem鍊硷紝濡傛灉涓嶆槸寰幆鍒欒秴鍑烘暟鎹簮鑼冨洿浣跨敤""绌虹櫧瀛楃涓插～鍏咃紝鍦ㄧ晫闈笂褰㈡垚绌虹櫧鏃犳暟鎹殑item椤�
            if (isLoop) {
                if (index < 0) {
                    index = index + adapter.getItemsCount();
                    if(index < 0){
                        index = 0;
                    }
                }
                if (index > adapter.getItemsCount() - 1) {
                    index = index - adapter.getItemsCount();
                    if (index > adapter.getItemsCount() - 1){
                        index = adapter.getItemsCount() - 1;
                    }
                }
                visibles[counter] = adapter.getItem(index);
            } else if (index < 0) {
                visibles[counter] = "";
            } else if (index > adapter.getItemsCount() - 1) {
                visibles[counter] = "";
            } else {
                visibles[counter] = adapter.getItem(index);
            }
            counter++;

        }

        //涓棿涓ゆ潯妯嚎
        canvas.drawLine(0.0F, firstLineY, measuredWidth, firstLineY, paintIndicator);
        canvas.drawLine(0.0F, secondLineY, measuredWidth, secondLineY, paintIndicator);
        //鍗曚綅鐨凩abel
        if(label != null) {
            int drawRightContentStart = measuredWidth - getTextWidth(paintCenterText,label);
            //闈犲彸骞剁暀鍑虹┖闅�
            canvas.drawText(label, drawRightContentStart - CENTERCONTENTOFFSET, centerY, paintCenterText);
        }
        counter = 0;
        while (counter < itemsVisible) {
            canvas.save();
            // L(寮ч暱)=伪锛堝姬搴︼級* r(鍗婂緞) 锛堝姬搴﹀埗锛�
            // 姹傚姬搴�--> (L * 蟺 ) / (蟺 * r)   (寮ч暱X娲�/鍗婂渾鍛ㄩ暱)
            float itemHeight = maxTextHeight * lineSpacingMultiplier;
            double radian = ((itemHeight * counter - itemHeightOffset) * Math.PI) / halfCircumference;
            // 寮у害杞崲鎴愯搴�(鎶婂崐鍦嗕互Y杞翠负杞村績鍚戝彸杞�90搴︼紝浣垮叾澶勪簬绗竴璞￠檺鍙婄鍥涜薄闄�
            float angle = (float) (90D - (radian / Math.PI) * 180D);
            // 涔濆崄搴︿互涓婄殑涓嶇粯鍒�
            if (angle >= 90F || angle <= -90F) {
                canvas.restore();
            } else {
                String contentText = getContentText(visibles[counter]);

                //璁＄畻寮�濮嬬粯鍒剁殑浣嶇疆
                measuredCenterContentStart(contentText);
                measuredOutContentStart(contentText);
                float translateY = (float) (radius - Math.cos(radian) * radius - (Math.sin(radian) * maxTextHeight) / 2D);
                //鏍规嵁Math.sin(radian)鏉ユ洿鏀筩anvas鍧愭爣绯诲師鐐癸紝鐒跺悗缂╂斁鐢诲竷锛屼娇寰楁枃瀛楅珮搴﹁繘琛岀缉鏀撅紝褰㈡垚寮у舰3d瑙嗚宸�
                canvas.translate(0.0F, translateY);
                canvas.scale(1.0F, (float) Math.sin(radian));
                if (translateY <= firstLineY && maxTextHeight + translateY >= firstLineY) {
                    // 鏉＄洰缁忚繃绗竴鏉＄嚎
                    canvas.save();
                    canvas.clipRect(0, 0, measuredWidth, firstLineY - translateY);
                    canvas.scale(1.0F, (float) Math.sin(radian) * SCALECONTENT);
                    canvas.drawText(contentText, drawOutContentStart, maxTextHeight, paintOuterText);
                    canvas.restore();
                    canvas.save();
                    canvas.clipRect(0, firstLineY - translateY, measuredWidth, (int) (itemHeight));
                    canvas.scale(1.0F, (float) Math.sin(radian) * 1F);
                    canvas.drawText(contentText, drawCenterContentStart, maxTextHeight - CENTERCONTENTOFFSET, paintCenterText);
                    canvas.restore();
                } else if (translateY <= secondLineY && maxTextHeight + translateY >= secondLineY) {
                    // 鏉＄洰缁忚繃绗簩鏉＄嚎
                    canvas.save();
                    canvas.clipRect(0, 0, measuredWidth, secondLineY - translateY);
                    canvas.scale(1.0F, (float) Math.sin(radian) * 1.0F);
                    canvas.drawText(contentText, drawCenterContentStart, maxTextHeight - CENTERCONTENTOFFSET, paintCenterText);
                    canvas.restore();
                    canvas.save();
                    canvas.clipRect(0, secondLineY - translateY, measuredWidth, (int) (itemHeight));
                    canvas.scale(1.0F, (float) Math.sin(radian) * SCALECONTENT);
                    canvas.drawText(contentText, drawOutContentStart, maxTextHeight, paintOuterText);
                    canvas.restore();
                } else if (translateY >= firstLineY && maxTextHeight + translateY <= secondLineY) {
                    // 涓棿鏉＄洰
                    canvas.clipRect(0, 0, measuredWidth, (int) (itemHeight));
                    canvas.drawText(contentText, drawCenterContentStart, maxTextHeight - CENTERCONTENTOFFSET, paintCenterText);
                    int preSelectedItem = adapter.indexOf(visibles[counter]);
                    if(preSelectedItem != -1){
                        selectedItem = preSelectedItem;
                    }
                } else {
                    // 鍏朵粬鏉＄洰
                    canvas.save();
                    canvas.clipRect(0, 0, measuredWidth, (int) (itemHeight));
                    canvas.scale(1.0F, (float) Math.sin(radian) * SCALECONTENT);
                    canvas.drawText(contentText, drawOutContentStart, maxTextHeight, paintOuterText);
                    canvas.restore();
                }
                canvas.restore();
            }
            counter++;
        }
    }

    /**
     * 鏍规嵁浼犺繘鏉ョ殑瀵硅薄鍙嶅皠鍑篻etPickerViewText()鏂规硶锛屾潵鑾峰彇闇�瑕佹樉绀虹殑鍊�
     * @param item
     * @return
     */
    private String getContentText(Object item) {
        String contentText = item.toString();
        try {
            Class<?> clz = item.getClass();
            Method m = clz.getMethod(GETPICKERVIEWTEXT);
            contentText = m.invoke(item, new Object[0]).toString();
        } catch (NoSuchMethodException e) {
        } catch (InvocationTargetException e) {
        } catch (IllegalAccessException e) {
        } catch (Exception e){
        }
        return contentText;
    }

    private void measuredCenterContentStart(String content) {
        Rect rect = new Rect();
        paintCenterText.getTextBounds(content, 0, content.length(), rect);
        switch (mGravity){
            case Gravity.CENTER:
                drawCenterContentStart = (int)((measuredWidth - rect.width()) * 0.5);
                break;
            case Gravity.LEFT:
                drawCenterContentStart = 0;
                break;
            case Gravity.RIGHT:
                drawCenterContentStart = measuredWidth - rect.width();
                break;
        }
    }
    private void measuredOutContentStart(String content) {
        Rect rect = new Rect();
        paintOuterText.getTextBounds(content, 0, content.length(), rect);
        switch (mGravity){
            case Gravity.CENTER:
                drawOutContentStart = (int)((measuredWidth - rect.width()) * 0.5);
                break;
            case Gravity.LEFT:
                drawOutContentStart = 0;
                break;
            case Gravity.RIGHT:
                drawOutContentStart = measuredWidth - rect.width();
                break;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        this.widthMeasureSpec = widthMeasureSpec;
        remeasure();
        setMeasuredDimension(measuredWidth, measuredHeight);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean eventConsumed = gestureDetector.onTouchEvent(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startTime = System.currentTimeMillis();
                cancelFuture();
                previousY = event.getRawY();
                break;

            case MotionEvent.ACTION_MOVE:
                float dy = previousY - event.getRawY();
                previousY = event.getRawY();
                totalScrollY = (int) (totalScrollY + dy);

                // 杈圭晫澶勭悊銆�
                if (!isLoop) {
                    float top = -initPosition * itemHeight;
                    float bottom = (adapter.getItemsCount() - 1 - initPosition) * itemHeight;
                    if(totalScrollY - itemHeight*0.3 < top){
                        top = totalScrollY - dy;
                    }
                    else if(totalScrollY + itemHeight*0.3 > bottom){
                        bottom = totalScrollY - dy;
                    }

                    if (totalScrollY < top) {
                        totalScrollY = (int) top;
                    } else if (totalScrollY > bottom) {
                        totalScrollY = (int) bottom;
                    }
                }
                break;

            case MotionEvent.ACTION_UP:
            default:
                if (!eventConsumed) {
                    float y = event.getY();
                    double l = Math.acos((radius - y) / radius) * radius;
                    int circlePosition = (int) ((l + itemHeight / 2) / itemHeight);

                    float extraOffset = (totalScrollY % itemHeight + itemHeight) % itemHeight;
                    mOffset = (int) ((circlePosition - itemsVisible / 2) * itemHeight - extraOffset);

                    if ((System.currentTimeMillis() - startTime) > 120) {
                        // 澶勭悊鎷栨嫿浜嬩欢
                        smoothScroll(ACTION.DAGGLE);
                    } else {
                        // 澶勭悊鏉＄洰鐐瑰嚮浜嬩欢
                        smoothScroll(ACTION.CLICK);
                    }
                }
                break;
        }
        invalidate();

        return true;
    }

    /**
     * 鑾峰彇Item涓暟
     * @return
     */
    public int getItemsCount() {
        return adapter != null ? adapter.getItemsCount() : 0;
    }

    /**
     * 闄勫姞鍦ㄥ彸杈圭殑鍗曚綅瀛楃涓�
     * @param label
     */
    public void setLabel(String label){
        this.label = label;
    }

    public void setGravity(int gravity) {
        this.mGravity = gravity;
    }

    public int getTextWidth(Paint paint, String str) {
        int iRet = 0;
        if (str != null && str.length() > 0) {
            int len = str.length();
            float[] widths = new float[len];
            paint.getTextWidths(str, widths);
            for (int j = 0; j < len; j++) {
                iRet += (int) Math.ceil(widths[j]);
            }
        }
        return iRet;
    }
}