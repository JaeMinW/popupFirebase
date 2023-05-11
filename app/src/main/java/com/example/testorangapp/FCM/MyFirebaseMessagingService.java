package com.example.testorangapp.FCM;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.example.testorangapp.R;
import com.example.testorangapp.main.MainActivity;
import com.google.firebase.FirebaseApp;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

//푸시메세지를 전달 받는 역할을 하는 클래스
//구글 클라우드 서버에서 보내오는 메세지는 이 클래스에서 받을 수 있음
//FirebaseMessagingService 상속
public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "FMS";
    private static String CHANNEL_ID = "channel";
    private static String CHANNEL_NAME = "channel";

    private String msg, title;

    public MyFirebaseMessagingService() {
    }

    //새 토큰을 확인했을때 호출되는 메서드
    //파라미터로 전달 받는 token은 이 앱의 등록 id
    //보내는 쪽에서 등록 id를 통해 메세지를 보낼 수 있다
    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);
        Log.e(TAG, "onNewToken 호출됨: " + token);
    }
    //푸시 메세지를 받았을 때 그 내용을 확
    //메세지가 도착하면 자동으로 호출되는 메서드인한 후 액티비티 쪽으로 보내는 메서드 호출
    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        Log.d(TAG,"onMessageReceived() 호출됨.");

        String from = remoteMessage.getFrom();//발신자 코드 확인
        //메시지를 전송할 때 넣었던 데이터 확인
        Map<String, String> data = remoteMessage.getData();
        //Map 객체의 contents키로 꺼내 발신 데이터 확인
        String contents = data.get("contents");
        Log.d(TAG, "from : " + from + ", contents : " + contents);

        //sendToActivity(getApplication(), from, contents);

        //메세지 받았을 때 알람 띄우기
        title = "Orang Project";
        msg = contents;


        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = null;
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            manager.createNotificationChannel(new NotificationChannel(
                    CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT
            ));

            builder = new NotificationCompat.Builder(this, CHANNEL_ID);
        }else{
            builder = new NotificationCompat.Builder(this);
        }

        //Intent intent = new Intent(this, MainActivity.class);
        //intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);

        //PendingIntent contentIntent = PendingIntent.getActivity(this, 0, new Intent(this, MainActivity.class),0);

        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 101, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        builder.setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(title)
                .setContentText(msg)
                .setAutoCancel(true)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setVibrate(new long[]{1,1000})
                .setContentIntent(pendingIntent);

        Notification noti = builder.build();
        manager.notify(1, noti);
        //builder.setContentIntent(pendingIntent);
    }

    //액티비티 쪽으로 데이터를 보내기 위해 인텐트 객체를 만들고 startActivity 메서드 호출
/*    private void sendToActivity(Context context, String from, String contents){
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra("from", from);
        intent.putExtra("contents", contents);

        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|
                Intent.FLAG_ACTIVITY_SINGLE_TOP|
                Intent.FLAG_ACTIVITY_CLEAR_TOP);

        context.startActivity(intent);
    }*/

}