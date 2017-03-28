package com.yiwen.databasetest;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

public class DatabaseProvider extends ContentProvider {
    public static final int BOOK_DIR = 0;
    public static final int BOOK_ITEM = 1;
    public static final int GATEGORY_DIR = 2;
    public static final int GATEGORY_ITEM = 3;
    public static final String AUTHORITY = "com.yiwen.databasetest.provider";
    private static UriMatcher uriMatcher;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, "book", BOOK_DIR);
        uriMatcher.addURI(AUTHORITY, "book/#", BOOK_ITEM);
        uriMatcher.addURI(AUTHORITY, "category", GATEGORY_DIR);
        uriMatcher.addURI(AUTHORITY, "category/#", GATEGORY_ITEM);
    }

    private static MydatabaseHelper dbHelper;

//    public DatabaseProvider() {
//
//    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int deletedRows = 0;
        switch (uriMatcher.match(uri)) {
            case BOOK_DIR:
                deletedRows = db.delete("Book", selection, selectionArgs);
                break;
            case BOOK_ITEM:
                String bookid = uri.getPathSegments().get(1);
                deletedRows = db.delete("Book", "id=?", new String[]{bookid});
                break;
            case GATEGORY_DIR:
                deletedRows = db.delete("Gotegory", selection, selectionArgs);
                break;
            case GATEGORY_ITEM:
                String gategorykid = uri.getPathSegments().get(1);
                deletedRows = db.delete("Botegory", "id=?", new String[]{gategorykid});
                break;
            default:
                break;
        }
        return deletedRows;
    }

    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)) {
            case BOOK_DIR:
                return "vnd.android.cursor.dir/vnd.com.yiwen.databasetest.provider.book";
            case BOOK_ITEM:
                return "vnd.android.cursor.item/vnd.com.yiwen.databasetest.provider.book";
            case GATEGORY_DIR:
                return "vnd.android.cursor.dir/vnd.com.yiwen.databasetest.provider.category";
            case GATEGORY_ITEM:
                return "vnd.android.cursor.item/vnd.com.yiwen.databasetest.provider.category";
            default:
                return null;
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Uri uri1Return = null;
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        switch (uriMatcher.match(uri)) {
            case BOOK_DIR:
            case BOOK_ITEM:
                long newbookid = db.insert("Book", null, values);
                uri1Return = Uri.parse("content://" + AUTHORITY + "/book/" + newbookid);
                break;
            case GATEGORY_DIR:
            case GATEGORY_ITEM:
                long newcategoryid = db.insert("Gategory", null, values);
                uri1Return = Uri.parse("content://" + AUTHORITY + "/category/" + newcategoryid);
                break;
            default:
                break;
        }
        return uri1Return;
    }

    @Override
    public boolean onCreate() {

        dbHelper = new MydatabaseHelper(getContext(), "BookStore.db", null, 2);
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = null;
        switch (uriMatcher.match(uri)) {
            case BOOK_DIR:
                cursor = db.query("Book", projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case BOOK_ITEM:
                String bookid = uri.getPathSegments().get(1);
                cursor = db.query("Book", projection, "id=?", new String[]{bookid}, null, null, sortOrder);
                break;
            case GATEGORY_DIR:
                cursor = db.query("Category", projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case GATEGORY_ITEM:
                String gategoryid = uri.getPathSegments().get(1);
                cursor = db.query("Category", projection, "id=?", new String[]{gategoryid}, null, null, sortOrder);
                break;
            default:
                break;
        }
        return cursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int updatedRows = 0;
        switch (uriMatcher.match(uri)) {
            case BOOK_DIR:
                updatedRows = db.update("Book", values, selection, selectionArgs);
                break;
            case BOOK_ITEM:
                String bookid = uri.getPathSegments().get(1);
                updatedRows = db.update("Book", values, "id=?", new String[]{bookid});
                break;
            case GATEGORY_DIR:
                updatedRows = db.update("Gotegory", values, selection, selectionArgs);
                break;
            case GATEGORY_ITEM:
                String gategorykid = uri.getPathSegments().get(1);
                updatedRows = db.update("Botegory", values, "id=?", new String[]{gategorykid});
                break;
            default:
                break;
        }
        return updatedRows;
    }

}
