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

public class patient_request extends AppCompatActivity {
    ArrayList<Donnar> listdonor=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_request);
        RecyclerView recyclerView2=findViewById(R.id.admin_rv2);
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("patient");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    // Convert each donor data to a Donor object
                    Donnar donor = dataSnapshot.getValue(Donnar.class);
                    listdonor.add(donor);
                }
                Patient_Recycle recycle= new Patient_Recycle(getApplicationContext(),listdonor);
                recyclerView2.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                recyclerView2.setAdapter(recycle);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}