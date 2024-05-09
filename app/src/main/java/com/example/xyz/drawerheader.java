package com.example.xyz;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class drawerheader extends AppCompatActivity {
TextView names,email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawerheader);
        names=findViewById(R.id.profile_name);
        email=findViewById(R.id.profile_email);
        names.setText("hii");
    }
}