package com.example.cst438_project03_group03.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.cst438_project03_group03.R;
import com.example.cst438_project03_group03.models.CommentInfo;
import com.example.cst438_project03_group03.models.UserInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Class: CommentsAdapter.java
 * Description: Recycler view adapter for post comments.
 */
public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.CommentsHolder> {

    private Context context;
    private List<CommentInfo> comments = new ArrayList<>();
    private HashMap<Integer, UserInfo> users = new HashMap<>();

    @NonNull
    @Override
    public CommentsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.comment_item, parent, false);

        return new CommentsHolder(itemView);
    }

    public CommentsAdapter(Context context) {
        this.context = context;
    }

    @Override
    public void onBindViewHolder(@NonNull CommentsHolder holder, int position) {
        CommentInfo comment = comments.get(position);

        if (comment.getProfilePic() != null) {
            String imageUrl = comment.getProfilePic()
                    .replace("http://", "https://");

            holder.profilePic.setVisibility(View.VISIBLE);
            Glide.with(holder.itemView)
                    .load(imageUrl)
                    .into(holder.profilePic);
        } else {
            holder.profilePic.setImageResource(R.mipmap.ic_blank_profile_pic);
        }

        if (comment.getComment() != null) {
            holder.comment.setText(comment.getComment());
        } else {
            holder.comment.setText("");
        }

        if (comment.getNumLikes() != 0) {
            holder.likes.setText("Likes: " + comment.getNumLikes());
        } else {
            holder.likes.setText("Likes: 0");
        }

        /**
         * Likes or unlikes a comments
         * TODO: Needs to be connected to an api endpoint for persistence.
         */
        holder.favoriteIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if (holder.favoriteIv.getTag().equals("not liked")) {
                   holder.favoriteIv.setImageResource(R.drawable.ic_favorite);
                   holder.favoriteIv.setTag("liked");
                   holder.likes.setText("Likes: " + (comment.getNumLikes() + 1));
               } else {
                   holder.favoriteIv.setImageResource(R.drawable.ic_favorite_border);
                   holder.favoriteIv.setTag("not liked");
                   holder.likes.setText("Likes: " + comment.getNumLikes());
               }
            }
        });
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    public void setComments(List<CommentInfo> comments) {
        this.comments = comments;
    }

    public void setUsers(HashMap<Integer, UserInfo> users) {
        this.users = users;
        setRecyclerData();
    }

    public void setRecyclerData() {
        for (CommentInfo comment : comments) {
            comment.setUsername(users.get(comment.getUserId()).getUsername());

            if (users.get(comment.getUserId()).getImage() != null) {
                comment.setProfilePic(users.get(comment.getUserId()).getImage());
            }
        }
        notifyDataSetChanged();
    }

    /**
     * Class: CommentsHolder.java
     * Description: Recycler view holder for comment data.
     */
    public class CommentsHolder extends RecyclerView.ViewHolder {

        private CircleImageView profilePic;
        private ImageView favoriteIv;
        private TextView comment;
        private TextView likes;

        public CommentsHolder(@NonNull View itemView) {
            super(itemView);

            profilePic = itemView.findViewById(R.id.comment_profile_pic_civ);
            favoriteIv = itemView.findViewById(R.id.comment_favorite_iv);
            favoriteIv.setTag("not liked");
            comment = itemView.findViewById(R.id.comment_tv);
            likes = itemView.findViewById(R.id.comment_likes);
        }
    }
}
