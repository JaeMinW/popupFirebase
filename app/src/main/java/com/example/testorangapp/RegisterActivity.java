package com.example.testorangapp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.testorangapp.databinding.ActivityRegisterBinding;
import com.example.testorangapp.function.Authentication;
import com.example.testorangapp.function.Message;
import com.example.testorangapp.model.UserModel;
import com.example.testorangapp.sign.LoginViewModel;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class RegisterActivity extends AppCompatActivity {
    private LoginViewModel viewModel;
    ActivityRegisterBinding activityRegisterBinding;
    private boolean checkPhone = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityRegisterBinding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(activityRegisterBinding.getRoot());


        viewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        RegisterUserButtonClickListener click = new RegisterUserButtonClickListener();
        activityRegisterBinding.btnRegister.setOnClickListener(click);
        EmailAuthButtonClickListener emailBtn = new EmailAuthButtonClickListener();
        activityRegisterBinding.btnEmailCheck.setOnClickListener(emailBtn);
        SmsAuthButtonClickListener smsBtn = new SmsAuthButtonClickListener();
        activityRegisterBinding.btnPhoneCheck.setOnClickListener(smsBtn);

    }
    class EmailAuthButtonClickListener implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            Authentication auth = new Authentication();
            //if(valid is true){}
            String email =  activityRegisterBinding.etUserEmail.getText().toString().trim();
//            Log.d("EmailLog2",""+auth.AuthEmail(email));
//            auth.AuthEmail(email);
        }
    }
    class SmsAuthButtonClickListener implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            String phoneNum = activityRegisterBinding.etUserPhone.getText().toString().trim();
            //if(valid is true){}
            sendAuthPhoneNum(phoneNum);
        }
    }
    //이것만 RegisterActivity에 있음 되겠다.
    public boolean sendAuthPhoneNum(String phoneNum){
        String checkPhoneNum = "+82"+phoneNum;
        Log.d("checkPhoneNum ", phoneNum);
        UserModel UserDB = new UserModel();
        Log.d("GETFIREBASAUTH",""+UserDB.getmFirebaseAuth().getCurrentUser().toString());
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(UserDB.getmFirebaseAuth())  //mAuth가 null이라 안됨 ,,,
                        .setPhoneNumber(checkPhoneNum)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)
                        .setCallbacks(mCallback)
                        .build();
        Log.d("PAGHODASF",""+options);
        PhoneAuthProvider.verifyPhoneNumber(options);
        return true;
    }
    // callback method is called on Phone auth provider.
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks
            // initializing our callbacks for on
            // verification callback method.
            mCallback = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        //1. 전화번호는 확인 됐으나, 인증코드를 입력해야 하는 상태
        //2. 대기 신호 UI만들어야 함.

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            String verificationId = s;
            Log.d("verfies",""+s);
            if(verificationId!=null){
                activityRegisterBinding.btnPhoneCheck.setText("인증완료");
                activityRegisterBinding.btnPhoneCheck.setEnabled(false);
                checkPhone = true;
            }
        }
        //문자는 정상적으로 날라갔는데 밑에 메서드가 실행이 안 되는 이유??? 는 뭘까
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            final String code = phoneAuthCredential.getSmsCode();
            Log.d("CODEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE", code+" ");
            if (code != null) {
               activityRegisterBinding.etUserCheckPhoneNum.setText(code);
            }
        }
        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            Log.d("FIREBASEException : e",""+e);
        }
        @Override
        public void onCodeAutoRetrievalTimeOut(@NonNull String s) {
            super.onCodeAutoRetrievalTimeOut(s);
        }
    };

    class RegisterUserButtonClickListener implements View
            .OnClickListener{
        @Override
        public void onClick(View view) {
            ///인터넷 연결 안되어 있을때 실패 메세지(함수로 만들것)
            String userEmail = activityRegisterBinding.etUserEmail.getText().toString().trim();
            String userPwd = activityRegisterBinding.etUserPwd.getText().toString().trim();
            UserModel UserDB = new UserModel();
            Message message = new Message();
            //FirebaseRepository
            //userEmail
            //userPwd;

            //mvvm
            viewModel.signUp(userEmail, userPwd);

//            FirebaseRepository firebaseRepository = new FirebaseRepository();
//            firebaseRepository.createUser(userEmail, userPwd, getApplicationContext());

        }
    }
}