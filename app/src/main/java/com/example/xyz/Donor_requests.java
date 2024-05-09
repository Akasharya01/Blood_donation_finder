package com.example.xyz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Donor_requests extends AppCompatActivity {
    ArrayList<String> list=new ArrayList<>();
    ArrayList<Donnar> listdonor=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donor_requests);
        RecyclerView recyclerView=findViewById(R.id.admin_rv);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("donors");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    // Convert each donor data to a Donor object
                    Donnar donor = dataSnapshot.getValue(Donnar.class);
                    listdonor.add(donor);
                }
                requestRecycle recycle= new requestRecycle(getApplicationContext(),listdonor);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                recyclerView.setAdapter(recycle);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}