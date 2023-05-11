package com.example.testorangapp.sign;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.testorangapp.FCM.MyFirebaseMessagingService;
import com.example.testorangapp.model.FirebaseRepository;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;

public class LoginViewModel extends ViewModel {

    private final FirebaseRepository mRepository;
    private MutableLiveData<UserTable> UserList = new MutableLiveData<>();
    String idToken;

    public LoginViewModel() {
        mRepository = new FirebaseRepository();
    }

    public void signUp(String email, String password){
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if(!task.isSuccessful()){
                            Log.w("MAIN", "토큰 가져오는데 실패함", task.getException());
                            return;
                        }
                        idToken = task.getResult().toString();
                        Log.d("Token", idToken);
                    }
                });
        mRepository.signUp(email,password,"inho","0105555555","19998123","남",idToken,true);

    }



    public void logIn(String email, String password) {
        mRepository.logIn(email, password);
    }

    public void logOut() {
        mRepository.logOut();
    }

    public void updateUser(String userId, String username) {
        mRepository.updateUser(userId, username);

    }

    public void getUser(String userId, ValueEventListener listener) {
        mRepository.getUser(userId, listener);
    }

}
