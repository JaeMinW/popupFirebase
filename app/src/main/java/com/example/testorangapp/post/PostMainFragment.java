package com.example.testorangapp.post;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.testorangapp.R;

public class PostMainFragment extends Fragment {


    public PostMainFragment() {
        // Required empty public constructor
    }

    public static PostMainFragment newInstance(String param1, String param2) {
        PostMainFragment fragment = new PostMainFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_post_main, container, false);
    }
}