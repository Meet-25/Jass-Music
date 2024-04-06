package com.example.android.jassmusic;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link search_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class search_Fragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    public RecyclerViewAdapter2 recyclerViewAdapter2;
    RecyclerView recyclerView;
    SearchView searchView;
    ArrayList<dataModel> arrayList;
    MediaPlayer mediaPlayer;
    private String mParam2;

    public search_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment searchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static search_Fragment newInstance(String param1, String param2) {
        search_Fragment fragment = new search_Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        recyclerView=view.findViewById(R.id.recyclerView5);
        searchView=view.findViewById(R.id.search_view1);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(getContext(),2);
        recyclerView.setLayoutManager(gridLayoutManager);
        arrayList = new ArrayList<>();
        arrayList.add(new dataModel(0,R.drawable.blindinglights, "Jazz Music1",R.raw.i_was_never_there));
        arrayList.add(new dataModel(1,R.drawable.jassimage, "Jazz Music2",R.raw.summertime_sadness));
        arrayList.add(new dataModel(2,R.drawable.blindinglights, "Jazz Music1",R.raw.i_was_never_there));
        arrayList.add(new dataModel(3,R.drawable.jassimage, "Jazz Music1",R.raw.summertime_sadness));
        arrayList.add(new dataModel(4,R.drawable.blindinglights, "Jazz Music1",R.raw.i_was_never_there));
        arrayList.add(new dataModel(5,R.drawable.jassimage, "Jazz Music2",R.raw.summertime_sadness));
        arrayList.add(new dataModel(6,R.drawable.blindinglights, "Jazz Music1",R.raw.i_was_never_there));
        arrayList.add(new dataModel(7,R.drawable.jassimage, "Jazz Music1",R.raw.summertime_sadness));
        arrayList.add(new dataModel(8,R.drawable.blindinglights, "Jazz Music1",R.raw.i_was_never_there));
        arrayList.add(new dataModel(9,R.drawable.jassimage, "Jazz Music2",R.raw.summertime_sadness));
        arrayList.add(new dataModel(12,R.drawable.blindinglights, "Jazz Music1",R.raw.i_was_never_there));
        arrayList.add(new dataModel(13,R.drawable.jassimage, "Jazz Music2",R.raw.summertime_sadness));
        arrayList.add(new dataModel(14,R.drawable.blindinglights, "Jazz Music1",R.raw.i_was_never_there));
        arrayList.add(new dataModel(15,R.drawable.jassimage, "Jazz Music1",R.raw.summertime_sadness));
        arrayList.add(new dataModel(16,R.drawable.blindinglights, "Jazz Music2",R.raw.i_was_never_there));
        arrayList.add(new dataModel(17,R.drawable.jassimage, "Jazz Music1",R.raw.summertime_sadness));
        arrayList.add(new dataModel(18,R.drawable.blindinglights, "Jazz Music1",R.raw.i_was_never_there));
        arrayList.add(new dataModel(19,R.drawable.jassimage, "Jazz Music2",R.raw.summertime_sadness));

        RecyclerViewAdapter2 recyclerViewAdapter2 = new RecyclerViewAdapter2(getContext(),arrayList, new CustomItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                if (position >= 0 && position < arrayList.size()) {
                    final dataModel temp=arrayList.get(position);
                    // Stop and reset the MediaPlayer if it's currently playing
                    if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                        mediaPlayer.stop();
                        mediaPlayer.reset();
                    }
                    // Create a new MediaPlayer instance for the selected audio and start playing
                    mediaPlayer = MediaPlayer.create(getContext(), arrayList.get(temp.index).getMusicresource());
                    Log.e("Music resource", "Invalid music resource: " + arrayList.get(temp.index).getMusicresource());
                    mediaPlayer.start();
                } else {
                    Log.e("MyApp", "Invalid position: " + position);
                }
            }
        });
        recyclerView.setAdapter(recyclerViewAdapter2);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                recyclerViewAdapter2.getFilter().filter(newText);
                return false;
            }
        });


        return view;
    }
}