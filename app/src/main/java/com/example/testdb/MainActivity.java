package com.example.testdb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private SQLiteDatabase testDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //获取整个activity的id
        LinearLayout linearLayout = findViewById(R.id.ll);
        //sharedPreference获取
        SharedPreferences prefs = getSharedPreferences("data", Context.MODE_PRIVATE);
        //获取背景色，默认白色,并加载到布局中
        String back_color = prefs.getString("back_color","#ffffff");
        linearLayout.setBackgroundColor(Color.parseColor(back_color));
        //获取字体色、字体大小，默认黑色,15
        TextView text = findViewById(R.id.text);
        String text_color = prefs.getString("text_color","#000000");
        text.setTextColor(Color.parseColor(text_color));
        float text_size = prefs.getFloat("text_size",15);
        text.setTextSize(text_size);
        //创建数据库
        MySqlhelp mySqlhelp = new MySqlhelp(this, "test.db", null, 1);
        //数据库创建或打开时调用
        testDatabase = mySqlhelp.getWritableDatabase();
        //显示信息按钮
        Button query = findViewById(R.id.query);
        query.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String val = "";
                Cursor cursor = testDatabase.query("staff", null, null, null, null, null, null);
                if(cursor.moveToFirst()){
                    do{
                        int id = cursor.getInt(cursor.getColumnIndex("id"));
                        String name = cursor.getString(cursor.getColumnIndex("name"));
                        String gender = cursor.getString(cursor.getColumnIndex("gender"));
                        String department = cursor.getString(cursor.getColumnIndex("department"));
                        float salary = cursor.getFloat(cursor.getColumnIndex("salary"));
                        val += "\t " + id + "\t\t\t\t " + name + "\t\t\t\t " + gender + "\t\t\t\t " + department + "\t\t\t\t " + salary + "\n";
                    }while(cursor.moveToNext());
                }
                cursor.close();
                text.setText(val);
            }
        });
        //设置背景色等信息
        Button settings = findViewById(R.id.setting);
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,SettingsActivity.class);
                startActivity(intent);
            }
        });
    }

}
