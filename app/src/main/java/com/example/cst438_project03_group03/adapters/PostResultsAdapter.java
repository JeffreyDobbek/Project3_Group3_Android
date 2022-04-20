package com.example.cst438_project03_group03.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cst438_project03_group03.R;
import com.example.cst438_project03_group03.database.Post;

import java.util.ArrayList;
import java.util.List;

public class PostResultsAdapter extends RecyclerView.Adapter<PostResultsAdapter.PostResultsHolder> {

    private List<Post> results = new ArrayList<>();

    @NonNull
    @Override
    public PostResultsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.post_item, parent, false);

        return new PostResultsHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PostResultsHolder holder, int position) {
        Post post = results.get(position);
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public void setResults(List<Post> results) {
        this.results = results;
        notifyDataSetChanged();
    }

    public class PostResultsHolder extends RecyclerView.ViewHolder {

        public PostResultsHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
