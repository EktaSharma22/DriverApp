package com.example.ganesha.driverurge.RegistrationSignIn;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;

import com.example.ganesha.driverurge.R;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.nbsp.materialfilepicker.MaterialFilePicker;
import com.nbsp.materialfilepicker.ui.FilePickerActivity;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class UploadActivity extends AppCompatActivity {

    Button btnUpload, btnLogout;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        mAuth = FirebaseAuth.getInstance();

        btnLogout = (Button)findViewById(R.id.btnLogout);
        btnUpload = (Button) findViewById(R.id.btnUpload);

        //permissions
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
            {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},100);
                return;
            }
        }
        enableButton();

        //Logout Button

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mAuth.signOut();
                LoginManager.getInstance().logOut();
                updateUI();

            }
        });

    }

    private void enableButton()
    {
        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MaterialFilePicker()
                        .withActivity(UploadActivity.this)
                        .withRequestCode(10)
                        .start();
            }
        });

    }



    // Request Permission Result

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == 100 && (grantResults[0] == PackageManager.PERMISSION_GRANTED ))
        {
            enableButton();
        }
        else
        {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 100);
            }
        }
    }


    ProgressDialog progress;


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable final Intent data)
    {
        if(requestCode == 10 && resultCode == RESULT_OK)
        {
            progress = new ProgressDialog(UploadActivity.this);
            progress.setTitle("Uploading");
            progress.setMessage("Please wait......");
            progress.show();


            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {

                    File f = new File(data.getStringExtra(FilePickerActivity.RESULT_FILE_PATH));
                    String content_type = getMimeType(f.getPath());
                    String file_path = f.getAbsolutePath();

                    OkHttpClient client = new OkHttpClient();
                    RequestBody file_body = RequestBody.create(MediaType.parse(content_type),f);

                    RequestBody requestBody = new MultipartBody.Builder()
                            .setType(MultipartBody.FORM)
                            .addFormDataPart("type",content_type)
                            .addFormDataPart("uploaded file",file_path.substring(file_path.lastIndexOf("/")+1), file_body)
                            .build();

                    Request request = new Request.Builder()
                            .url("http://192.168.168.26/testing/save_file.php")
                            .post(requestBody)
                            .build();

                    try{
                        Response response = client.newCall(request).execute();
                        if(!response.isSuccessful())
                        {
                            throw new IOException("Error : "+response);
                        }

                        progress.dismiss();

                    } catch (IOException e)
                    {
                        e.printStackTrace();
                    }



                }
            });
        }

    }

    private String getMimeType(String path) {

        String extension = MimeTypeMap.getFileExtensionFromUrl(path);
        return MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
    }

    //Checking the User

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentuser = mAuth.getCurrentUser();
        if(currentuser == null)
        {
            updateUI();
        }
    }

    // Updating Ui

    private void updateUI()
    {
        Intent intent = new Intent(UploadActivity.this,SignInActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed()
    {
        finish();
    }
}
