package com.example.ganesha.driverurge.Auth;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ganesha.driverurge.R;
import com.example.ganesha.driverurge.RegistrationSignIn.RegisterActivity;

public class EarnMain extends Fragment
{
    TextView tvProceed;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.earn_main, container, false);
        tvProceed = (TextView)view.findViewById(R.id.tvProceed);
        tvProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),RegisterActivity.class);
                startActivity(intent);
            }
        });
        return view;

    }

}
