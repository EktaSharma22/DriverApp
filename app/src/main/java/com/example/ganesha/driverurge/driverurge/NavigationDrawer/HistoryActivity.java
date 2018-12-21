package com.example.ganesha.driverurge.NavigationDrawer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.example.ganesha.driverurge.R;

public class HistoryActivity extends AppCompatActivity implements View.OnClickListener{

    private RecyclerView recyclerView;
    private PastTripsAdapter pastTripsAdapter;
    private UpcomingTripsAdapter upcomingTripsAdapter;
    private Button btnPastTrips,btnUpcomingTrips;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        //Intialisation
        recyclerView = (RecyclerView) findViewById(R.id.rvHistory);
        btnPastTrips = (Button)findViewById(R.id.btnPastTrips);
        btnUpcomingTrips = (Button) findViewById(R.id.btnUpcomingTrips);

        pastTripsAdapter = new PastTripsAdapter(this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(pastTripsAdapter);

    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.btnRegister:
                pastTripsAdapter = new PastTripsAdapter(this);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.setAdapter(pastTripsAdapter);
                break;

            case R.id.ivShowPass:
                upcomingTripsAdapter = new UpcomingTripsAdapter(this);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(upcomingTripsAdapter);
                break;
        }

    }
}
