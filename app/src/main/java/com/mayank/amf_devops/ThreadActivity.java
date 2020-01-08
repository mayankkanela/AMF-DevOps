package com.mayank.amf_devops;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ThreadActivity extends AppCompatActivity {
    private TextView tvAlias;
    private TextView tvTitle;
    private TextView tvDescription;
    private TextView tvReply;

    private FirebaseAuth auth;
    private FirebaseUser user;
    private FirebaseFirestore db;

    private ArrayList<String> replies = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread);
        initView();
        initData();
        initListeners();
    }

    private void initListeners() {
        tvReply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

    private void initData() {
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        db = FirebaseFirestore.getInstance();
        Intent intent = new Intent();
        intent = getIntent();
        tvTitle.setText(intent.getStringExtra("Title"));

        db.collection("Thread").whereEqualTo("UserId", user.getUid())
                .whereEqualTo("Title", intent.getStringExtra("Title")).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (!queryDocumentSnapshots.isEmpty()) {
                            for (QueryDocumentSnapshot queryDocumentSnapshot : queryDocumentSnapshots) {
                                tvAlias.setText("ninja");
                                tvDescription.setText(queryDocumentSnapshot.get("Description").toString());
                                replies = (ArrayList<String>) queryDocumentSnapshot.get("Replies");
                            }
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

    }

    private void initView() {
        tvAlias = findViewById(R.id.tvAlias);
        tvDescription = findViewById(R.id.tvDescp);
        tvTitle = findViewById(R.id.tvTitle);
        tvReply = findViewById(R.id.tvReply);
    }

}
