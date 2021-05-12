package com.example.testdb;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MySqlhelp extends SQLiteOpenHelper {
    public MySqlhelp(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    //创建staff表的SQL语句
    private String createDatabase = "create table staff(" +
            "id integer primary key autoincrement," +
            "name text," +
            "gender text," +
            "department text," +
            "salary float)";
    @Override
    public void onCreate(SQLiteDatabase db){
        //在创建数据库时执行语句，创建staff表
        db.execSQL(createDatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){

    }
}
