package com.hr.nipuream.gz.controller.other.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import java.util.List;

/**
 * 描述：
 * 作者：Nipuream
 * 时间: 2016-07-22 19:41
 * 邮箱：571829491@qq.com
 */
public class FirstPageAdapter extends FragmentPagerAdapter{

    private List<Fragment> fragments;

    public FirstPageAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }


    @Override
    public Fragment getItem(int position) {
        if(fragments != null)
            return fragments.get(position);
        else
            return null;
    }

    @Override
    public int getCount() {
        if(fragments !=null)
            return fragments.size();
        else
            return 0;
    }

}
