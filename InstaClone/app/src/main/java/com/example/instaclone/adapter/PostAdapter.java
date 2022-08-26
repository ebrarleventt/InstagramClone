package com.example.instaclone.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.instaclone.databinding.RecyclerFeedBinding;
import com.example.instaclone.model.Post;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostHolder> {

    private ArrayList<Post> postArrayList;

    public PostAdapter(ArrayList<Post> postArrayList){
        this.postArrayList = postArrayList;
    }

    @NonNull
    @Override
    public PostHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerFeedBinding recyclerFeedBinding = RecyclerFeedBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new PostHolder(recyclerFeedBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull PostHolder holder, int position) {
        holder.recyclerFeedBinding.recyclerViewEmailTV.setText(postArrayList.get(position).email);
        holder.recyclerFeedBinding.recyclerViewCommentTV.setText(postArrayList.get(position).comment);
        Picasso.get().load(postArrayList.get(position).downloadUrl).into(holder.recyclerFeedBinding.recyclerViewImageView);
    }

    @Override
    public int getItemCount() {
        return postArrayList.size();
    }

    public class PostHolder extends RecyclerView.ViewHolder{

        RecyclerFeedBinding recyclerFeedBinding;

        public PostHolder(RecyclerFeedBinding recyclerFeedBinding) {
            super(recyclerFeedBinding.getRoot());
            this.recyclerFeedBinding = recyclerFeedBinding;
        }
    }

}
