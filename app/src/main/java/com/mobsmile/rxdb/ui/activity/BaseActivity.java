package com.mobsmile.rxdb.ui.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.mobsmile.rxdb.R;

/**
 * This file created by barissarikaya on 5/29/17.
 */
public class BaseActivity extends AppCompatActivity {

    public void replaceFragment(Fragment fragment) {
        replaceFragment(fragment, false, true);
    }

    public void replaceFragment(Fragment fragment, boolean addToBackStack, boolean popAllBefore) {
        FragmentManager mFragmentManager = getFragmentManager();
        Fragment currentFragment = mFragmentManager.findFragmentByTag(fragment.getClass().getName());
        if (currentFragment != null && currentFragment.isVisible()) {
            return;
        }

        if (popAllBefore) {
            getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            getFragmentManager().executePendingTransactions();
        }

        FragmentTransaction transaction = mFragmentManager.beginTransaction();

        transaction.replace(R.id.container, fragment, fragment.getClass().getName());
        if (addToBackStack) {
            transaction.addToBackStack(fragment.getClass().getName());
        }
        transaction.commit();
    }

}
