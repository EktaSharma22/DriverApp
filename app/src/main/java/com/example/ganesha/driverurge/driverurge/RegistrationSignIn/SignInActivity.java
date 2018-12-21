package com.example.ganesha.driverurge.RegistrationSignIn;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ganesha.driverurge.NavigationDrawer.NavDrawerActivity;
import com.example.ganesha.driverurge.R;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.Arrays;

public class SignInActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 1;
    ImageView ivBack;

    TextView tvAlreadyAccount,tvSignin;

    private ImageView ivFbLog;
    private ProgressBar progressBar;
    private Button btnLogin;
    private EditText etPhoneEmail,etLoginPasswrd;


    private SignInButton mGoogleBtn;
    private GoogleSignInClient mGoogleSignInClient;
    private GoogleApiClient mGoogleApiClient;

    private CallbackManager mCallbackManager;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        // Intialization
        mAuth = FirebaseAuth.getInstance();
        ivFbLog = (ImageView)findViewById(R.id.ivFbLog);
        btnLogin = (Button)findViewById(R.id.btnLogin);
        etPhoneEmail = (EditText)findViewById(R.id.etPhoneEmail);
        etLoginPasswrd = (EditText)findViewById(R.id.etLoginPasswrd);

        //ProgressBar
        progressBar = (ProgressBar)findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.GONE);


        //Bottombar Text
        tvAlreadyAccount = (TextView)findViewById(R.id.tvAlreadyAccount);
        tvAlreadyAccount.setText("Don't have an Account ?");
        tvSignin = (TextView)findViewById(R.id.tvSignin);
        tvSignin.setText("Sign Up");


        //OnClicks
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(userSignIn()){
                    Intent intent = new Intent(SignInActivity.this,NavDrawerActivity.class);
                    startActivity(intent);
                }
            }
        });

        //
        ivBack = (ImageView)findViewById(R.id.ivBack);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });





        //Facebook Callbacks
        mCallbackManager = CallbackManager.Factory.create();
        ivFbLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LoginManager.getInstance().logInWithReadPermissions(SignInActivity.this, Arrays.asList("email", "public_profile"));
                LoginManager.getInstance().registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        Log.d("Facelog", "facebook:onSuccess:" + loginResult);
                        handleFacebookAccessToken(loginResult.getAccessToken());
                    }

                    @Override
                    public void onCancel() {
                        Log.d("Facelog", "facebook:onCancel");
                        // ...
                    }

                    @Override
                    public void onError(FacebookException error) {
                        Log.d("Facelog", "facebook:onError", error);
                        // ...
                    }
                });

            }
        });


        // Google Authentication
        mGoogleBtn = (SignInButton)findViewById(R.id.sign_in_button);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(getApplicationContext())
               .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
                   @Override
                   public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

                   }
               })
               .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
               .build();

         mGoogleBtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               signIn();
           }
       });

    }


    @Override
    protected void onStart() {
        super.onStart();
        //mAuth.addAuthStateListener(mAuthListner);
        FirebaseUser user = mAuth.getCurrentUser();
        if(user != null)
        { progressBar.setVisibility(View.VISIBLE);
          updateUI();
        }}


    /*
        Update UI
     */

    private void updateUI() {
        progressBar.setVisibility(View.VISIBLE);
        Intent intent = new Intent(SignInActivity.this,UploadActivity.class);
        startActivity(intent);
        finish();
    }


    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        mCallbackManager.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if(result.isSuccess()){
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
        }}
    }

    /*
        FirebaseGoogleAuth
     */

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            progressBar.setVisibility(View.VISIBLE);
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("status", "signInWithCredential:success");
                            updateUI();
                            mGoogleApiClient.clearDefaultAccountAndReconnect();

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("status", "signInWithCredential:failure", task.getException());
                            Toast.makeText(SignInActivity.this,"Authentication Failed",Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }



    /*
       Facebook Auth
     */

    private void handleFacebookAccessToken(AccessToken token) {
        Log.d("Facelog", "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            progressBar.setVisibility(View.VISIBLE);
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("Facelog", "signInWithCredential:success");
                            updateUI();

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("Facelog", "signInWithCredential:failure", task.getException());
                            Toast.makeText(SignInActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }}
                });
    }


    /*
       Handle Backpress
     */

    @Override
    public void onBackPressed()
    {
        finish();
    }


    /*
        Validations on the edit text and handle login button click
     */

    private boolean userSignIn()
    {
        String email = etPhoneEmail.getText().toString().trim();
        String password = etLoginPasswrd.getText().toString().trim();

        if(email.isEmpty())
        {
            etPhoneEmail.setError("Email is required");
            etPhoneEmail.requestFocus();
            return false;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            etPhoneEmail.setError("Enter a valid Email");
            etPhoneEmail.requestFocus();
            return false;
        }

        if(password.isEmpty())
        {
            etLoginPasswrd.setError("Password is required");
            etLoginPasswrd.requestFocus();
            return false;
        }

        if(password.length() < 12)
        {
            etLoginPasswrd.setError("Password must be 12 character long");
            etLoginPasswrd.requestFocus();
            return false;
        }
        return true;
    }

}
