package com.example.xyz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class donordetails extends AppCompatActivity {
    static final  int REQUEST_CALL=1;
TextView name,email,contact,health,blood,address,age,date,accept,reject;
ImageView call;
String token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donordetails);
        name=findViewById(R.id.donor_name);
        email=findViewById(R.id.donor_email);
        contact=findViewById(R.id.donor_contact);
        health=findViewById(R.id.donor_health);
        blood=findViewById(R.id.donor_blood);
        address=findViewById(R.id.donor_address);
        age=findViewById(R.id.donor_age);
        date=findViewById(R.id.donor_date);
        call=findViewById(R.id.call);
        accept=findViewById(R.id.accept);
        reject=findViewById(R.id.reject);
        name.setText(getIntent().getStringExtra("name"));
        email.setText(getIntent().getStringExtra("email"));
        contact.setText(getIntent().getStringExtra("contact"));
        health.setText(getIntent().getStringExtra("healthissue"));
        blood.setText(getIntent().getStringExtra("bloodgroup"));
        address.setText(getIntent().getStringExtra("address"));
        age.setText(getIntent().getStringExtra("age"));
        date.setText(getIntent().getStringExtra("datetime"));
        token=getIntent().getStringExtra("token");
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NotificationSender.sendNotification(getApplicationContext(), "Your Donation Request has been accepted", "Congratulation",token);
            }
        });
        reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NotificationSender.sendNotification(getApplicationContext(), "Your Donation Request has been Rejected", "Sorry",token);
            }
        });
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callButton();
            }
        });

    }

    private void callButton() {
        String number=contact.getText().toString();
        if(number.trim().length()>0){
            if(ContextCompat.checkSelfPermission(donordetails.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(donordetails.this,new String[]{Manifest.permission.CALL_PHONE},REQUEST_CALL);

            }else{
                String dial="tel:"+number;
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
            }
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