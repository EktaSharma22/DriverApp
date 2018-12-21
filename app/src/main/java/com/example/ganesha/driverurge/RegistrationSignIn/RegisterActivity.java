package com.example.ganesha.driverurge.RegistrationSignIn;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.ganesha.driverurge.R;

public class RegisterActivity extends AppCompatActivity {

    Button btnSignup, btnSignin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btnSignup = (Button)findViewById(R.id.btnSignup);
        btnSignin = (Button)findViewById(R.id.btnSignin);

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_signup =  new Intent(RegisterActivity.this,SignUpActivity.class);
                startActivity(intent_signup);
            }
        });


        btnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_login = new Intent(RegisterActivity.this,SignInActivity.class);
                startActivity(intent_login);
            }
        });


    }
}
