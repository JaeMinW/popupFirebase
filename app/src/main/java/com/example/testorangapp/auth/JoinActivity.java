package com.example.testorangapp.auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.testorangapp.FCM.MyFirebaseMessagingService;
import com.example.testorangapp.R;
import com.example.testorangapp.main.MainActivity;
import com.example.testorangapp.sign.UserTable;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;

public class JoinActivity extends AppCompatActivity {

    EditText et_userEmail, et_userPhone, et_userBirth, et_userPwd, et_userCheckPwd;
    Button btn_reg;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        mAuth = FirebaseAuth.getInstance();

        et_userEmail = findViewById(R.id.et_userEmail);
        et_userPhone = findViewById(R.id.et_userPhone);
        et_userPwd = findViewById(R.id.et_userPwd);
        et_userCheckPwd = findViewById(R.id.et_userCheckPwd);
        et_userBirth = findViewById(R.id.et_userBirth);

        btn_reg = findViewById(R.id.btn_register);
        btn_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean isGoToJoin = true;

                String email = et_userEmail.getText().toString();
                String phone = et_userPhone.getText().toString();
                String userBirth = et_userBirth.getText().toString();
                String password = et_userPwd.getText().toString();
                String password2 = et_userCheckPwd.getText().toString();

                if(email.isEmpty()){
                    Toast.makeText(JoinActivity.this, "이메일을 입력하세요", Toast.LENGTH_LONG).show();
                    isGoToJoin = false;
                }

                if(phone.isEmpty()){
                    Toast.makeText(JoinActivity.this, "전화번호를 입력하세요", Toast.LENGTH_LONG).show();
                    isGoToJoin = false;
                }

                if(userBirth.isEmpty()){
                    Toast.makeText(JoinActivity.this, "생년월일을 입력하세요", Toast.LENGTH_LONG).show();
                    isGoToJoin = false;
                }

                if(password.isEmpty()){
                    Toast.makeText(JoinActivity.this, "비밀번호를 입력하세요", Toast.LENGTH_LONG).show();
                    isGoToJoin = false;
                }

                if(password2.isEmpty()){
                    Toast.makeText(JoinActivity.this, "비밀번호 확인 부분을 입력하세요", Toast.LENGTH_LONG).show();
                    isGoToJoin = false;
                }

                if(!password.equals(password2)) {
                    Toast.makeText(JoinActivity.this, "비밀번호가 일치하지 않습니다", Toast.LENGTH_LONG).show();
                    isGoToJoin = false;
                }

                if(password.length() < 6) {
                    Toast.makeText(JoinActivity.this, "6자리 이상의 비밀번호를 입력하세요", Toast.LENGTH_LONG).show();
                    isGoToJoin = false;
                }

                if(isGoToJoin){
                    mAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(JoinActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(JoinActivity.this, "회원가입 성공", Toast.LENGTH_LONG).show();

                                        final String UID = mAuth.getCurrentUser().getUid();
                                        final FirebaseDatabase database = FirebaseDatabase.getInstance();
                                        final DatabaseReference myRef = database.getReference("users").child(UID);

                                        FirebaseMessaging.getInstance().getToken()
                                                .addOnCompleteListener(new OnCompleteListener<String>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<String> task) {
                                                        if(task.isSuccessful()){
                                                            String token = task.getResult();

                                                            UserTable userTable = new UserTable();
                                                            userTable.setUuid(UID);
                                                            userTable.setEmail(email);
                                                            userTable.setPwd(password);
                                                            userTable.setBirth(userBirth);
                                                            userTable.setPh(phone);
                                                            userTable.setIdToken(token);

                                                            //토큰값, 이름, 성별, 지역, 생성일 추가하기

                                                            myRef.setValue(userTable);
                                                            Toast.makeText(JoinActivity.this, "회원가입 성공", Toast.LENGTH_LONG).show();

                                                            Intent intent = new Intent(JoinActivity.this, MainActivity.class);
                                                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                            startActivity(intent);
                                                        }
                                                        else{
                                                            Toast.makeText(JoinActivity.this, "토큰 값 가져오기 실패", Toast.LENGTH_LONG).show();
                                                        }
                                                    }
                                                });

                                    } else {
                                        //실패 원인 확인하기
                                        Log.e("JoinActivity", "회원가입 실패: " + task.getException(), task.getException());
                                        //중복된 아이디 입력시 메세지 출력


                                        Toast.makeText(JoinActivity.this, "실패", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                }
            }
        });
    }
}