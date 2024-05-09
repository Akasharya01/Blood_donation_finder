package com.example.xyz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class application extends AppCompatActivity {
    Spinner blood_group,health_issue;
    EditText name,email,contact,district,age;
    Button request;
    String a,b,token;
    ArrayList<String> list=new ArrayList<>();
    ArrayList<String> list2=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_application);
        blood_group=findViewById(R.id.blood_group);
        health_issue=findViewById(R.id.health_issue);
        name=findViewById(R.id.name);
        email=findViewById(R.id.email);
        contact=findViewById(R.id.contact);
        district=findViewById(R.id.district);
        request=findViewById(R.id.request);
        age=findViewById(R.id.age);
        list.add("--select--");list.add("A+");list.add("O+");list.add("B+");list.add("AB+");list.add("AB-");list.add("A-");list.add("B-");list.add("O-");
        ArrayAdapter ad=new ArrayAdapter(getApplicationContext(),R.layout.selected,list);
        ad.setDropDownViewResource(R.layout.dropdown);
        blood_group.setAdapter(ad);
        list2.add("--select--");
        list2.add("Yes");
        list2.add("No");
        ArrayAdapter ad1=new ArrayAdapter(getApplicationContext(),R.layout.selected,list2);
        ad.setDropDownViewResource(R.layout.dropdown);
        health_issue.setAdapter(ad1);

        blood_group.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                a=list.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        health_issue.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                b=list2.get(i);
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
                        // Get new FCM registration token
                         token = task.getResult();
                        //Toast.makeText(MainActivity.this, token, Toast.LENGTH_SHORT).show();
                    }
                });
        request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String d1,d2,d3,d4,d5;
                d1=name.getText().toString();
                d2=email.getText().toString();
                d3=contact.getText().toString();
                d4=district.getText().toString();
                d5=age.getText().toString();
                if(d1.equals("") || d2.equals("") || d3.equals("") || d4.equals("")||d5.equals(""))
                {
                    Toast.makeText(application.this, "plz fill information", Toast.LENGTH_SHORT).show();
                }

                else
                {
                    if(validateinfo(d1,d2,d3,d4)) {
                        Calendar calendar = Calendar.getInstance();
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
                        String date = simpleDateFormat.format(calendar.getTime());
                        FirebaseDatabase db = FirebaseDatabase.getInstance();
                        DatabaseReference root = db.getReference().child("donors");
                        HashMap<String, String> donor = new HashMap<>();
                        donor.put("name", d1);
                        donor.put("email", d2);
                        donor.put("contact", d3);
                        donor.put("district", d4);
                        donor.put("healthissue", b);
                        donor.put("bloodgroup", a);
                        donor.put("datetime", date);
                        donor.put("age", d5);
                        donor.put("token",token);
                        root.child(d3).setValue(donor);
                        AlertDialog dialog = new AlertDialog.Builder(application.this).create();
                        dialog.setTitle("Successfuly");
                        dialog.setIcon(R.drawable.ic_baseline_done_24);
                        dialog.setMessage("send your request");
                        dialog.setButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent i2 = new Intent(application.this, MainActivity.class);
                                startActivity(i2);
                                finish();
                            }
                        });
                        dialog.show();
                    }
                }
            }
        });
    }

    private boolean validateinfo(String d1, String d2, String d3, String d4) {
        if(!d1.matches("[a-zA-Z ]+")){
            name.requestFocus();
            name.setError("Enter only alphabet character");
            return false;
        }
        else if(!d2.matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")){
            email.requestFocus();
            email.setError("Enter valid email");
            return false;
        }
        else if(!d3.matches("[6-9][0-9]{9}")){
            contact.requestFocus();
            contact.setError("Enter valid number");
            return false;
        }
        else{
            return true;
        }
    }

}