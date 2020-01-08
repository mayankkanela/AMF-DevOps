package com.mayank.amf_devops.ui.main;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.mayank.amf_devops.R;
import com.mayank.amf_devops.adapters.RecyclerViewAdapter;

import java.util.ArrayList;


public class SecondFragment extends Fragment {

    private FirebaseFirestore db;
    private FirebaseUser user;

    private ArrayList<String> sTitles = new ArrayList<>();

    private RecyclerView rvHome;
    private RecyclerViewAdapter recyclerViewAdapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        initView(root);
        initData(root); // Init data and database call
        initListener(root);
        return root;
    }

    private void initListener(View root) {
    }

    private void initData(View root) {
        db = FirebaseFirestore.getInstance();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        //Database call
        db.collection("Thread").
            get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if(!queryDocumentSnapshots.isEmpty())
                {
                    for(QueryDocumentSnapshot queryDocumentSnapshot: queryDocumentSnapshots)
                    {
                        sTitles.add(queryDocumentSnapshot.get("Title").toString());
                        recyclerViewAdapter.notifyDataSetChanged();
                    }
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

        recyclerViewAdapter = new RecyclerViewAdapter(root.getContext(), sTitles);
        rvHome.setLayoutManager(new LinearLayoutManager(root.getContext(),RecyclerView.VERTICAL,false));
        rvHome.setAdapter(recyclerViewAdapter);
    }

    private void initView(View root) {
        rvHome = root.findViewById(R.id.rvHome);
    }
}