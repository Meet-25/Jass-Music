package com.example.android.jassmusic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.android.material.slider.Slider;

import java.util.ArrayList;

public class music_playing_Activity extends AppCompatActivity {

    Slider slider;
    ImageView skip_previous,play_arrow,skip_next,music_playing_image;
    MediaPlayer mediaPlayer;
    ArrayList<dataModel> arrayList;
    int position;
    String music,image;
    int slider_height_default=0;
    CardView cardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_playing);

        slider=findViewById(R.id.slider);
        play_arrow=findViewById(R.id.play_arrow);
        skip_next=findViewById(R.id.skip_next);
        skip_previous=findViewById(R.id.skip_previous);
        music_playing_image=findViewById(R.id.music_playing_image);
        cardView=findViewById(R.id.materialCardView);

        Intent intent=getIntent();
//        Bundle bundle=intent.getExtras();
        arrayList= (ArrayList<dataModel>) intent.getSerializableExtra("songs");
        music = intent.getStringExtra("music");
        image=intent.getStringExtra("image");
        position=intent.getIntExtra("pos",0);

        System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
        Log.e("Music resource", "music arraylist: " + arrayList);
        Log.e("Music resource", "music position: " + arrayList.get(position));
        Log.e("Music uri", "music uri: " + music);

        String imageUrl = Uri.parse(image).toString();

//        music_playing_image.setImageResource(Integer.parseInt(image));
        Glide.with(this).load(imageUrl).into(music_playing_image);

        Animation animation= AnimationUtils.loadAnimation(this,R.anim.music_image_animation);
        cardView.setAnimation(animation);

        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.reset();
        }

        Uri uri=Uri.parse(arrayList.get(position).getSongurl());
        Log.e("Music uri", "music uri: " + uri.toString());
        mediaPlayer = MediaPlayer.create(getApplicationContext(),uri);
        mediaPlayer.start();


        slider.addOnSliderTouchListener(new Slider.OnSliderTouchListener() {
            @Override
            public void onStartTrackingTouch(@NonNull Slider slider) {
                slider.setTrackHeight(30);
            }

            @Override
            public void onStopTrackingTouch(@NonNull Slider slider) {
                slider.setTrackHeight(slider_height_default);
                mediaPlayer.seekTo(mediaPlayer.getDuration());
            }
        });

        play_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                    play_arrow.setImageResource(R.drawable.pause_arrow);
                }
                else {
                    mediaPlayer.start();
                    play_arrow.setImageResource(R.drawable.play_arrow);
                }
            }
        });

        skip_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    mediaPlayer.stop();
                    mediaPlayer.release();

                position = (position + 1) % arrayList.size();
                System.out.println("hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh");
                Log.e("Music position", "music position: " + music);
                String next_image=arrayList.get(position).getImg();
                String imageUrl = Uri.parse(next_image).toString();
                Glide.with(getApplicationContext()).load(imageUrl).into(music_playing_image);
                Uri uri=Uri.parse(arrayList.get(position).getSongurl());
                mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
                mediaPlayer.start();
                }
        });

        skip_previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.stop();
                mediaPlayer.release();
                position = ((position - 1) < 0) ? (arrayList.size() - 1) : position - 1;
                System.out.println("hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh");
                Log.e("Music position", "music position: " + music);
                String previous_image=arrayList.get(position).getImg();
                String imageUrl = Uri.parse(previous_image).toString();
                Glide.with(getApplicationContext()).load(imageUrl).into(music_playing_image);
                Uri uri=Uri.parse(arrayList.get(position).getSongurl());
                mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
                mediaPlayer.start();
            }
        });
    }
}