package com.yiwen.servicepractice;

import android.os.AsyncTask;
import android.os.Environment;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * User: Yiwen(https://github.com/yiwent)
 * Date: 2017-04-02
 * Time: 12:54
 * FIXME
 */
public class DownloadTask extends AsyncTask<String, Integer, Integer> {
    public static final int TYPE_SUCCESS  = 1;
    public static final int TYPE_FAILED   = 2;
    public static final int TYPE_PAUSED   = 3;
    public static final int TYPE_CANCELED = 4;

    private DownloadLisener mLisener;
    private boolean isCanceled = false;
    private boolean isPaused   = false;
    private int lastProgress;

    public DownloadTask(DownloadLisener lisener) {
        this.mLisener = lisener;
    }

    @Override
    protected Integer doInBackground(String... params) {
        InputStream is = null;
        RandomAccessFile saveFile = null;
        File file = null;
        try {
            long downloadlength = 0;
            String dowmloadUrl = params[0];
            String fileName = dowmloadUrl.substring(dowmloadUrl.lastIndexOf("/"));
            String directory = Environment.
                    getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath();
            file = new File(directory + fileName);
            if (file.exists()) {
                downloadlength = file.length();
            }
            long contentlongth = getCententLength(dowmloadUrl);
            if (contentlongth == 0) {
                return TYPE_FAILED;
            } else if (contentlongth == downloadlength) {
                return TYPE_SUCCESS;
            }
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .addHeader("RANGE", "bytes" + downloadlength + "-")
                    .url(dowmloadUrl)
                    .build();
            Response response = client.newCall(request).execute();
            if (response != null) {
                is = response.body().byteStream();
                saveFile = new RandomAccessFile(file, "rw");
                saveFile.seek(downloadlength);
                byte[] b = new byte[1024];
                int total = 0;
                int len;
                while ((len = is.read(b)) != -1) {
                    if (isCanceled) {
                        return TYPE_CANCELED;
                    } else if (isPaused) {
                        return TYPE_PAUSED;
                    } else {
                        total += len;
                        saveFile.write(b, 0, len);

                        int progress = (int) ((total + downloadlength) * 100 / contentlongth);
                        publishProgress(progress);
                    }
                }
                response.body().close();
                return TYPE_SUCCESS;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
                if (saveFile != null) {
                    saveFile.close();
                }
                if (isCanceled && file != null) {
                    file.delete();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return TYPE_FAILED;
    }

    private long getCententLength(String dowmloadUrl) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request reques = new Request.Builder()
                .url(dowmloadUrl)
                .build();
        Response response = client.newCall(reques).execute();
        if (response != null && response.isSuccessful()) {
            long contentlength = response.body().contentLength();
            response.body().close();
            return contentlength;
        }
        return 0;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        int progress = values[0];
        if (progress > lastProgress) {
            mLisener.onProgess(progress);
            lastProgress = progress;
        }
    }

    @Override
    protected void onPostExecute(Integer integer) {
        switch (integer) {
            case TYPE_SUCCESS:
                mLisener.onSuccess();
                break;
            case TYPE_FAILED:
                mLisener.onFailed();
                break;
            case TYPE_PAUSED:
                mLisener.onPaused();
                break;
            case TYPE_CANCELED:
                mLisener.onCanceled();
                break;
            default:
                break;
        }
    }

    public void pauseDownload() {
        isPaused = true;
    }

    public void cancleDownload() {
        isCanceled = true;
    }

}
