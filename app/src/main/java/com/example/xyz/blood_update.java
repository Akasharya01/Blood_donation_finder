package com.example.xyz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class blood_update extends AppCompatActivity {
EditText aplus,anev,bplus,bnev,abplus,abnev,oplus,onev;
TextView save;
ArrayList<String> list=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_update);
        aplus=findViewById(R.id.aplus);
        anev=findViewById(R.id.anev);
        abplus=findViewById(R.id.abplus);
        abnev=findViewById(R.id.abnev);
        bplus=findViewById(R.id.bplus);
        bnev=findViewById(R.id.bnev);
        oplus=findViewById(R.id.oplus);
        onev=findViewById(R.id.onev);
        save=findViewById(R.id.save);
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("admin").child("bloodunits");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    list.add(dataSnapshot.getValue().toString());
                }
                aplus.setText(list.get(0));anev.setText(list.get(1));
                abplus.setText(list.get(2));abnev.setText(list.get(3));
                bplus.setText(list.get(4));bnev.setText(list.get(5));
                oplus.setText(list.get(6));onev.setText(list.get(7));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap<String,String> blood=new HashMap<>();
                blood.put("A+",aplus.getText().toString());blood.put("A-",anev.getText().toString());
                blood.put("AB+",abplus.getText().toString());blood.put("AB-",abnev.getText().toString());
                blood.put("B+",bplus.getText().toString());blood.put("B-",bnev.getText().toString());
                blood.put("O+",oplus.getText().toString());blood.put("O-",onev.getText().toString());
                reference.setValue(blood);
            }
        });
    }
}