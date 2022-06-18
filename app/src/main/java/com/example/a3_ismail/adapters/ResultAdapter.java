package com.example.a3_ismail.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a3_ismail.data.models.Result;
import com.example.a3_ismail.databinding.CustomRowLayoutBinding;

import java.util.List;

public class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.MyViewHolder> {

    private final Context context;
    private final List<Result> dataSourceArray;
    CustomRowLayoutBinding binding;

    public ResultAdapter(Context context, List<Result> results){
        this.dataSourceArray = results;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(CustomRowLayoutBinding.inflate(LayoutInflater.from(context), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Result item = dataSourceArray.get(position);
        holder.bind(context, item);
    }

    @Override
    public int getItemCount() {
        Log.d("ResultAdapter", "getItemCount: Number of items " +this.dataSourceArray.size() );
        return this.dataSourceArray.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder{
        CustomRowLayoutBinding itemBinding;

        public MyViewHolder(CustomRowLayoutBinding binding){
            super(binding.getRoot());
            this.itemBinding = binding;
        }

        public void bind(Context context, Result currentResult){
            itemBinding.tvDatePlayed.setText("Date Played: " + currentResult.getDate());
            itemBinding.tvScore.setText("Score: " + currentResult.getScore());
        }
    }
}
