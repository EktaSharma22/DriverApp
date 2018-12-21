package com.example.ganesha.driverurge.Base;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.ganesha.driverurge.R;
import com.github.lguipeng.library.animcheckbox.AnimCheckBox;

public class CheckBox extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_box);
        AnimCheckBox checkbox = (AnimCheckBox)findViewById(R.id.checkbox);
        checkbox.setChecked(true);
        boolean animation = true;
        checkbox.setChecked(false, animation);
    }
}
