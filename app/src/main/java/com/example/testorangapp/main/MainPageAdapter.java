package com.example.testorangapp.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testorangapp.R;
import com.example.testorangapp.post.PostTable;

import java.util.ArrayList;

public class MainPageAdapter extends RecyclerView.Adapter<MainPageAdapter.ViewHolder> {
    Context context;
    ArrayList<PostTable> list;

    public MainPageAdapter(Context context, ArrayList<PostTable> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MainPageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.row_recycle_post,parent,false
        );
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MainPageAdapter.ViewHolder holder, int position) {
        PostTable postTable = list.get(position);
        holder.postTitle.setText(postTable.getTitle());
    }
    @Override
    public int getItemCount() {
        return list.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView postTitle;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            postTitle = itemView.findViewById(R.id.tv_row_post_name);
        }
    }
}
