package com.example.myapplication;
import androidx.annotation.NonNull;
import androidx.viewpager2.adapter.*;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MyPagerAdapter extends
        RecyclerView.Adapter<MyPagerAdapter.ViewHolder> {
    private final int[] layouts = {R.layout.page1,
            R.layout.page2, R.layout.page3};

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layouts[viewType], parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return layouts.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
