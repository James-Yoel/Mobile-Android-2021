package umn.ac.id.jamesyoel_00000028895_if633_fl_uts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

public class PlayMusicActivity extends AppCompatActivity {

    Button btnNext, btnPrev, btnPause;
    String songSetTitle;
    TextView songTitle;
    SeekBar mySeekBar;
    static MediaPlayer mediaPlayer;
    int position;
    ArrayList<File> songList;
    Thread updateSeekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_music);
        getSupportActionBar().setTitle("Now Playing");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        btnNext = findViewById(R.id.btnNext);
        btnPause = findViewById(R.id.btnPause);
        btnPrev = findViewById(R.id.btnPrev);
        songTitle = findViewById(R.id.songTitle);
        mySeekBar = findViewById(R.id.seekBar);

        updateSeekBar = new Thread(){
            @Override
            public void run(){
                int totalDuration = mediaPlayer.getDuration();
                int currentPosition = 0;
                while(currentPosition < totalDuration){
                    try{
                        sleep(500);
                        currentPosition = mediaPlayer.getCurrentPosition();
                        mySeekBar.setProgress(currentPosition);
                    }
                    catch (final Exception e){
                        e.printStackTrace();
                        if(e instanceof IllegalStateException){
                            boolean check = true;
                            int counter = 0;
                            for(int i = 0; i < 2; i++){
                                if(check){
                                    mediaPlayer.reset();
                                    if(mediaPlayer != null && mediaPlayer.isPlaying()){
                                        currentPosition = mediaPlayer.getCurrentPosition();
                                    }
                                    else {
                                        currentPosition = 0;
                                    }
                                    if(currentPosition > 0){
                                        check = false;
                                        counter++;
                                    }
                                }
                                else {
                                    if(counter == 0){
                                        try {
                                            throw e;
                                        } catch (InterruptedException interruptedException) {
                                            interruptedException.printStackTrace();
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        };
        if(mediaPlayer != null){
            mediaPlayer.stop();
            mediaPlayer.release();
        }
        Intent myIntent = getIntent();
        Bundle bundle = myIntent.getExtras();
        songList = (ArrayList) bundle.getParcelableArrayList("Music");
        songSetTitle = songList.get(position).getName();
        String title = myIntent.getStringExtra("Title");
        songTitle.setText(title);
        songTitle.setSelected(true);
        position = bundle.getInt("Position", 0);
        Uri uri = Uri.parse(songList.get(position).toString());
        mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
        mediaPlayer.start();
        mySeekBar.setMax(mediaPlayer.getDuration());

        updateSeekBar.start();
        mySeekBar.getProgressDrawable().setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.black), PorterDuff.Mode.MULTIPLY);
        mySeekBar.getThumb().setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.black), PorterDuff.Mode.SRC_IN);
        mySeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(seekBar.getProgress());
            }
        });

        btnPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mySeekBar.setMax(mediaPlayer.getDuration());
                if(mediaPlayer.isPlaying()){
                    btnPause.setBackgroundResource(R.drawable.ic_baseline_play_arrow_24);
                    mediaPlayer.pause();
                }
                else {
                    btnPause.setBackgroundResource(R.drawable.ic_baseline_pause_24);
                    mediaPlayer.start();
                }
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.stop();
                mediaPlayer.release();
                position = ((position + 1)%songList.size());
                Uri uri = Uri.parse(songList.get(position).toString());
                mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
                songSetTitle = songList.get(position).getName();
                songTitle.setText(songSetTitle);
                mySeekBar.setProgress(0);
                mediaPlayer.start();
            }
        });

        btnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.stop();
                mediaPlayer.release();
                position = ((position - 1)< 0)?(songList.size()-1):(position-1);
                Uri uri = Uri.parse(songList.get(position).toString());
                mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
                songSetTitle = songList.get(position).getName();
                songTitle.setText(songSetTitle);
                mySeekBar.setProgress(0);
                mediaPlayer.start();
            }
        });
    }

    public boolean onOptionsItemSelected(MenuItem item){
        Intent myIntent = new Intent(PlayMusicActivity.this, ListLaguMenuActivity.class);
        myIntent.putExtra("Result", "play");
        startActivity(myIntent);
        return true;
    }
}