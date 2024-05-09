package com.example.xyz;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class profilefragement extends Fragment {
TextView pname,pemail,logout;
public profilefragement() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_profilefragement, container, false);
        pname=v.findViewById(R.id.pname);
        pemail=v.findViewById(R.id.pemail);
        logout=v.findViewById(R.id.logout);
        SharedPreferences sp=this.getActivity().getSharedPreferences("first",MODE_PRIVATE);
        String n=sp.getString("name","");
        String e=sp.getString("email","");
        pemail.setText(e);
        pname.setText(n);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(new internet().isconnected(getActivity())){
                SharedPreferences.Editor editor=sp.edit();
                editor.remove("password");
                editor.remove("name");
                editor.remove("email");
                editor.commit();
                startActivity(new Intent(getActivity(),loginpage.class));
                getActivity().finish();}
                else {
                    Toast.makeText(getActivity(), "No Internet Connection", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return v;
    }
}