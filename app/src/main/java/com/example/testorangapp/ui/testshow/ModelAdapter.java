package com.example.testorangapp.ui.testshow;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testorangapp.R;
import com.example.testorangapp.model.PostTable;

import java.util.List;

public class ModelAdapter extends RecyclerView.Adapter<ModelAdapter.ViewHolder> {
    private List<PostTable> mModels;

    public ModelAdapter(List<PostTable> models) {
        mModels = models;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PostTable model = mModels.get(position);
        holder.bind(model);
    }

    @Override
    public int getItemCount() {
        return mModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextView = itemView.findViewById(R.id.test_TextView1);
        }

        public void bind(PostTable model) {
            mTextView.setText(model.getTitle());
        }
    }
}

