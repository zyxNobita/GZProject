package com.huizai.widget.view.wheelview.view.view;

import android.content.Context;
import android.view.View;

import com.huizai.widget.R;
import com.huizai.widget.view.wheelview.view.TimePickerView.Type;
import com.huizai.widget.view.wheelview.view.adapter.NumericWheelAdapter;
import com.huizai.widget.view.wheelview.view.lib.WheelView;
import com.huizai.widget.view.wheelview.view.listener.OnItemSelectedListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;


public class WheelTime {
	public static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	private View view;
	private WheelView wv_year;
	private WheelView wv_month;
	private WheelView wv_day;
	private WheelView wv_hours;
	private WheelView wv_mins;

	private Type type;
	public static final int DEFULT_START_YEAR = 1900;
	public static final int DEFULT_END_YEAR = 2100;
	private int startYear = DEFULT_START_YEAR;
	private int endYear = DEFULT_END_YEAR;



	public WheelTime(View view) {
		super();
		this.view = view;
		type = Type.ALL;
		setView(view);
	}
	public WheelTime(View view,Type type) {
		super();
		this.view = view;
		this.type = type;
		setView(view);
	}
	public void setPicker(int year ,int month,int day){
		this.setPicker(year, month, day, 0, 0);
	}
	
	/**
	 * @Description: TODO 寮瑰嚭鏃ユ湡鏃堕棿閫夋嫨鍣�
	 */
	public void setPicker(int year ,int month ,int day,int h,int m) {
		// 娣诲姞澶у皬鏈堟湀浠藉苟灏嗗叾杞崲涓簂ist,鏂逛究涔嬪悗鐨勫垽鏂�
		String[] months_big = { "1", "3", "5", "7", "8", "10", "12" };
		String[] months_little = { "4", "6", "9", "11" };

		final List<String> list_big = Arrays.asList(months_big);
		final List<String> list_little = Arrays.asList(months_little);

		Context context = view.getContext();
		// 骞�
		wv_year = (WheelView) view.findViewById(R.id.year);
		wv_year.setAdapter(new NumericWheelAdapter(startYear, endYear));// 璁剧疆"骞�"鐨勬樉绀烘暟鎹�
		wv_year.setLabel(context.getString(R.string.pickerview_year));// 娣诲姞鏂囧瓧
		wv_year.setCurrentItem(year - startYear);// 鍒濆鍖栨椂鏄剧ず鐨勬暟鎹�

		// 鏈�
		wv_month = (WheelView) view.findViewById(R.id.month);
		wv_month.setAdapter(new NumericWheelAdapter(1, 12));
		wv_month.setLabel(context.getString(R.string.pickerview_month));
		wv_month.setCurrentItem(month);

		// 鏃�
		wv_day = (WheelView) view.findViewById(R.id.day);
		// 鍒ゆ柇澶у皬鏈堝強鏄惁闂板勾,鐢ㄦ潵纭畾"鏃�"鐨勬暟鎹�
		if (list_big.contains(String.valueOf(month + 1))) {
			wv_day.setAdapter(new NumericWheelAdapter(1, 31));
		} else if (list_little.contains(String.valueOf(month + 1))) {
			wv_day.setAdapter(new NumericWheelAdapter(1, 30));
		} else {
			// 闂板勾
			if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0)
				wv_day.setAdapter(new NumericWheelAdapter(1, 29));
			else
				wv_day.setAdapter(new NumericWheelAdapter(1, 28));
		}
		wv_day.setLabel(context.getString(R.string.pickerview_day));
		wv_day.setCurrentItem(day - 1);


        wv_hours = (WheelView)view.findViewById(R.id.hour);
		wv_hours.setAdapter(new NumericWheelAdapter(0, 23));
		wv_hours.setLabel(context.getString(R.string.pickerview_hours));// 娣诲姞鏂囧瓧
		wv_hours.setCurrentItem(h);

		wv_mins = (WheelView)view.findViewById(R.id.min);
		wv_mins.setAdapter(new NumericWheelAdapter(0, 59));
		wv_mins.setLabel(context.getString(R.string.pickerview_minutes));// 娣诲姞鏂囧瓧
		wv_mins.setCurrentItem(m);

		// 娣诲姞"骞�"鐩戝惉
		OnItemSelectedListener wheelListener_year = new OnItemSelectedListener() {
			@Override
			public void onItemSelected(int index) {
				int year_num = index + startYear;
				// 鍒ゆ柇澶у皬鏈堝強鏄惁闂板勾,鐢ㄦ潵纭畾"鏃�"鐨勬暟鎹�
				int maxItem = 30;
				if (list_big
						.contains(String.valueOf(wv_month.getCurrentItem() + 1))) {
					wv_day.setAdapter(new NumericWheelAdapter(1, 31));
					maxItem = 31;
				} else if (list_little.contains(String.valueOf(wv_month
						.getCurrentItem() + 1))) {
					wv_day.setAdapter(new NumericWheelAdapter(1, 30));
					maxItem = 30;
				} else {
					if ((year_num % 4 == 0 && year_num % 100 != 0)
							|| year_num % 400 == 0){
						wv_day.setAdapter(new NumericWheelAdapter(1, 29));
						maxItem = 29;
					}
					else{
						wv_day.setAdapter(new NumericWheelAdapter(1, 28));
						maxItem = 28;
					}
				}
				if (wv_day.getCurrentItem() > maxItem - 1){
					wv_day.setCurrentItem(maxItem - 1);
				}
			}
		};
		// 娣诲姞"鏈�"鐩戝惉
		OnItemSelectedListener wheelListener_month = new OnItemSelectedListener() {
			@Override
			public void onItemSelected(int index) {
				int month_num = index + 1;
				int maxItem = 30;
				// 鍒ゆ柇澶у皬鏈堝強鏄惁闂板勾,鐢ㄦ潵纭畾"鏃�"鐨勬暟鎹�
				if (list_big.contains(String.valueOf(month_num))) {
					wv_day.setAdapter(new NumericWheelAdapter(1, 31));
					maxItem = 31;
				} else if (list_little.contains(String.valueOf(month_num))) {
					wv_day.setAdapter(new NumericWheelAdapter(1, 30));
					maxItem = 30;
				} else {
					if (((wv_year.getCurrentItem() + startYear) % 4 == 0 && (wv_year
							.getCurrentItem() + startYear) % 100 != 0)
							|| (wv_year.getCurrentItem() + startYear) % 400 == 0){
						wv_day.setAdapter(new NumericWheelAdapter(1, 29));
						maxItem = 29;
					}
					else{
						wv_day.setAdapter(new NumericWheelAdapter(1, 28));
						maxItem = 28;
					}
				}
				if (wv_day.getCurrentItem() > maxItem - 1){
					wv_day.setCurrentItem(maxItem - 1);
				}

			}
		};
		wv_year.setOnItemSelectedListener(wheelListener_year);
		wv_month.setOnItemSelectedListener(wheelListener_month);

		// 鏍规嵁灞忓箷瀵嗗害鏉ユ寚瀹氶�夋嫨鍣ㄥ瓧浣撶殑澶у皬(涓嶅悓灞忓箷鍙兘涓嶅悓)
		int textSize = 6;
		switch(type){
		case ALL:
			textSize = textSize * 3;
			break;
		case YEAR_MONTH_DAY:
			textSize = textSize * 4;
			wv_hours.setVisibility(View.GONE);
			wv_mins.setVisibility(View.GONE);
			break;
		case HOURS_MINS:
			textSize = textSize * 4;
			wv_year.setVisibility(View.GONE);
			wv_month.setVisibility(View.GONE);
			wv_day.setVisibility(View.GONE);
			break;
		case MONTH_DAY_HOUR_MIN:
			textSize = textSize * 3;
			wv_year.setVisibility(View.GONE);
			break;
        case YEAR_MONTH:
            textSize = textSize * 4;
            wv_day.setVisibility(View.GONE);
            wv_hours.setVisibility(View.GONE);
            wv_mins.setVisibility(View.GONE);
		}
		wv_day.setTextSize(textSize);
		wv_month.setTextSize(textSize);
		wv_year.setTextSize(textSize);
		wv_hours.setTextSize(textSize);
		wv_mins.setTextSize(textSize);

	}

	/**
	 * 璁剧疆鏄惁寰幆婊氬姩
	 * @param cyclic
	 */
	public void setCyclic(boolean cyclic){
		wv_year.setCyclic(cyclic);
		wv_month.setCyclic(cyclic);
		wv_day.setCyclic(cyclic);
		wv_hours.setCyclic(cyclic);
		wv_mins.setCyclic(cyclic);
	}
	public String getTime() {
		StringBuffer sb = new StringBuffer();
			sb.append((wv_year.getCurrentItem() + startYear)).append("-")
			.append((wv_month.getCurrentItem() + 1)).append("-")
			.append((wv_day.getCurrentItem() + 1)).append(" ")
			.append(wv_hours.getCurrentItem()).append(":")
			.append(wv_mins.getCurrentItem());
		return sb.toString();
	}

	public View getView() {
		return view;
	}

	public void setView(View view) {
		this.view = view;
	}

	public int getStartYear() {
		return startYear;
	}

	public void setStartYear(int startYear) {
		this.startYear = startYear;
	}

	public int getEndYear() {
		return endYear;
	}

	public void setEndYear(int endYear) {
		this.endYear = endYear;
	}
}
