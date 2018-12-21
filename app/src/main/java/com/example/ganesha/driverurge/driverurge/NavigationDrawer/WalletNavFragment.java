package com.example.ganesha.driverurge.NavigationDrawer;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.ganesha.driverurge.R;

public class WalletNavFragment extends Fragment implements View.OnClickListener
{
    private EditText etWalletAmount;
    private Button btn30,btn50,btn80,btn100;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.nav_wallet_frag, container, false);

        btn30 = (Button)rootview.findViewById(R.id.btn30);
        btn50 = (Button)rootview.findViewById(R.id.btn50);
        btn80 = (Button)rootview.findViewById(R.id.btn80);
        btn100 = (Button)rootview.findViewById(R.id.btn100);
        etWalletAmount = (EditText)rootview.findViewById(R.id.etWalletAmount);

        btn30.setOnClickListener(this);
        btn50.setOnClickListener(this);
        btn80.setOnClickListener(this);
        btn100.setOnClickListener(this);

        return rootview;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Wallet");
    }

    @Override
    public void onClick(View view)
    {
        switch(view.getId())
        {
            case R.id.btn30:
                etWalletAmount.setText("30");
                break;

            case R.id.btn50:
                etWalletAmount.setText("50");
                break;

            case R.id.btn80:
                etWalletAmount.setText("80");
                break;

            case R.id.btn100:
                etWalletAmount.setText("100");
                break;
        }
    }

}
