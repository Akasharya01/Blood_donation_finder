package com.example.xyz;

import android.content.Context;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Vibrator;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) {
        super.onMessageReceived(message);
        Uri notification= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Ringtone r=RingtoneManager.getRingtone(getApplicationContext(),notification);
        r.play();
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.P){
            r.setLooping(false);
        }
        Vibrator v=(Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        long[] pattern={100,300,300,300};
        v.vibrate(pattern,-1);
        getFirebaseMessage(message.getNotification().getTitle(),message.getNotification().getBody());
    }
    public void getFirebaseMessage(String title,String msg)
    {
        NotificationCompat.Builder builder=new NotificationCompat.Builder(this,"mychannel")
                .setSmallIcon(R.drawable.blood)
                .setContentText(title)
                .setContentText(msg)
                .setAutoCancel(true);
        NotificationManagerCompat manager=NotificationManagerCompat.from(this);
        manager.notify(101,builder.build());
    }
}
