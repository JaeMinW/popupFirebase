package com.example.testorangapp.ui.testshow;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testorangapp.databinding.FragmentBlankBinding;

import java.util.ArrayList;

public class TestPostFragment extends Fragment {
    private TestPostFragmentViewModel mViewModel;
    private RecyclerView mRecyclerView;
    private ModelAdapter mAdapter;
    private FragmentBlankBinding binding;

    public TestPostFragment() {
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentBlankBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

       //mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView = binding.recyclerTestView1;

        mAdapter = new ModelAdapter(new ArrayList<>());
        Log.d("DAF","");
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mAdapter);
        mViewModel = new ViewModelProvider(this).get(TestPostFragmentViewModel.class);
        mViewModel.getModelList().observe(getViewLifecycleOwner(), models -> {
            mAdapter = new ModelAdapter(models);
            mRecyclerView.setAdapter(mAdapter);
        });
        return root;
    }
}