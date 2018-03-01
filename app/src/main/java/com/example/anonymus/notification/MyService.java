package com.example.anonymus.notification;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;

public class MyService extends Service {
    private MyPlayer myPlayer;
    private IBinder iBinder;

    @Override
    public void onCreate() {
        super.onCreate();
        myPlayer = new MyPlayer(this);
        iBinder = new MyBinder();
    }

    @Override
    public IBinder onBind(Intent intent) {
       myPlayer.startMusic();
       return iBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {

        myPlayer.stopMusic();
        return super.onUnbind(intent);
    }

    public void seek(){
        myPlayer.seekMusic(60000);
    }


    private class MyPlayer {
        private MediaPlayer mediaPlayer;

        public MyPlayer(Context context) {
            mediaPlayer = MediaPlayer.create(context,R.raw.xindungbuongloichiatay);
            mediaPlayer.setLooping(true);
        }
        public void startMusic(){
            if (mediaPlayer!=null){
                mediaPlayer.start();
            }
        }
        public void seekMusic(int position){
            mediaPlayer.seekTo(position);
        }
        public void stopMusic(){
            if (mediaPlayer!=null){
                mediaPlayer.stop();
            }
        }
    }

    public class MyBinder extends Binder {
        public MyService getService(){
            return MyService.this;
        }
    }
}
