package com.yiwen.fragmenttest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Administrator on 2017/3/22.
 */

public class LeftFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.left_fragment,container,false);
        return  view;
    }
}
