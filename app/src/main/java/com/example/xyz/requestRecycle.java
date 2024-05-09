package com.example.xyz;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class requestRecycle extends RecyclerView.Adapter<requestRecycle.viewHolder> {

    Context context;
    ArrayList<Donnar> list;
    public requestRecycle(@NonNull Context context,ArrayList<Donnar> list) {
        this.context = context;
        this.list=list;
    }
    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.requestshow,parent,false);
        viewHolder vh=new viewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull requestRecycle.viewHolder holder, int position) {
        holder.n.setText(list.get(position).getName());
        holder.ad.setText(list.get(position).getDistrict());
        holder.date.setText(list.get(position).getDatetime());
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(context,donordetails.class);
                i.putExtra("name",list.get(position).getName());
                i.putExtra("email",list.get(position).getEmail());
                i.putExtra("contact",list.get(position).getContact());
                i.putExtra("bloodgroup",list.get(position).getBloodgroup());
                i.putExtra("address",list.get(position).getDistrict());
                i.putExtra("healthissue",list.get(position).getHealthissue());
                i.putExtra("age",list.get(position).getAge());
                i.putExtra("datetime",list.get(position).getDatetime());
                i.putExtra("token",list.get(position).getToken());
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        TextView n,ad,date,view;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            n=itemView.findViewById(R.id.requestname);
            ad=itemView.findViewById(R.id.requestadd);
            date=itemView.findViewById(R.id.requestdate);
            view=itemView.findViewById(R.id.view);
        }
    }
}
