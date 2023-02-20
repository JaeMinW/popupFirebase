package com.example.testorangapp.function;

import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;

public class Authentication extends ViewModel {

    private String url = "https://www.example.com/finishSignUp?cartId=1234";
    private final FirebaseAuth mAuth;
    public Authentication(){
        this.mAuth = FirebaseAuth.getInstance();

    }

}


