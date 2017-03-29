package com.yiwen.cameratest;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;

public class MainActivity extends AppCompatActivity {
    private ImageView imageView;
    private Uri imageUrl;
    public static final int TAKE_PHOTO = 1;
    public static final int CHOOSE_PHOTO = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button takephoto = (Button) findViewById(R.id.bt_take_photo);
        imageView = (ImageView) findViewById(R.id.imageView);
        Button chose = (Button) findViewById(R.id.bt_choose_fromalbum);
        takephoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File outputImage = new File(getExternalCacheDir(), "output_image.jpg");
                try {
                    if (outputImage.exists()) {
                        outputImage.delete();
                    }
                    outputImage.createNewFile();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (Build.VERSION.SDK_INT >= 24) {
                    imageUrl = FileProvider.getUriForFile(MainActivity.this,
                            "com.yiwen.cameratest.fileprovider", outputImage);
//                    ContentValues contentValues = new ContentValues(1);
//                    contentValues.put(MediaStore.Images.Media.DATA, outputImage.getAbsolutePath());
//                    imageUrl =getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,contentValues);
                    Log.d("imageUrl", "onClick: " + imageUrl);
                } else {
                    imageUrl = Uri.fromFile(outputImage);
                }
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUrl);
                startActivityForResult(intent, TAKE_PHOTO);
            }
        });
        chose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                } else {
                    openAlum();
                }
            }
        });

    }

    private void openAlum() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent, CHOOSE_PHOTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
                    try {
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUrl));
                        imageView.setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case CHOOSE_PHOTO:
                if (resultCode == RESULT_OK) {
                    if (Build.VERSION.SDK_INT >= 19) {
                        handleImageOnkitkat(data);
                    } else {
                        handleImageBeforekitkat(data);
                    }
                }
                break;
            default:
                break;
        }
    }

    private void handleImageBeforekitkat(Intent data) {
        Uri url = data.getData();
        String imagePath = getImgePath(url, null);
        displayImage(imagePath);
    }

    private void displayImage(String imagePath) {
        if (imagePath != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            imageView.setImageBitmap(bitmap);
        } else {
            Toast.makeText(MainActivity.this, "failed to get image", Toast.LENGTH_SHORT).show();
            ;
        }
    }

    private String getImgePath(Uri url, String selection) {
        String path = null;
        //通过url和path获取
        Cursor cursor = getContentResolver().query(url, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToNext()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    @TargetApi(19)
    private void handleImageOnkitkat(Intent data) {
        String imagePath = null;
        Uri url = data.getData();
        if (DocumentsContract.isDocumentUri(this, url)) {
            //Document类Uri，通过Document id处理
            String docid = DocumentsContract.getDocumentId(url);
            if ("com.android.providers.media.documents".equals(url.getAuthority())) {
                String id = docid.split(":")[1];
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImgePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
                Log.d("imagePath", "handleImageOnkitkat:1 " + imagePath);
            } else if ("com.android.providers.downloads.documents".equals(url.getAuthority())) {
                Uri contentUrl = ContentUris.withAppendedId(Uri.parse("content://downloads/puplic_downloads"), Long.valueOf(docid));
                imagePath = getImgePath(contentUrl, null);
                Log.d("imagePath", "handleImageOnkitkat:2 " + "imagePath");
            }
        } else if ("content".equalsIgnoreCase(url.getScheme())) {
            //content类Uri，普通方式处理
            imagePath = getImgePath(url, null);
            Log.d("imagePath", "handleImageOnkitkat:3 " + "imagePath");
        } else if ("file".equalsIgnoreCase(url.getScheme())) {
            //file类Uri，直接获取
            imagePath = url.getPath();
            Log.d("imagePath", "handleImageOnkitkat:4 " + "imagePath");
        }

        displayImage(imagePath);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openAlum();
                } else {
                    Toast.makeText(MainActivity.this, "you denied the peimission", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }
}
