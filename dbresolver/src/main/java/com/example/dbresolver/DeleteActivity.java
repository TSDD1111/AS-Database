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

public class DeleteActivity extends AppCompatActivity {
    String staffId = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);
        //删除信息按钮
        Button delete = findViewById(R.id.deleteInfo);
        delete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                EditText id = findViewById(R.id.id);
                staffId = id.getText().toString();
                Uri uri = Uri.parse("content://com.example.testdb.provider/staff/" + staffId);
                getContentResolver().delete(uri, null, null);
                Toast.makeText(DeleteActivity.this, "删除信息成功", Toast.LENGTH_SHORT).show();
            }
        });
        //返回菜单
        Button back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(DeleteActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
