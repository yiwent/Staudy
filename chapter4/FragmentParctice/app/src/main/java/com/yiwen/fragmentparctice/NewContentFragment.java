package com.yiwen.fragmentparctice;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Administrator on 2017/3/23.
 */

public class NewContentFragment extends Fragment {

    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.new_content_frag, container, false);
        return view;
    }
    public void refresh(String title,String content){
        View viesibility_layout=view.findViewById(R.id.visibility_layout);
        viesibility_layout.setVisibility(View.VISIBLE);
        TextView textView_title= (TextView) view.findViewById(R.id.new_title);
        TextView textView_content= (TextView) view.findViewById(R.id.new_content);
        textView_content.setText(content);
        textView_title.setText(title);
    }

}
