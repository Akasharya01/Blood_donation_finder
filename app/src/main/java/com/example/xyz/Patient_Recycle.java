package com.example.xyz;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Patient_Recycle extends RecyclerView.Adapter<Patient_Recycle.viewHolder>{
    static final  int REQUEST_CALL=1;
    Context context;
    ArrayList<Donnar> list;
    String token;
    public Patient_Recycle(@NonNull Context context,ArrayList<Donnar> list){
        this.context = context;
        this.list=list;
    }


    @NonNull
    @Override
    public Patient_Recycle.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.list,parent,false);
        Patient_Recycle.viewHolder vh=new Patient_Recycle.viewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull Patient_Recycle.viewHolder holder, int position) {
        holder.gp.setText(list.get(position).getP_bloodgroup());
        holder.name.setText(list.get(position).getP_name());
        holder.age.setText(list.get(position).getP_age());
        holder.contact.setText(list.get(position).getP_contact());
        holder.blood.setText(list.get(position).getP_bloodgroup());
        holder.reason.setText(list.get(position).getP_reason());
        holder.unit.setText(list.get(position).getP_unit());
        holder.date.setText(list.get(position).getP_date());
        holder.call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String contact=holder.contact.getText().toString();
                if(contact.trim().length()>0){
                    if(ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
                        ActivityCompat.requestPermissions((Activity) context,new String[]{Manifest.permission.CALL_PHONE},REQUEST_CALL);

                    }else{
                        String dial="tel:"+contact;
                        context.startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                    }
                }
            }
        });
        token=list.get(position).getP_token();
        holder.accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                donordetails.NotificationSender.sendNotification(context, "Your Need blood Request has been accepted", "Congratulation",token);
            }
        });
        holder.reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                donordetails.NotificationSender.sendNotification(context, "Your Need blood Request has been Rejected", "Sorry",token);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        TextView name,age,contact,blood,reason,unit,date,gp,accept,reject;
        ImageView call;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.name);
            age=itemView.findViewById(R.id.age);
            contact=itemView.findViewById(R.id.contact);
            blood=itemView.findViewById(R.id.blood);
            reason=itemView.findViewById(R.id.reason);
            unit=itemView.findViewById(R.id.unit);
            date=itemView.findViewById(R.id.date);
            gp=itemView.findViewById(R.id.gp);
            accept=itemView.findViewById(R.id.accept);
            reject=itemView.findViewById(R.id.reject);
            call=itemView.findViewById(R.id.call);
        }
    }


    public static class NotificationSender {

        private static final String FCM_API = "https://fcm.googleapis.com/fcm/send";
        private static final String SERVER_KEY = "AAAAT8nRlcg:APA91bEbUChjjNTobsMqJa-FT4wrEFseAsrJvM6XbbBD1h0jvEaWH9KLP-Dsgk2P8BXDYG6clIfF46NBmuTBwmc7sz9gxr6yU_JumpU2qQ0cWIv-zRJrqkyljk4kxSDSrDRAkKEZsu64";
        private static final String CONTENT_TYPE = "application/json";
        private static final String TOPIC = "YOUR_TOPIC";
        //private static final String TOKEN = "fkLn4lCuS3q1rS_OPQGDxq:APA91bGWmNwLlTfPW9ly7AfQ2CZ3aJ6VF6_KXhHlFvNaiy9iZV1mMEUVKrniLsY0vuaU0MVN1hbXWr9RhcaUXYbZWChZWtpfGeNPO7q7bkmmjOZZ9_B-DA1Yrxp3yA4_U77DCJG2q5ht";

        public static void sendNotification(Context context, String title, String body, String token) {

            Log.e("TAG", "sendNotification: "+"calll---" );
            RequestQueue requestQueue = Volley.newRequestQueue(context);
            JSONObject notificationObject = new JSONObject();
            JSONObject notificationBody = new JSONObject();
            try {
                notificationBody.put("title", title);
                notificationBody.put("body", body);

                notificationObject.put("to",token);
                notificationObject.put("notification", notificationBody);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            Log.e("TAG", "sendNotification:-- "+notificationObject );

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, FCM_API, notificationObject,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            // Handle response if needed

                            Log.e("TAG", "onResponse:--- "+response );

                            Log.e("TAG", "sendNotification: "+"done---" );                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // Handle error
                        }
                    }) {
                @Override
                public Map<String, String> getHeaders() {
                    Map<String, String> headers = new HashMap<>();
                    headers.put("Authorization", "key=" + SERVER_KEY);
                    headers.put("Content-Type", CONTENT_TYPE);
                    return headers;
                }
            };

            requestQueue.add(jsonObjectRequest);
        }
    }
}
