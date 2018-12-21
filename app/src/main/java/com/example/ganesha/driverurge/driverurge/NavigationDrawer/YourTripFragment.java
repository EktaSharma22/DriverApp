package com.example.ganesha.driverurge.NavigationDrawer;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ganesha.driverurge.R;


public class YourTripFragment extends Fragment {

    private RecyclerView rv;

    public static YourTripFragment newInstance()
    {
        return new YourTripFragment();
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView=inflater.inflate(R.layout.your_trip_fragment,null);

        //REFERENCE
        rv= (RecyclerView) rootView.findViewById(R.id.your_trip_recycler_view);

        //LAYOUT MANAGER
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));

        //ADAPTER
        rv.setAdapter(new YourTripAdapter(getActivity()));

        return rootView;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Your Trips");
    }
}