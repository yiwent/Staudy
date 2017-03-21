package com.yiwen.baseactivity;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/3/20.
 */

public class ActivityCollector {
    public static List<Activity> activityList=new ArrayList<>();
    public static void addActivity(Activity activity){
        activityList.add(activity);
    }
    protected static void remveActivity(Activity activity){
        activityList.remove(activity);
    }
    public static void finishiAll(){
        for (Activity activity:activityList
             ) {
            if(!activity.isFinishing()){
                activity.finish();
            }

        }
    }
}
