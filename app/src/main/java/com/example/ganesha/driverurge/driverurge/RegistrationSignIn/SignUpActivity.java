package com.example.ganesha.driverurge.RegistrationSignIn;

import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ganesha.driverurge.R;
import com.example.ganesha.driverurge.RetrofitApi.RetrofitClient;
import com.google.firebase.iid.FirebaseInstanceId;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText etFirstName, etLastName, etEmail, etMobile, etPassword, etConfrmPasswrd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        etFirstName =(EditText)findViewById(R.id.etFirstName);
        etLastName =(EditText)findViewById(R.id.etLastName);
        etEmail =(EditText)findViewById(R.id.etEmail);
        etMobile =(EditText)findViewById(R.id.etMobile);
        etPassword =(EditText)findViewById(R.id.etPassword);
        etConfrmPasswrd =(EditText)findViewById(R.id.etCnfrmPassword);

        findViewById(R.id.btnRegister).setOnClickListener(this);
        findViewById(R.id.ivShowPass).setOnClickListener(this);
        findViewById(R.id.ivShowCpass).setOnClickListener(this);
        findViewById(R.id.ivBack).setOnClickListener(this);


    }

    // Validation for the data

    private void userSignup()
    {
        String device_id = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        String device_type = ("android");

        String device_tokens = FirebaseInstanceId.getInstance().getToken();

        String first_name = etFirstName.getText().toString().trim();
        String last_name = etLastName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String mobile = etMobile.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String password_confirmation = etConfrmPasswrd.getText().toString().trim();

        if(first_name.isEmpty())
        {
            etFirstName.setError("First Name is required");
            etFirstName.requestFocus();
            return;
        }

        if(last_name.isEmpty())
        {
            etLastName.setError("Last Name is required");
            etLastName.requestFocus();
            return;
        }

        if(email.isEmpty())
        {
            etEmail.setError("Email is required");
            etEmail.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            etEmail.setError("Enter a valid Email");
            etEmail.requestFocus();
            return;
        }

        if(mobile.isEmpty())
        {
            etMobile.setError("Mobile Number is required");
            etMobile.requestFocus();
            return;
        }

        if(mobile.length() < 13)
        {
            etMobile.setError("Enter a valid mobile number");
            etMobile.requestFocus();
            return;
        }


        if(password.isEmpty())
        {
            etPassword.setError("Password is required");
            etPassword.requestFocus();
            return;
        }

        if(password.length() < 12)
        {
            etPassword.setError("Password must be 12 character long");
            etPassword.requestFocus();
            return;
        }

        if(password_confirmation.isEmpty())
        {
            etPassword.setError("Confirm your Password");
            etPassword.requestFocus();
            return;
        }

        if(!password.equals(password_confirmation))
        {
            etConfrmPasswrd.setError("password does not matches");
            etConfrmPasswrd.requestFocus();
            return;
        }

        Call<ResponseBody> call = RetrofitClient.getmInstance()
                .getApi()
                .createAccount(device_id,device_type,device_tokens,first_name,last_name,email,mobile,password,password_confirmation);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                try {
                   /* String s = response.body().string();
                    Toast.makeText(SignUpActivity.this,s,Toast.LENGTH_LONG).show();*/

                    Intent upload_intent = new Intent(SignUpActivity.this,ResponseScreenActivity.class);
                    startActivity(upload_intent);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(SignUpActivity.this,t.getMessage(),Toast.LENGTH_LONG).show();

            }
        });


    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.btnRegister:
                userSignup();
                break;

            case R.id.ivShowPass:
                etPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                break;

            case R.id.ivShowCpass:
                etConfrmPasswrd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                break;

            case R.id.ivBack:
                finish();
                break;
        }

    }
}
