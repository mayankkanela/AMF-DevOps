package com.mayank.amf_devops;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.mayank.amf_devops.ui.main.SectionsPagerAdapter;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder builder= new AlertDialog.Builder(MainActivity.this);
                final EditText title = new EditText(MainActivity.this);
                title.setInputType(InputType.TYPE_CLASS_TEXT);
                TextView tv1 = new TextView(MainActivity.this);
                tv1.setTextSize(20);
                tv1.setText("Enter Brief Title");

                final EditText description = new EditText(MainActivity.this);
                description.setInputType(InputType.TYPE_CLASS_TEXT);
                TextView tv2 = new TextView(MainActivity.this);
                tv2.setTextSize(20);
                tv2.setText("Enter Brief Description");

                LinearLayout linearLayout = new LinearLayout(MainActivity.this);
                linearLayout.setOrientation(LinearLayout.VERTICAL);
                linearLayout.addView(tv1, LinearLayout.LayoutParams.MATCH_PARENT);
                linearLayout.addView(title, LinearLayout.LayoutParams.MATCH_PARENT);
                linearLayout.addView(tv2, LinearLayout.LayoutParams.MATCH_PARENT);
                linearLayout.addView(description, LinearLayout.LayoutParams.MATCH_PARENT, 250);

                builder.setView(linearLayout);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialogInterface, int i) {
                        if (!title.getText().toString().isEmpty() && !description.getText().toString().isEmpty()) {

                            FirebaseAuth auth = FirebaseAuth.getInstance();
                            FirebaseUser user = auth.getCurrentUser();
                            FirebaseFirestore db = FirebaseFirestore.getInstance();

                            Map<String, Object> item = new HashMap<>();
                            item.put("UserId",user.getUid());
                            item.put("Title",title.getText().toString());
                            item.put("Description",description.getText().toString());

                            db.collection("Thread").document()
                                    .set(item)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(MainActivity.this,"Success!",Toast.LENGTH_SHORT).show();
                                    MainActivity.this.finish();
                                    startActivity(MainActivity.this.getIntent());
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(MainActivity.this,"Failed, try again!",Toast.LENGTH_SHORT).show();
                                    dialogInterface.dismiss();
                                }
                            });
                        }
                        else
                            Toast.makeText(MainActivity.this,"Missing Fields!",Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();

            }
        });
    }
}