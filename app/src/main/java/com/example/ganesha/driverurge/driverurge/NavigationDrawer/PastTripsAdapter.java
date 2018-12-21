package com.example.ganesha.driverurge.NavigationDrawer;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ganesha.driverurge.R;

public class PastTripsAdapter extends RecyclerView.Adapter<PastTripsAdapter.RecyclerVH> {

    Context c;

    public PastTripsAdapter(Context c) {
        this.c = c;
    }

    @Override
    public RecyclerVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(c).inflate(R.layout.history_past_trip,parent,false);
        return new RecyclerVH(v);
    }

    @Override
    public void onBindViewHolder(RecyclerVH holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 3;
    }

    /*
    VIEWHOLDER CLASS
     */
    public class RecyclerVH extends RecyclerView.ViewHolder
    {
        public RecyclerVH(View itemView) {
            super(itemView);

        }
    }
}