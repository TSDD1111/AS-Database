package com.example.testdb;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        //保存信息
        Button save = findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取输入的信息
                EditText back_color = findViewById(R.id.back_color);
                EditText text_color = findViewById(R.id.text_color);
                EditText text_size = findViewById(R.id.text_size);
                SharedPreferences.Editor eidtor = getSharedPreferences("data", Context.MODE_PRIVATE).edit();
                //将输入的配置信息保存下来，同时判断是否为16进制颜色和是否为浮点数
                if(!back_color.getText().toString().equals("") && back_color.getText().toString().matches("^#([0-9a-fA-F]{6}|[0-9a-fA-F]{3})$")){
                    eidtor.putString("back_color", back_color.getText().toString());
                }
                if(!text_color.getText().toString().equals("") && text_color.getText().toString().matches("^#([0-9a-fA-F]{6}|[0-9a-fA-F]{3})$")){
                    eidtor.putString("text_color", text_color.getText().toString());
                }
                if(!text_size.getText().toString().equals("") && text_size.getText().toString().matches("^\\d+(\\.\\d+)?$")){
                    eidtor.putFloat("text_size", Float.parseFloat(text_size.getText().toString()));
                }
                eidtor.apply();
                Toast.makeText(SettingsActivity.this,"保存成功",Toast.LENGTH_SHORT).show();
            }
        });
        //返回主菜单
        Button back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
