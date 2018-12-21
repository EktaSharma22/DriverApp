package com.example.ganesha.driverurge.Auth;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.ganesha.driverurge.R;

public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ViewPager pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(new Pager(getSupportFragmentManager()));


    }
}
