package com.example.android.jassmusic;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class RecyclerViewAdapter2 extends RecyclerView.Adapter<RecyclerViewAdapter2.viewHolder> implements Filterable {
    ArrayList<dataModel> arrayList;
    ArrayList<dataModel> backup;
    CustomItemClickListener listener;
    Context context;

    RecyclerViewAdapter2(Context context,ArrayList<dataModel> arrayList,CustomItemClickListener listener ){
        this.arrayList=arrayList;
        backup=new ArrayList<>(arrayList);
        backup=arrayList;
        this.listener=listener;
        this.context=context;
    }



    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.home2_item_list,parent,false);
        return new viewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter2.viewHolder holder, int position) {
        String imageUrl = arrayList.get(position).getImg();

        // Load the image from Firebase Storage using Glide or Picasso
        Glide.with(context)
                .load(imageUrl)
                .error(R.drawable.blindinglights)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        Log.e("GlideError", "Error loading image: " + e.getMessage());
                        return false; // Return false to allow Glide to handle the error
                    }
                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(holder.imageView2);

//        holder.imageView2.setImageResource(arrayList.get(position).getImage());
        holder.textView.setText(arrayList.get(position).getText());
        holder.imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int adapterPosition=holder.getAdapterPosition();
                listener.onItemClick(view,adapterPosition);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    Filter filter=new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence keyword) {
            ArrayList<dataModel> filtereddata=new ArrayList<>();
            if(keyword.toString().isEmpty()){
                filtereddata.addAll(backup);
            }
            else{
                for(dataModel s:backup){
                    if(s.getText().toString().toLowerCase().contains(keyword.toString().toLowerCase())){
                        filtereddata.add(s);
                    }
                }
            }
            FilterResults Results=new FilterResults();
            Results.values=filtereddata;
            return Results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            arrayList.clear();
            arrayList.addAll((ArrayList<dataModel>)filterResults.values);
            notifyDataSetChanged();
        }
    };

    @Override
    public Filter getFilter() {
        return filter;
    }


    static class viewHolder extends RecyclerView.ViewHolder {
        ImageView imageView2;
        TextView textView;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            imageView2 = itemView.findViewById(R.id.imageView2);
            textView = itemView.findViewById(R.id.recycler_text2);
//            mediaPlayer = new MediaPlayer();
//
//            // Set up your MediaPlayer here, similar to what you had in onBindViewHolder
//            try {
//                Uri uri = Uri.parse("android.resource://" + context.getPackageName() + "/" +R.raw.i_was_never_there);
//                mediaPlayer.setDataSource(context, uri);
//                mediaPlayer.prepare();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    mediaPlayer.start();
//                }
//            });
        }
    }

}
