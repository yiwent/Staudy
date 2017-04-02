package com.yiwen.servicepractice;

/**
 * Created by yiwen (https://github.com/yiwent)
 * Date:2017/4/2
 * Time: 12:52
 */

public interface DownloadLisener {
    void onProgess(int progress);
    void onSuccess();
    void onFailed();
    void onPaused();
    void onCanceled();
}
