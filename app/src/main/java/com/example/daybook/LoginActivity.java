package com.example.daybook;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Arrays;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    private EditText mail;
    private EditText password;
    private LoginButton fbLogin;

    private CallbackManager callbackManager;
    private Button signIn, signUp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        auth = FirebaseAuth.getInstance();

        signIn=(Button)findViewById(R.id.sign_in_button);
        signUp=(Button)findViewById(R.id.sign_up_button);
        mail= (EditText)findViewById(R.id.email);
        password = (EditText)findViewById(R.id.password);
        fbLogin = (LoginButton)findViewById(R.id.fb_button);
        fbLogin.setReadPermissions(Arrays.asList("email","public_profile"));
        callbackManager = CallbackManager.Factory.create();

        fbLogin.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                handleFacebookToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Toast.makeText(getApplicationContext(),"Cancelled login!",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(getApplicationContext(),error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = mail.getText().toString();
                final String passw = password.getText().toString();
                try {
                if (passw.length() > 0 && email.length() > 0) {
                    auth.signInWithEmailAndPassword(email, passw).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(LoginActivity.this, "Authentication failed", Toast.LENGTH_LONG).show();
                                Log.v("error", task.getResult().toString());

                            } else {
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                               /* ArrayList<FirebaseAuth> list = new ArrayList<>();
                                list.add(auth);
                                intent.putExtra("auth",list);*/
                                startActivity(intent);
                                finish();
                            }
                        }
                    });
                }else{
                      Toast.makeText(LoginActivity.this,"Fill all the fieds! ",Toast.LENGTH_LONG).show();

                }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.forget_password_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ForgetAndChangePasswordActivity.class).putExtra("Mode", 0));
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onResume() {
        if (auth.getCurrentUser() != null) {
            startActivity(new Intent(LoginActivity.this, AccountSettingsActivity.class));
            finish();
        }
        super.onResume();
    }

    private void handleFacebookToken(AccessToken token){
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        auth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(!task.isSuccessful()){
                    Toast.makeText(LoginActivity.this, "Authentication failed", Toast.LENGTH_LONG).show();
                    Log.v("error", task.getResult().toString());
                }else{
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    /*ArrayList<FirebaseAuth> list = new ArrayList<>();
                    list.add(auth);
                    intent.putExtra("auth",list);
                    */startActivity(intent);
                    finish();
                }
            }
        });

    }
}
