package com.example.testorangapp.ui.testshow;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.testorangapp.model.FirebaseRepository;
import com.example.testorangapp.model.PostTable;

import java.util.List;

public class TestPostFragmentViewModel extends ViewModel {

    private FirebaseRepository mFirebaseRepository;
    private LiveData<List<PostTable>> mModelList;

    public TestPostFragmentViewModel() {
        mFirebaseRepository = new FirebaseRepository();
        mModelList = mFirebaseRepository.getModelList();
    }

    public LiveData<List<PostTable>> getModelList() {
        return mModelList;
    }
}
