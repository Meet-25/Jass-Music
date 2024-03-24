package com.example.android.jassmusic;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.graphics.drawable.Animatable;
import android.media.session.MediaSession;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

public class MainActivity extends AppCompatActivity {

    //variables
    Animation topAnim, bottomAnim;
    ImageView image;
    TextView jazzText, slogan;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);

        image = findViewById(R.id.jazz_image);
        jazzText = findViewById(R.id.jazz_text);
        slogan = findViewById(R.id.slogan);

        image.setAnimation(topAnim);
        jazzText.setAnimation(bottomAnim);
        slogan.setAnimation(bottomAnim);
        Handler handel = new Handler();
        Runnable runn = new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, first_page.class);
                startActivity(intent);
                finish();
            }
        };
        int splashScreen = 4000;
        handel.postDelayed(runn, splashScreen);



    }
    private void replaceFragment(Fragment fragment){
//        FragmentManager fragmentManager=getSupportFragmentManager();
//        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
//        fragmentTransaction.replace(R.id.framelayout,fragment);
//        fragmentTransaction.commit();
    }
}