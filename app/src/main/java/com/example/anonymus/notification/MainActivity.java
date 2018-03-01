package com.example.anonymus.notification;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity{
    Button start,seek,stop;
    MyService myService;
    Boolean isBound = false;
    ServiceConnection connection;

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        start = findViewById(R.id.start);
        stop = findViewById(R.id.stop);
        seek = findViewById(R.id.seek);

        connection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                MyService.MyBinder binder = new MyService().new MyBinder();
                myService = binder.getService();
                isBound = true;
            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {
                isBound = false;
            }
        };


        final Intent intent = new Intent(this,MyService.class);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bindService(intent,connection, Context.BIND_AUTO_CREATE);
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //neu server dang chay thi tat di
                if (isBound){
                    unbindService(connection);
                    isBound = false;
                }
            }
        });

        seek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isBound){
                    myService.seek();
                }else {
                    Toast.makeText(myService, "Service chua hoat dong seek cai l", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }



}
