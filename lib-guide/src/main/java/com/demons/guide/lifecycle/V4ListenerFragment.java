package com.demons.guide.lifecycle;

import android.support.v4.app.Fragment;


public class V4ListenerFragment extends Fragment {

    FragmentLifecycle mFragmentLifecycle;

    public void setFragmentLifecycle(FragmentLifecycle lifecycle) {
        mFragmentLifecycle = lifecycle;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mFragmentLifecycle != null) mFragmentLifecycle.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mFragmentLifecycle != null) mFragmentLifecycle.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mFragmentLifecycle != null) mFragmentLifecycle.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mFragmentLifecycle != null) mFragmentLifecycle.onDestroy();
    }
}
