package com.project.omotoso.ubi_learning;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

/**
 * Created by omotoso on 4/11/2016.
 */
public class FragmentTabListener<T extends Fragment> implements ActionBar.TabListener {

    private android.support.v4.app.Fragment mFragment;
    private final FragmentActivity mActivity;
    private final String mTag;
    private final Class<T> mClass;
    private final int mFragmentContainerId;
    private final Bundle mFragmentArgs;

    public FragmentTabListener(FragmentActivity activity, String tag, Class<T> clz) {
        mActivity = activity;
        mTag = tag;
        mClass = clz;
        mFragmentContainerId = android.R.id.content;
        mFragmentArgs =new Bundle();
    }

    public FragmentTabListener(FragmentActivity activity, String tag, Class<T> clz, int fragmentContainerId) {
        this.mActivity = activity;
        this.mTag = tag;
        this.mClass = clz;
        this.mFragmentContainerId = fragmentContainerId;
        mFragmentArgs = new Bundle();
    }
    public FragmentTabListener(FragmentActivity activity, String tag, Class<T> clz, int fragmentContainerId, Bundle args) {
        this.mActivity = activity;
        this.mTag = tag;
        this.mClass = clz;
        this.mFragmentContainerId = fragmentContainerId;
        mFragmentArgs = args;
    }
    @Override
    public void onTabSelected(ActionBar.Tab tab, android.app.FragmentTransaction fragmentTransaction) {
        android.support.v4.app.FragmentTransaction sft = mActivity.getSupportFragmentManager().beginTransaction();
        //check if the fragment is already initialized
        if (mFragment== null){
            //if not instantiate and add it to the activity
            mFragment= android.support.v4.app.Fragment.instantiate(mActivity,mClass.getName(),mFragmentArgs);
            sft.add(mFragmentContainerId,mFragment, mTag);
        }else{
            sft.attach(mFragment);
        }
        sft.commit();
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        android.support.v4.app.FragmentTransaction sft = mActivity.getSupportFragmentManager().beginTransaction();
        if (mFragment != null){

            sft.detach(mFragment);
        }
        sft.commit();
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }
}
