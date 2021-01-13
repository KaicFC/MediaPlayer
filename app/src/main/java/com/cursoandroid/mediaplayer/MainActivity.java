package com.cursoandroid.mediaplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;

public class MainActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;
    private SeekBar seekVolume;
    private AudioManager audioManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.bach);

        inicializarSeekBar();

    }

    private void inicializarSeekBar(){

        seekVolume = findViewById(R.id.seekVolume);

        //AudioManager
        //Configurar o áudio manager
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        //Recuperar os valores do volume atual e máximo
        int volumeMaximo = audioManager.getStreamMaxVolume(audioManager.STREAM_MUSIC);
        int volumeAtual = audioManager.getStreamVolume(audioManager.STREAM_MUSIC);

        //SeekBar
        //Configurar os valores máximos para a seekBar
        seekVolume.setMax(volumeMaximo);
        //Configurar o volume atual na seekBar
        seekVolume.setProgress(volumeAtual);

        seekVolume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    public void executarSom(View view){

        if( mediaPlayer != null){
            mediaPlayer.start();
        }
    }

    public void pausarSom(View view){

        if(mediaPlayer.isPlaying()){
            mediaPlayer.pause();
        }
    }

    public void pararSom(View view){

        if(mediaPlayer.isPlaying()){
            mediaPlayer.stop();
            mediaPlayer =  MediaPlayer.create(getApplicationContext(), R.raw.bach);
        }
    }

    public void onStop(){
        super.onStop();

        if(mediaPlayer.isPlaying()){
            mediaPlayer.pause();
        }
    }

    public void onDestroy(){
        super.onDestroy();
        if(mediaPlayer != null && mediaPlayer.isPlaying()){
            mediaPlayer.stop();
            //Libera recursos de memoria...
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

}
