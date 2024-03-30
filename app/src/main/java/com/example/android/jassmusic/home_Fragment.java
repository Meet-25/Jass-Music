package com.example.android.jassmusic;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class home_Fragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    ArrayList<dataModel> arrayList;
    ArrayList<dataModel> arrayList2;
    ArrayList<dataModel> arrayList3;
    RecyclerView recyclerView;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    RecyclerViewAdapter recyclerViewAdapter;
    MediaPlayer mediaPlayer;
    ValueEventListener valueEventListener;


    public static home_Fragment newInstance(String param1, String param2) {
        home_Fragment fragment = new home_Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public home_Fragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            String mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        arrayList = new ArrayList<>();
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_, container, false);

        recyclerView = view.findViewById(R.id.recyclerView2);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        firebaseDatabase= FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference("songs");


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()){
                    dataModel dataModelobj=ds.getValue(dataModel.class);
                    arrayList.add(dataModelobj);
                    System.out.println("hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh");
                    Log.e("Music position", "song url: " + dataModelobj.getSongurl());
                    Log.e("Music position", "song url: " + dataModelobj.getImg());
                    Log.e("Music position", "array list: " + arrayList.get(0));
                }
                RecyclerViewAdapter2 recyclerViewAdapter2 = new RecyclerViewAdapter2(getContext(),arrayList, new CustomItemClickListener() {
                    @Override
                    public void onItemClick(View v, int position) {

                        if (position >= 0 && position < arrayList.size()) {

                            String image=arrayList.get(position).getImg();
                            String songurl=arrayList.get(position).getSongurl();


                            Intent intent=new Intent(getContext(),music_playing_Activity.class);
                            intent.putExtra("songs",arrayList);
                            intent.putExtra("music",songurl);
                            intent.putExtra("image",image);
                            intent.putExtra("pos",position);
                            Objects.requireNonNull(getContext()).startActivity(intent);

                        }

                    }
                });

                recyclerView.setAdapter(recyclerViewAdapter2);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(),"failed to access data",Toast.LENGTH_LONG).show();
            }
        });


        return view;
    }
}