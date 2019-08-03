package com.example.mediaplayer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.File;

public class VideoPlayer extends AppCompatActivity implements View.OnClickListener{

    private VideoView videoPlayer;
    private Button btn_stop;
    private Button btn_pause;
    private Button btn_play;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);
        btn_play=findViewById(R.id.play_video);
        btn_pause=findViewById(R.id.pause_video);
        btn_stop=findViewById(R.id.stop_video);
        btn_play.setOnClickListener(this);
        btn_pause.setOnClickListener(this);
        btn_stop.setOnClickListener(this);
        if(ContextCompat.checkSelfPermission(VideoPlayer.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(VideoPlayer.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
        }else{
            intitMediaPlayer();
        }


    }

    private void intitMediaPlayer() {
        try{
            File file=new File(Environment.getExternalStorageDirectory(),"0084.mp4");
            videoPlayer.setVideoPath(file.getPath());
        }catch (Exception e){
            Toast.makeText(this,"cant find the name",Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode){
            case 1:
                if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    intitMediaPlayer();
                }else{
                    Toast.makeText(this,"deney to open",Toast.LENGTH_LONG).show();
                    finish();
                }
                break;
            default:
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.play:
                if(!videoPlayer.isPlaying()){
                    videoPlayer.start();
                }
                break;
            case R.id.pause:
                if(videoPlayer.isPlaying()){
                    videoPlayer.pause();
                }
                break;
            case R.id.stop:
                if(videoPlayer.isPlaying()){
                    videoPlayer.resume();
                }
                break;
            default:break;
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(videoPlayer!=null){
            videoPlayer.suspend();
        }
    }
}
