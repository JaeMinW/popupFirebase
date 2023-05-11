package com.example.testorangapp.FCM;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.testorangapp.R;
import com.example.testorangapp.sign.UserTable;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SendingActivity extends AppCompatActivity {

    EditText editText;
    TextView textView;

    private DatabaseReference mDatabase;
    //static String regId = "czJ2CyspSaS4rFs-vRpbKa:APA91bFdWazB4sGMye7IQdyw7C0fJ9LzPh2eU6l6-76MrW0kYX_C2fHkg5hevua_eggd6SnomnlSyoPZhqj6Vy77RLiChQH4i8oXakPzsNHzu5s6UpX5qP9JwGzCL_Ey8UHUG4hBZ2bR";

    static RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sending);

        editText = findViewById(R.id.editText);
        textView = findViewById(R.id.textView);

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String input = editText.getText().toString();

                //DB에서 Token값 가져오기
                DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("users");
                ValueEventListener userListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                            UserTable user = userSnapshot.getValue(UserTable.class);
                            String token = user.getIdToken();
                            Log.d("Token", "onDataChange: " + token);
                            // 토큰 값을 이용해 원하는 동작을 수행
                            send(input, token);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        // 오류 처리
                    }
                };
                usersRef.addValueEventListener(userListener);


            }
        });

        if(requestQueue == null){
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }
    }

    //Volley : RequestQueue 객체에 요청객체를 만들어 추가하면 자동으로 메세지를 전송하는 방식
    public void send(String input, String token){

        //Log.d("RegId", regId);
        //전송 정보를 담아둘 JSONObject 객체 생성
        JSONObject requestData = new JSONObject();

        try {
            requestData.put("priority", "high");//옵션 추가

            //전송할 데이터 추가
            //추가 시 또 다른 JSONObject 객체를 만든 후 그 안에 추가
            JSONObject dataObj = new JSONObject();
            dataObj.put("contents", input);

            requestData.put("data", dataObj);



            //푸시 메세지를 수신할 단말의 등록 ID를 JSONArray에 추가한 후 requestData 객체에 추가
            //받는 단말의 등록 id를 JSONArray 객체에 추가한 후 registration_ids라는 이름으료 요청 객체에 추가
            JSONArray idArray = new JSONArray();
            idArray.put(0, token);
            requestData.put("registration_ids", idArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //푸시 전송
        //JSONObject 타입의 요청 객체, 리스너 전달
        sendData(requestData, new SendResponseListener(){
            @Override
            public void onRequestStarted() {
                println("onRequestStarted() 호출됨.");
            }

            @Override
            public void onRequestCompleted() {
                println("onRequestCompleted() 호출됨.");
            }

            @Override
            public void onRequestWithError(VolleyError error) {
                println("onRequestWithError() 호출됨.");
            }
        });
    }

    public interface SendResponseListener{
        public void onRequestStarted();
        public void onRequestCompleted();
        public void onRequestWithError(VolleyError error);
    }

    public void sendData(JSONObject requestData, final SendResponseListener listener) {
        JsonObjectRequest request = new JsonObjectRequest(//Volley 요청객체를 만들고 요청을 위한 데이터 설정
                Request.Method.POST,//요청방식(Post)
                "https://fcm.googleapis.com/fcm/send",//클라우드 서버의 요청 주소
                requestData,//요청 데이터가 들어있는 객체
                new Response.Listener<JSONObject>() {//성공 응답을 받았을 때 호출되는 리스너 객체
                    @Override
                    public void onResponse(JSONObject response) {
                        listener.onRequestCompleted();
                    }
                }, new Response.ErrorListener() {//오류 응답을 받았을 때 호출되는 리스너 객체
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onRequestWithError(error);
            }
        }
        ) {
            //요청을 위한 파라미터 설정
            //요청 파라미터를 설정하기 위한 메서드
            //비어있는 HashMap 객체만 반환
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                return params;
            }

            //요청을 위한 헤더 설정
            //getHeaders() : HashMap 객체에 값을 넣어 반환하면 HTTP 요청 시 헤더가 설정된다
            //Authorization이라는 키 사용
            //개발자 콘솔 페이지 > 프로젝트 설정 > 클라우드 메세징 > 서버 키
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization",
                        "key=AAAAo2rjRJY:APA91bGB8T2utpvUBaOFvu7p5aXu1W0jLVEQTusGmwsPGIKIqAf2xW6jte-Bf250pCVZgqzjljBKaPtWGr6sRhROrvnCcY3sKzRBASaFgdPSmvT0X0x8vbZ6VQwu2EgaxfbPk41rkMOB");
                return headers;
            }

            @Override
            public String getBodyContentType() {
                return "application/json";
            }
        };

        request.setShouldCache(false);
        listener.onRequestStarted();
        requestQueue.add(request);
    }

    public void println(String data){
        textView.append(data + "\n");
    }
}


