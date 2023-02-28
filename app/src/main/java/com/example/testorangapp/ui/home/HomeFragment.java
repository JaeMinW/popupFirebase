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

import com.example.testorangapp.PostActivity;
import com.example.testorangapp.PostActivity2;
import com.example.testorangapp.adapter.MainPageAdapter;
import com.example.testorangapp.databinding.FragmentHomeBinding;
import com.example.testorangapp.model.PostTable;
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
/*
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot dataSnapshot : snapshot.getChildren()){

                    PostTable post = dataSnapshot.getValue(PostTable.class);
                    //같은 테이블과 같은 디비구문을 맞추면
                    // 알아서 키값으로 받아서 올 것 같다.

                    list.add(post);

                }
                mainPageAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

 */
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
            Intent intent = new Intent(getActivity(), PostActivity2.class);
             startActivity(intent);
        }
    }

}