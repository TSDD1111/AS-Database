package com.example.dbresolver;

import android.app.Service;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.IBinder;
import android.os.Looper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Date;

public class MyService extends Service {
    private boolean isThreadStop = false;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate(){
        super.onCreate();
        Toast.makeText(MyService.this, "监听服务已启动", Toast.LENGTH_SHORT).show();
    }

    @Override
    public int onStartCommand(Intent intent,int flags, int startId){
        myThread thread = new myThread();
        thread.start();
        return super.onStartCommand(intent,flags,startId);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        isThreadStop = true;
    }

    public class myThread extends Thread{
        @Override
        public void run(){
            while(true){
                try {
                    Uri uri = Uri.parse("content://com.example.testdb.provider/staff");
                    Cursor cursor = getContentResolver().query(uri,null,null,null,null);
                    if(cursor.moveToFirst()){
                        do{
                            int id = cursor.getInt(cursor.getColumnIndex("id"));
                            float salary = cursor.getFloat(cursor.getColumnIndex("salary"));
                            if(salary < 0){
                                //读取check.log文件，并将置零的id存入文件
                                FileOutputStream output = openFileOutput("check.log", Context.MODE_APPEND);
                                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(output));
                                Date date = new Date();
                                writer.write(date.toString() + " id:" + id + " reset salary 0.\n");
                                writer.close();
                                //对于salary为负数的项，更新为0
                                uri = Uri.parse("content://com.example.testdb.provider/staff/" + id);
                                ContentValues values = new ContentValues();
                                values.put("salary", 0.0);
                                getContentResolver().update(uri, values, null, null);
                            }
                        } while(cursor.moveToNext());
                    }
                    cursor.close();
                    sleep(10000);
                    if(isThreadStop == true){
                        break;
                    }
                } catch (InterruptedException | FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
