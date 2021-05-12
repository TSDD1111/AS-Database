package com.example.dbresolver;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class UpdateActivity extends AppCompatActivity {
    String staffId = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        //更新信息按钮
        Button update = findViewById(R.id.updateInfo);
        update.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                EditText id = findViewById(R.id.id);
                staffId = id.getText().toString();
                Uri uri = Uri.parse("content://com.example.testdb.provider/staff/" + staffId);
                //获取更新的数据
                EditText name = findViewById(R.id.name);
                EditText gender = findViewById(R.id.gender);
                EditText department = findViewById(R.id.department);
                EditText salary = findViewById(R.id.salary);
                ContentValues values = new ContentValues();
                //若为空字符串，则保持原来信息
                if(!name.getText().toString().equals("")){
                    values.put("name", name.getText().toString());
                }
                if(!gender.getText().toString().equals("")){
                    values.put("gender", gender.getText().toString());
                }
                if(!department.getText().toString().equals("")){
                    values.put("department", department.getText().toString());
                }
                if(!salary.getText().toString().equals("")){
                    values.put("salary", salary.getText().toString());
                }
                getContentResolver().update(uri, values, null, null);
                Toast.makeText(UpdateActivity.this, "更新信息成功", Toast.LENGTH_SHORT).show();
            }
        });
        //返回菜单
        Button back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(UpdateActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
