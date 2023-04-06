package com.example.testorangapp.model;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.testorangapp.post.PostTable;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class FirebaseRepository {
    private final FirebaseAuth mAuth;
    private final DatabaseReference mDatabase;

    public FirebaseRepository() {
        this.mAuth = FirebaseAuth.getInstance();
        this.mDatabase = FirebaseDatabase.getInstance().getReference("Post");
        Log.d("ADSF","ADSF");
    }
    public FirebaseAuth GetAuth(){
        return mAuth;
    }
    public LiveData<List<PostTable>> getModelList() {
        MutableLiveData<List<PostTable>> modelList = new MutableLiveData<>();
        mDatabase.child("Category").child("1").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<PostTable> models = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    PostTable model = dataSnapshot.getValue(PostTable.class);
                    models.add(model);
                    Log.d("ADSF","::"+models.size()+""+models.get(modelList.getValue().indexOf(model)));
                }
                modelList.setValue(models);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("ADSF","ADSF"+error);
            }
        });
        return modelList;
    }

    public boolean createSms(String checkPhoneNum) {

        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)  //mAuth가 null이라 안됨 ,,,
                        .setPhoneNumber(checkPhoneNum)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setCallbacks(mCallback)
                        .build();
        Log.d("PAGHODASF", "" + options);
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
            Log.d("verfies", "" + s);
            if (verificationId != null) {
//                activityRegisterBinding.btnPhoneCheck.setText("인증완료");
//                activityRegisterBinding.btnPhoneCheck.setEnabled(false);
//                checkPhone = true;
            }
        }

        //문자는 정상적으로 날라갔는데 밑에 메서드가 실행이 안 되는 이유??? 는 뭘까
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            final String code = phoneAuthCredential.getSmsCode();
            Log.d("CODEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE", code + " ");
            if (code != null) {
//                activityRegisterBinding.etUserCheckPhoneNum.setText(code);
            }
        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            Log.d("FIREBASEException : e", "" + e);
        }

        @Override
        public void onCodeAutoRetrievalTimeOut(@NonNull String s) {
            super.onCodeAutoRetrievalTimeOut(s);
        }

    };

    public void createUser(String email, String password, Context mContext) {
        mAuth.createUserWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Toast.makeText(mContext, "Success!!@!@!@!@!@", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(mContext, "EXENTEPAT::::+" + e, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void signUp(String email, String pwd, String name, String ph, String birth, String sex, boolean pushFlag) {


        mAuth.createUserWithEmailAndPassword(email,pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                //Set Data,in Firebase S
                //mDatabase.child("UserList").push().setValue()

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

    public void logIn(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                mAuth.createUserWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Log.d("ONSESSENSENSE",""+authResult);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
            }
        });
    }

    public void logOut() {
        mAuth.signOut();
    }

    public void updateUser(String userId, String username) {
        mDatabase.child("users").child(userId).child("username").setValue(username);
    }

    public void getUser(String userId, ValueEventListener listener) {
        mDatabase.child("users").child(userId).addListenerForSingleValueEvent(listener);
    }

}

