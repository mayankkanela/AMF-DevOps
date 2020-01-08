package com.mayank.amf_devops;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LaunchActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        mAuth = FirebaseAuth.getInstance();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                FirebaseUser user = mAuth.getCurrentUser();
                if(user!=null)
                {
                    startActivity(new Intent(LaunchActivity.this,MainActivity.class));
                    LaunchActivity.this.finish();
                }
                else
                    startActivity(new Intent(LaunchActivity.this,LoginActivity.class));
                    LaunchActivity.this.finish();
            }
        },200);

    }
}
