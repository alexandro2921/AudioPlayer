package com.example.audioplayer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements RVAdapterInterface{


    SearchView searchView;
    RecyclerView recyclerView;
    List<Model> modelList;
    RVAdapter adapter;
    MediaPlayer mediaPlayer;
    ImageView prev,play,next;
    SeekBar seekBar;
    Integer currentAudioIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        modelList = getModelList();
        List<Model> modelListFiltered = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerView);
        adapter = new RVAdapter(this,MainActivity.this, modelList);
        recyclerView.setAdapter(adapter);

        searchView = findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                showSearchResult(newText);
                return false;
            }
        });

        prev = findViewById(R.id.previous);
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playPrevious();
            }
        });
        play = findViewById(R.id.play);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playPause();
            }
        });
        next = findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playNext();
            }
        });
        seekBar = findViewById(R.id.seekBar);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser){
                    mediaPlayer.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        MainActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {

                if(mediaPlayer!=null){
                    seekBar.setMax(mediaPlayer.getDuration());
                    seekBar.setProgress(mediaPlayer.getCurrentPosition());

                    if(mediaPlayer.isPlaying()){
                        play.setImageResource(R.drawable.ic_pause);
                    }else{
                        play.setImageResource(R.drawable.ic_play);
                    }
                }

                new Handler().postDelayed(this,100);
            }
        });
    }
    public List<Model> getModelList(){
        List<Model> list = new ArrayList<>();

        list.add(new Model(R.raw.a_girl_like_you,R.drawable.ic_baseline_music_note_24," A Girl Like You","Daniel J",false));
        list.add(new Model(R.raw.seven,R.drawable.ic_baseline_music_note_24," Seven","Jungkook",false));
        list.add(new Model(R.raw.the_box,R.drawable.ic_baseline_music_note_24," The Box","Roddy Ricch",false));
        list.add(new Model(R.raw.naughty_or_nice,R.drawable.ic_baseline_music_note_24," Naughty or Nice","Cash Cash",false));
        list.add(new Model(R.raw.humble,R.drawable.ic_baseline_music_note_24," Humble","Kendrick Lamar",false));
        list.add(new Model(R.raw.what_it_is,R.drawable.ic_baseline_music_note_24," What It Is Block Boy","Doechii",false));
        list.add(new Model(R.raw.billie_eilish,R.drawable.ic_baseline_music_note_24," Billie Eilish","Armani White",false));
        return list;
    }

    public void showSearchResult(String input){
        List<Model> result = new ArrayList<>();
        for(Model model : modelList){
            if(model.getArtistName().toLowerCase().contains(input.toLowerCase())){
                result.add(model);
            }
        }
        
        if(result.isEmpty()){
            Toast.makeText(this, "Not Found", Toast.LENGTH_SHORT).show();
        }else{
            adapter.setResultList(result);
            adapter.notifyDataSetChanged();
        }

        Log.i("MSGG",result.toString());

    }

    public void playPrevious(){
        if(mediaPlayer!=null){
            mediaPlayer.release();
        }

        if(currentAudioIndex==0){
            currentAudioIndex=1;
            Toast.makeText(this, "NO MORE PREVIOUS SONG", Toast.LENGTH_SHORT).show();
        }
            mediaPlayer = MediaPlayer.create(MainActivity.this,modelList.get(currentAudioIndex-1).getId());
            if(mediaPlayer.isPlaying()){
                mediaPlayer.stop();
            }else{
                mediaPlayer.start();
                for(Model model : modelList) {
                    model.setPlaying(false);
                }
                modelList.get(currentAudioIndex-1).setPlaying(true);
                currentAudioIndex-=1;
                adapter.notifyDataSetChanged();
            }
    }

    public void playNext(){
        if(mediaPlayer!=null){
            mediaPlayer.release();
        }
        if(currentAudioIndex==modelList.size()-1) {
            currentAudioIndex = modelList.size() - 1;
            Toast.makeText(this, "NO MORE NEXT SONG", Toast.LENGTH_SHORT).show();
            mediaPlayer = MediaPlayer.create(MainActivity.this, modelList.get(currentAudioIndex).getId());
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
            } else {
                mediaPlayer.start();
                for (Model model : modelList) {
                    model.setPlaying(false);
                }
                modelList.get(currentAudioIndex).setPlaying(true);
            }
        }else{
            mediaPlayer = MediaPlayer.create(MainActivity.this,modelList.get(currentAudioIndex+1).getId());
            if(mediaPlayer.isPlaying()){
                mediaPlayer.stop();
            }else{
                mediaPlayer.start();
                for(Model model : modelList) {
                    model.setPlaying(false);
                }
                modelList.get(currentAudioIndex+1).setPlaying(true);
        }
            currentAudioIndex+=1;
            adapter.notifyDataSetChanged();
        }
    }

    public void playPause(){
        if(mediaPlayer.isPlaying()&&mediaPlayer!=null){
            mediaPlayer.pause();
        }else{
            mediaPlayer.start();
        }
    }

    @Override
    public void onItemClick(int position) {

        currentAudioIndex = position;
        Model audio = modelList.get(position);
        if(mediaPlayer!=null){
            mediaPlayer.release();
        }

        try{
            mediaPlayer = MediaPlayer.create(MainActivity.this,audio.getId());
            if(mediaPlayer.isPlaying()){
                mediaPlayer.stop();
                audio.setPlaying(false);
            }else{
                mediaPlayer.start();
                for(Model model : modelList) {
                    model.setPlaying(false);
                }
                audio.setPlaying(true);
                adapter.notifyDataSetChanged();
            }

        }catch (Exception e){
            Log.e("exception",e.getMessage());
        }
    }
}