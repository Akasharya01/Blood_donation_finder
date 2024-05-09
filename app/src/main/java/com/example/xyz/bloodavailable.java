package com.example.xyz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class bloodavailable extends AppCompatActivity {
        TextView a_plus,a_min,b_plus,b_min,ab_plus,ab_min,o_plus,o_min;
        String a="7",b,c,d,e,f,g,h;
        ArrayList<String> list=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bloodavailable);
        a_plus=findViewById(R.id.a_plus);
        a_min=findViewById(R.id.a_min);
        b_plus=findViewById(R.id.b_plus);
        b_min=findViewById(R.id.b_min);
        ab_plus=findViewById(R.id.ab_plus);
        ab_min=findViewById(R.id.ab_min);
        o_plus=findViewById(R.id.o_plus);
        o_min=findViewById(R.id.o_min);
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("admin").child("bloodunits");
        reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                            list.add(dataSnapshot.getValue().toString());
                        }
                        for(int i=0;i<list.size();i++)
                        {
                            if(list.get(i).equals("0") || list.get(i).equals("1"))
                            {
                                list.remove(i);
                                list.add(i,"not available");
                            }
                        }
                        a_plus.setText(list.get(0));a_min.setText(list.get(1));
                        b_plus.setText(list.get(2));b_min.setText(list.get(3));
                        ab_plus.setText(list.get(4));ab_min.setText(list.get(5));
                        o_plus.setText(list.get(6));o_min.setText(list.get(7));
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
        });
    }
}