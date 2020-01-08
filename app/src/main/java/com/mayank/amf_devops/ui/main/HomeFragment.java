package com.mayank.amf_devops.ui.main;

import android.icu.text.CaseMap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.mayank.amf_devops.R;
import com.mayank.amf_devops.adapters.RecyclerViewAdapter;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
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
        rvHome.setAdapter(recyclerViewAdapter);
        return root;
    }

    private void initListener(View root) {
    }

    private void initData(View root) {
        db = FirebaseFirestore.getInstance();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        String sUser = user.getUid();
       //Database call
        db.collection("Thread").whereEqualTo("UserId", sUser)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful())
                {   if(!task.getResult().isEmpty())
                    for(QueryDocumentSnapshot queryDocumentSnapshot: task.getResult())
                        {
                            sTitles.add(queryDocumentSnapshot.get("Title").toString());
                        }
                        recyclerViewAdapter.notifyDataSetChanged();

                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("Test :","Failed"+e.getMessage());
            }
        });

        recyclerViewAdapter = new RecyclerViewAdapter(root.getContext(), sTitles);
        rvHome.setLayoutManager(new LinearLayoutManager(root.getContext(),RecyclerView.VERTICAL,false));

    }

    private void initView(View root) {
        rvHome = root.findViewById(R.id.rvHome);

    }
}