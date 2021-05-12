package com.example.dbresolver;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class InsertActivity extends AppCompatActivity{
    String staffId = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);
        //插入信息按钮
        Button insert = findViewById(R.id.insertInfo);
        insert.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Uri uri = Uri.parse("content://com.example.testdb.provider/staff");
                EditText name = findViewById(R.id.name);
                EditText gender = findViewById(R.id.gender);
                EditText department = findViewById(R.id.department);
                EditText salary = findViewById(R.id.salary);
                ContentValues values = new ContentValues();
                values.put("name", name.getText().toString());
                values.put("gender", gender.getText().toString());
                values.put("department", department.getText().toString());
                values.put("salary", salary.getText().toString());
                Uri newUri = getContentResolver().insert(uri, values);
                staffId = newUri.getPathSegments().get(1);
                Toast.makeText(InsertActivity.this, "添加信息成功", Toast.LENGTH_SHORT).show();
            }
        });
        //返回菜单
        Button back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(InsertActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
