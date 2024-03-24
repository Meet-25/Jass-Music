package com.example.android.jassmusic;

import static android.widget.Toast.makeText;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.common.util.JsonUtils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link favourite_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class favourite_Fragment extends androidx.fragment.app.Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ArrayList<dataModel> arrayList;
    RecyclerView recyclerView;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;



    public favourite_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment favourite_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static favourite_Fragment newInstance(String param1, String param2) {
        favourite_Fragment fragment = new favourite_Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    // Create a method to play media from an ArrayList of resource IDs
    public void playMediaFromArrayList(Context context, ArrayList<Integer> mediaResources) {
        for (Integer mediaResource : mediaResources) {
            MediaPlayer mediaPlayer = MediaPlayer.create(context, mediaResource);
            mediaPlayer.start();
            // You can handle mediaPlayer events (completion, errors, etc.) as needed.
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        arrayList = new ArrayList<>();
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favourite_, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference("songs");

//hi

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



        arrayList.add(new dataModel(0, R.drawable.blindinglights, "Jazz Music", R.raw.i_was_never_there));
        arrayList.add(new dataModel(1, R.drawable.blindinglights, "Jazz Music", R.raw.i_was_never_there));
        arrayList.add(new dataModel(2, R.drawable.blindinglights, "Jazz Music", R.raw.i_was_never_there));
        arrayList.add(new dataModel(3, R.drawable.jassimage, "Jazz Music", R.raw.summertime_sadness));
        arrayList.add(new dataModel(4, R.drawable.jassimage, "Jazz Music", R.raw.summertime_sadness));
        arrayList.add(new dataModel(5, R.drawable.jassimage, "Jazz Music", R.raw.summertime_sadness));
        arrayList.add(new dataModel(6, R.drawable.blindinglights, "Jazz Music", R.raw.i_was_never_there));
        arrayList.add(new dataModel(7, R.drawable.jassimage, "Jazz Music", R.raw.summertime_sadness));
        arrayList.add(new dataModel(8, R.drawable.blindinglights, "Jazz Music", R.raw.i_was_never_there));
        arrayList.add(new dataModel(9, R.drawable.jassimage, "Jazz Music", R.raw.summertime_sadness));
        arrayList.add(new dataModel(10, R.drawable.blindinglights, "Jazz Music", R.raw.i_was_never_there));
        arrayList.add(new dataModel(11, R.drawable.jassimage, "Jazz Music", R.raw.summertime_sadness));
        arrayList.add(new dataModel(12, R.drawable.blindinglights, "Jazz Music", R.raw.i_was_never_there));
        arrayList.add(new dataModel(13, R.drawable.jassimage, "Jazz Music", R.raw.summertime_sadness));
        arrayList.add(new dataModel(14, R.drawable.blindinglights, "Jazz Music", R.raw.i_was_never_there));
        arrayList.add(new dataModel(15, R.drawable.jassimage, "Jazz Music", R.raw.summertime_sadness));
        arrayList.add(new dataModel(16, R.drawable.blindinglights, "Jazz Music", R.raw.i_was_never_there));
        arrayList.add(new dataModel(17, R.drawable.jassimage, "Jazz Music", R.raw.summertime_sadness));



        return view;
    }
}