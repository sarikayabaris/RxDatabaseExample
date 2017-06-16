package com.mobsmile.rxdb.ui.activity;

import android.app.Fragment;
import android.os.Bundle;

import com.mobsmile.rxdb.R;
import com.mobsmile.rxdb.ui.fragment.DBListFragment;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        if (null == savedInstanceState) {
            Fragment fragment = getFragmentManager().findFragmentByTag(DBListFragment.class.getName());
            if (null == fragment)
                fragment = new DBListFragment();
            replaceFragment(fragment);
        }
    }
}

