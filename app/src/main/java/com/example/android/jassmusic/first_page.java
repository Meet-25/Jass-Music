package com.example.android.jassmusic;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class first_page extends AppCompatActivity {

    BottomNavigationView bnview;
    FirebaseAuth auth;
    FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_page);
        auth=FirebaseAuth.getInstance();
        user=auth.getCurrentUser();
//        if (user==null){
//            Intent intent=new Intent(getApplicationContext(),person_Fragment.class);
//            startActivity(intent);
//        }
<<<<<<< HEAD
//hi meet Dobariya
=======
//hi
>>>>>>> da6f88802314813881d06d150a8456c78c6fee35
        bnview=findViewById(R.id.bnview);

        bnview.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id=item.getItemId();
                if (id==R.id.home){
                    callFragment(new home_Fragment(),true);

                }else if(id==R.id.person){
                    callFragment(new person_Fragment(),false);

                }
                else if(id==R.id.favourate){
                    callFragment(new favourite_Fragment(),false);

                }
                else if(id==R.id.search){
                    callFragment(new search_Fragment(),false);
                }

                return true;
            }
        });
        bnview.setSelectedItemId(R.id.home);
        }

    public void callFragment(Fragment fragment , boolean flag) {
        FragmentManager fm=getSupportFragmentManager();
        FragmentTransaction ft= fm.beginTransaction();
        if (flag==true) {
            ft.add(R.id.framelayout, fragment);
        }else {
            ft.replace(R.id.framelayout,fragment);
        }
        ft.commit();
    }
}