package com.example.xyz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class Need_blood extends AppCompatActivity {
    Spinner blood_group;
    EditText name,unit,contact,reason,age;
    ArrayList<String> list=new ArrayList<>();
    Button request;
    String a,token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_need_blood);
        blood_group=findViewById(R.id.blood_group);
        name=findViewById(R.id.name);
        unit=findViewById(R.id.unit);
        contact=findViewById(R.id.contact);
        reason=findViewById(R.id.reason);
        request=findViewById(R.id.request);
        age=findViewById(R.id.age);
        list.add("--select--");list.add("A+");list.add("O+");list.add("B+");list.add("AB+");list.add("AB-");list.add("A-");list.add("B-");list.add("O-");
        ArrayAdapter ad=new ArrayAdapter(getApplicationContext(),R.layout.selected,list);
        ad.setDropDownViewResource(R.layout.dropdown);
        blood_group.setAdapter(ad);
        blood_group.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                a=list.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w("tag", "Fetching FCM registration token failed", task.getException());
                            return;
                        }
                        token = task.getResult();

                    }
                });
        request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String d1,d2,d3,d4,d5;
                d1=name.getText().toString();
                d2=unit.getText().toString();
                d3=contact.getText().toString();
                d4=reason.getText().toString();
                d5=age.getText().toString();

                if(d1.equals("") || d2.equals("") || d3.equals("") || d4.equals("")||d5.equals(""))
                {
                    Toast.makeText(Need_blood.this, "plz fill information", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Calendar calendar=Calendar.getInstance();
                    SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd-MM-yyyy HH:mm");
                    String date=simpleDateFormat.format(calendar.getTime());
                    FirebaseDatabase db=FirebaseDatabase.getInstance();
                    DatabaseReference root=db.getReference().child("patient");
                    HashMap<String,String> patient=new HashMap<>();
                    patient.put("p_name",d1);
                    patient.put("p_unit",d2);
                    patient.put("p_contact",d3);
                    patient.put("p_reason",d4);
                    patient.put("p_bloodgroup",a);
                    patient.put("p_date",date);
                    patient.put("p_age",d5);
                    patient.put("p_token",token);
                    root.child(d3).setValue(patient);
                    AlertDialog dialog=new AlertDialog.Builder(Need_blood.this).create();
                    dialog.setTitle("Successfuly");
                    dialog.setIcon(R.drawable.ic_baseline_done_24);
                    dialog.setMessage("send your request");
                    dialog.setButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent i2=new Intent(Need_blood.this,MainActivity.class);
                            startActivity(i2);
                            finish();
                        }
                    });
                    dialog.show();

                }
            }
        });
    }
}