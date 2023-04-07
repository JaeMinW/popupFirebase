package com.example.testorangapp.sign;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.testorangapp.model.FirebaseRepository;
import com.google.firebase.database.ValueEventListener;

public class LoginViewModel extends ViewModel {

    private final FirebaseRepository mRepository;
    private MutableLiveData<UserTable> UserList = new MutableLiveData<>();

    public LoginViewModel() {
        mRepository = new FirebaseRepository();
    }

    public void signUp(String email, String password){
        mRepository.signUp(email,password,"inho","0105555555","19998123","남",true);

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
