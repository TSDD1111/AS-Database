package com.example.testdb;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

public class MyProvider extends ContentProvider {
    private static final String authority = "com.example.testdb.provider";
    private static final int staffDir = 1;
    private static final int staffItem = 2;
    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    private MySqlhelp mySqlhelp;

    //注册向外部程序提供的Uri
    static {
        uriMatcher.addURI(authority,"staff",staffDir);
        uriMatcher.addURI(authority,"staff/#",staffItem);
    }
    @Override
    public boolean onCreate(){
        //创建或打开数据库
        mySqlhelp = new MySqlhelp(getContext(), "test.db", null, 1);
        return true;
    }
    //重写查询方法
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db = mySqlhelp.getReadableDatabase();
        Cursor cursor = db.query("staff",projection,selection,selectionArgs,null,null,sortOrder);
        return cursor;
    }
    //返回对应的类型，是一行还是一整个表
    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match((uri))){
            case staffDir:return "vnd.android.cursor.dir/vnd.com.example.testdb.provider.staff";
            case staffItem: return "vnd.android.cursor.item/vnd.com.example.testdb.provider.staff";
            default:return null;
        }
    }
    //重写插入
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase db = mySqlhelp.getWritableDatabase();    //获取数据库
        long newStaffId = db.insert("staff", null, values);  //执行对应的sql语句
        return Uri.parse("content://" + authority + "/staff/" + newStaffId);
    }
    //重写删除
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = mySqlhelp.getWritableDatabase();        //获取数据库
        String staffId = uri.getPathSegments().get(1);              //获取对应数据的id
        db.delete("staff","id=?",new String[] {staffId});       //执行删除语句
        return 1;
    }
    //重写更新
    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        SQLiteDatabase db = mySqlhelp.getWritableDatabase();        //获取数据库
        String staffId = uri.getPathSegments().get(1);              //获取对应数据的id
        db.update("staff",values,"id=?", new String[] {staffId});   //更新数据
        return 1;
    }

}
