package com.example.xyz;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class registrationpage extends AppCompatActivity {
EditText uname,email,password,repassword;
Button registration;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrationpage);
        uname=findViewById(R.id.uname);
        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        repassword=findViewById(R.id.repassword);
        registration=findViewById(R.id.registration);
        internet in=new internet();
        FirebaseDatabase db=FirebaseDatabase.getInstance();
        DatabaseReference root=db.getReference().child("users");
        registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (in.isconnected(registrationpage.this)) {
                    String n = uname.getText().toString();
                    String m = email.getText().toString();
                    String p = password.getText().toString();
                    String q = repassword.getText().toString();
                    if(validateinfo(n,m,p)) {
                        if (p.equals(q)) {
                            HashMap<String, String> usermap = new HashMap<>();
                            usermap.put("username", n);
                            usermap.put("email", m);
                            usermap.put("password", q);
                            root.child(p).setValue(usermap);
                            Dialog dialog = new Dialog(registrationpage.this);
                            dialog.setContentView(R.layout.popup);
                            dialog.show();
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Intent i = new Intent(registrationpage.this, loginpage.class);
                                    startActivity(i);
                                    finish();
                                }
                            }, 3000);

                        } else {
                            repassword.setError("Enter same password");
                            repassword.requestFocus();
                        }
                    }
                }
                else {
                    Toast.makeText(registrationpage.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private boolean validateinfo(String d1, String d2, String d3) {
        if(!d1.matches("[a-zA-Z]+")){
            uname.requestFocus();
            uname.setError("Enter only alphabet character");
            return false;
        }
        else if(!d2.matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")){
            email.requestFocus();
            email.setError("Enter valid email");
            return false;
        }
        else if(d3.length()<6){
            password.requestFocus();
            password.setError("Minimum 6 character Required");
            return false;
        }
        else{
            return true;
        }
    }
}