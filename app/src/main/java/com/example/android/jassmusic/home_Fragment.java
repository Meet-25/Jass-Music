package com.example.android.jassmusic;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class home_Fragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    ArrayList<dataModel> arrayList1;

    ArrayList<dataModel> arrayList2;
    ArrayList<dataModel> arrayList3;
    RecyclerView recyclerView;
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
        View view = inflater.inflate(R.layout.fragment_home_, container, false);
        recyclerView = view.findViewById(R.id.recyclerView2);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        int index=0;



        arrayList1 = new ArrayList<>();
        arrayList1.add(new dataModel(0,R.drawable.jassimage, "Jazz music",R.raw.jazzgirl));
        arrayList1.add(new dataModel(1,R.drawable.blindinglights, "Jazz music",R.raw.i_was_never_there));
        arrayList1.add(new dataModel(2,R.drawable.jassimage, "Jazz music",R.raw.jazzgirl));
        arrayList1.add(new dataModel(3,R.drawable.blindinglights, "Jazz music",R.raw.i_was_never_there));
        arrayList1.add(new dataModel(4,R.drawable.jassimage, "Jazz music",R.raw.jazzgirl));
        arrayList1.add(new dataModel(5,R.drawable.blindinglights, "Jazz music",R.raw.i_was_never_there));
        recyclerView.setAdapter(new RecyclerViewAdapter(arrayList1));

        recyclerView = view.findViewById(R.id.recyclerview3);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        arrayList2 = new ArrayList<>();
        arrayList2.add(new dataModel(6,R.drawable.blindinglights, "Jazz music",R.raw.i_was_never_there));
        arrayList2.add(new dataModel(7,R.drawable.jassimage, "Jazz music",R.raw.jazzgirl));
        arrayList2.add(new dataModel(8,R.drawable.jassimage, "Jazz music",R.raw.i_was_never_there));
        arrayList2.add(new dataModel(9,R.drawable.jassimage, "Jazz music",R.raw.jazzgirl));
        arrayList2.add(new dataModel(10,R.drawable.jassimage, "Jazz music",R.raw.i_was_never_there));
        arrayList2.add(new dataModel(11,R.drawable.jassimage, "Jazz music",R.raw.jazzgirl));
        recyclerView.setAdapter(new RecyclerViewAdapter(arrayList2));


        recyclerView = view.findViewById(R.id.recyclerview4);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        arrayList3 = new ArrayList<>();
        arrayList3.add(new dataModel(12,R.drawable.blindinglights, "Jazz music",R.raw.jazzgirl));
        arrayList3.add(new dataModel(13,R.drawable.jassimage, "Jazz music",R.raw.i_was_never_there));
        arrayList3.add(new dataModel(14,R.drawable.blindinglights, "Jazz music",R.raw.jazzgirl));
        arrayList3.add(new dataModel(15,R.drawable.jassimage, "Jazz music",R.raw.i_was_never_there));
        arrayList3.add(new dataModel(16,R.drawable.blindinglights, "Jazz music",R.raw.jazzgirl));
        arrayList3.add(new dataModel(17,R.drawable.jassimage, "Jazz music",R.raw.i_was_never_there));
        recyclerView.setAdapter(new RecyclerViewAdapter(arrayList3));

        return view;

    }
}