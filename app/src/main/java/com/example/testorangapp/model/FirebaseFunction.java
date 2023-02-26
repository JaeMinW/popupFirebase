package com.example.testorangapp.model;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class FirebaseFunction {

    private DatabaseReference mDatabaseRef;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseStorage mStorage;
    private StorageReference storageRef;
    //유저가 정상적인 유저인지 먼저 확인한다.

    public FirebaseFunction(){
        //파이어베이스에 이미지 업로드 인스턴스와 참조생성
        this.mStorage = FirebaseStorage.getInstance();
        this.storageRef = mStorage.getReference();
        // Authentication, Database, Storage 들은 인스턴스들을 생성 및 초기화해줘야 한다.
//        mAuth = FirebaseAuth.getInstance();
//        mDatabase = FirebaseDatabase.getInstance();
//        mStorage = FirebaseStorage.getInstance();
        //파이어베이스 인증과 게시글 내용 등록 인스턴스 생성 .


        this.mFirebaseAuth = FirebaseAuth.getInstance();
        this.mDatabaseRef = FirebaseDatabase.getInstance().getReference();
    }
    public void CreatePostFunction (){
        storageRef.child("Path").getPath();
    }


}
