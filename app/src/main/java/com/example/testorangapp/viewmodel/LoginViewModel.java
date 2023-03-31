package com.example.testorangapp.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.testorangapp.model.FirebaseRepository;
import com.example.testorangapp.model.UserAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.database.ValueEventListener;

public class LoginViewModel extends ViewModel {

    private final FirebaseRepository mRepository;
    private MutableLiveData<UserAccount> UserList = new MutableLiveData<>();

    public LoginViewModel() {
        mRepository = new FirebaseRepository();
    }

    public void signUp(String email, String password){
        mRepository.signUp(email, password);
    }

    public void logIn(String email, String password, OnCompleteListener<AuthResult> listener) {
        mRepository.logIn(email, password, listener);
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
