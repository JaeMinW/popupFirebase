package com.example.testorangapp.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testorangapp.databinding.FragmentHomeBinding;
import com.example.testorangapp.main.MainPageAdapter;
import com.example.testorangapp.model.FirebaseRepository;
import com.example.testorangapp.post.PostActivity;
import com.example.testorangapp.post.PostTable;
import com.example.testorangapp.sign.LoginActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    RecyclerView recyclerView;
    DatabaseReference databaseReference;
    MainPageAdapter mainPageAdapter;
    ArrayList<PostTable> list;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textHome;
        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        CreatePostTable createPostTable = new CreatePostTable();
        binding.btnCreatePost.setOnClickListener(createPostTable);

        CreatePostTable1 createPostTable1 = new CreatePostTable1();
        binding.btnCreateTestPost.setOnClickListener(createPostTable1);


        recyclerView = binding.homeRecycler;
        databaseReference = FirebaseDatabase.getInstance().getReference("Post").child("Category").child("1");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        list = new ArrayList<>();
        mainPageAdapter = new MainPageAdapter(getContext(),list);
        recyclerView.setAdapter(mainPageAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    class CreatePostTable implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(getActivity(), PostActivity.class);
             startActivity(intent);
        }
    }

    class CreatePostTable1 implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            FirebaseRepository firebaseRepository = new FirebaseRepository();
            firebaseRepository.logOut();
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
            getActivity().finish();
        }
    }

}