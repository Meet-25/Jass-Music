package com.example.android.jassmusic;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.viewHolder> implements Filterable {
    ArrayList<dataModel> arrayList;
    ArrayList<dataModel> backup;

    public RecyclerViewAdapter(ArrayList<dataModel> arrayList ){
        this.arrayList=arrayList;
        backup=new ArrayList<>(arrayList);
    }
    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.home_list_item,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        holder.imageView.setImageResource(arrayList.get(position).getImage());
        holder.textView.setText(arrayList.get(position).getText());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter=new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence keyword) {
            ArrayList<dataModel> filtereddata=new ArrayList();
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

    static class viewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView textView;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.recycler_image);
            textView=itemView.findViewById(R.id.recycler_text);
        }
    }
}
