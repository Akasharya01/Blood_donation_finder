package com.example.xyz;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthCredential;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.HashMap;

public class loginpage extends AppCompatActivity {
    TextInputEditText email,password;
    Button btn;
    TextView reg;
    FirebaseAuth auth;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginpage);
        btn = findViewById(R.id.btn);
        reg = findViewById(R.id.reg);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        reference = FirebaseDatabase.getInstance().getReference("users");
        internet in = new internet();
        checkdata();
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (in.isconnected(loginpage.this)) {
                    String a = email.getText().toString().trim();
                    String b = password.getText().toString().trim();
                    if (a.equals("admin@gmail.com") && b.equals("0000")) {
                        Intent i = new Intent(loginpage.this, AdminHomeActivity.class);
                        startActivity(i);
                        finish();
                    }
                    Query qry = reference.orderByChild("password").equalTo(b);
                    qry.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                String ps = snapshot.child(b).child("email").getValue(String.class);
                                if (ps.equals(a)) {
                                    String n = snapshot.child(b).child("username").getValue(String.class);
                                    SharedPreferences sp = getSharedPreferences("first", MODE_PRIVATE);
                                    SharedPreferences.Editor ed = sp.edit();
                                    ed.putString("name", n);
                                    ed.putString("password", b);
                                    ed.putString("email", a);
                                    ed.apply();
                                    Intent i = new Intent(loginpage.this, MainActivity.class);
                                    Dialog dialog = new Dialog(loginpage.this);
                                    dialog.setContentView(R.layout.popup);
                                    dialog.show();
                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            startActivity(i);
                                            dialog.dismiss();
                                            finish();

                                        }
                                    }, 3000);

                                } else {
                                    email.setError("No such user exist");
                                    email.requestFocus();

                                }
                            } else {
                                password.setError("Wrong password");
                                password.requestFocus();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                        }
                    });
                } else {
                    Toast.makeText(loginpage.this, "No Internet connection", Toast.LENGTH_SHORT).show();
                }
            }
        });
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(loginpage.this, registrationpage.class);
                startActivity(i);
            }
        });
    }
    private void checkdata() {
        SharedPreferences sp=getSharedPreferences("first",MODE_PRIVATE);
        if(sp.contains("email")) {
            String a = sp.getString("email", "");
            Intent i = new Intent(loginpage.this,MainActivity.class);
            startActivity(i);
            finish();
        }
    }
}