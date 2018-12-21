package com.example.ganesha.driverurge.NavigationDrawer;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ganesha.driverurge.R;

public class YourTripAdapter extends RecyclerView.Adapter<YourTripAdapter.RecyclerVH> {

    Context c;

    public YourTripAdapter(Context c) {
        this.c = c;
    }

    @Override
    public RecyclerVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(c).inflate(R.layout.your_trip_child,parent,false);
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
        CardView cardView;

        public RecyclerVH(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.card_view);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), HistoryActivity.class);
                    view.getContext().startActivity(intent);
                }
            });


        }
    }
}