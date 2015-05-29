package com.wanglei.mylover.utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

public class FragmentManager {

	public static void replaceFragment(FragmentActivity mFragmentActivity, int container, Fragment fragment, boolean addToBackStack) {
		
		FragmentTransaction fragmentTransaction = mFragmentActivity.getSupportFragmentManager().beginTransaction();
		fragmentTransaction.replace(container, fragment);
		if (addToBackStack) {
			fragmentTransaction.addToBackStack(null);
		}
		fragmentTransaction.commit();

	}

}
