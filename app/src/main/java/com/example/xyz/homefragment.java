package com.example.xyz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class homefragment extends Fragment {
    Activity mainActivity;
    ArrayList<Integer> al=new ArrayList<>();
    Handler h=new Handler();
    CardView c1,c2,c3;
    ViewPager2 vp;

    public homefragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_homefragment, container, false);
        vp=v.findViewById(R.id.vp);
        mainActivity=getActivity();
        c1=v.findViewById(R.id.c1);
        c2=v.findViewById(R.id.c3);
        c3=v.findViewById(R.id.c4);
        internet in=new internet();
        c1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(in.isconnected(mainActivity)){
                Intent i2=new Intent(mainActivity,application.class);
                startActivity(i2);}
                else {
                    Toast.makeText(mainActivity, "No Internet Connection", Toast.LENGTH_SHORT).show();
                }
            }
        });
        c2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(in.isconnected(mainActivity)){
                    Intent i2=new Intent(mainActivity,bloodavailable.class);
                    startActivity(i2);
                    }
                else {
                    Toast.makeText(mainActivity, "No Internet Connection", Toast.LENGTH_SHORT).show();
                }
            }
        });
        c3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(in.isconnected(mainActivity)){
                    startActivity(new Intent(mainActivity,Need_blood.class));
                }
                else {
                    Toast.makeText(mainActivity, "No Internet Connection", Toast.LENGTH_SHORT).show();
                }
            }
        });

        al.add(R.drawable.b1);
        al.add(R.drawable.b2);
        al.add(R.drawable.b1);
        al.add(R.drawable.b3);
        vp.setAdapter(new sliderAdapter(al,vp));
        vp.setClipToPadding(false);
        vp.setClipChildren(false);
        vp.setOffscreenPageLimit(3);
        vp.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);
        CompositePageTransformer cp=new CompositePageTransformer();
        cp.addTransformer(new MarginPageTransformer(10));
        cp.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float r=1-Math.abs(position);
                page.setScaleY(0.75f+r+0.05f);
            }
        });
        vp.setPageTransformer(cp);
        vp.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                h.removeCallbacks(run);
                h.postDelayed(run,2500);
            }
        });
        return v;
    }
    private Runnable run=new Runnable() {
        @Override
        public void run() {
            vp.setCurrentItem(vp.getCurrentItem()+1);
        }
    };
}