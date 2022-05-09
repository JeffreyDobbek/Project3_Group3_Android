package com.example.cst438_project03_group03.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.cst438_project03_group03.R;
import com.example.cst438_project03_group03.database.Post;
import com.example.cst438_project03_group03.models.ImageInfo;
import com.example.cst438_project03_group03.models.PostInfo;
import com.example.cst438_project03_group03.models.UserInfo;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.microedition.khronos.opengles.GL;

import de.hdodenhof.circleimageview.CircleImageView;

public class PostResultsAdapter extends RecyclerView.Adapter<PostResultsAdapter.PostResultsHolder> {

    private List<PostInfo> posts = new ArrayList<>();
    private HashMap<Integer, UserInfo> users = new HashMap<>();
    private HashMap<Integer, List<ImageInfo>> images = new HashMap<>();

    @NonNull
    @Override
    public PostResultsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.post_item, parent, false);

        return new PostResultsHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PostResultsHolder holder, int position) {
        PostInfo post = posts.get(position);

        int lastOrder = 0;

        if (post.getProfilePic() != null) {
            String imageUrl = post.getProfilePic()
                    .replace("http://", "https://");

            holder.profilePic.setVisibility(View.VISIBLE);
            Glide.with(holder.itemView)
                    .load(imageUrl)
                    .into(holder.profilePic);
        } else {
            holder.profilePic.setVisibility(View.INVISIBLE);
        }

        if (post.getUsername() != null) {
            holder.username.setText(post.getUsername());
        } else {
            holder.username.setText("");
        }

        if (post.getImages() != null) {
            Glide.with(holder.itemView)
                    .load(post.getImages().get(0).getImage())
                    .into(holder.image1);

            Glide.with(holder.itemView)
                    .load(post.getImages().get(1).getImage())
                    .into(holder.image2);

            if (post.getImages().size() >= 3) {
                holder.image3.setVisibility(View.VISIBLE);

                Glide.with(holder.itemView)
                        .load(post.getImages().get(2).getImage())
                        .into(holder.image3);
            } else {
                holder.image3.setVisibility(View.GONE);
            }

            if (post.getImages().size() == 4) {
                holder.image4.setVisibility(View.VISIBLE);

                Glide.with(holder.itemView)
                        .load(post.getImages().get(3).getImage())
                        .into(holder.image4);
            } else {
                holder.image4.setVisibility(View.GONE);
            }
        }

        if (post.getCaption() != null) {
            holder.caption.setText(post.getCaption());
        }

        if (post.getNumComments() > 0) {
            holder.numComments.setText(post.getNumComments());
        }
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public void setPosts(List<PostInfo> posts) {
        this.posts = posts;
    }

    public void setUsers(HashMap<Integer, UserInfo> users) {
        this.users = users;
    }

    public void setImages(HashMap<Integer, List<ImageInfo>> images) {
        this.images = images;
        setRecyclerData();
    }

    private void setRecyclerData() {
        for (PostInfo post : posts) {
            post.setUsername(users.get(post.getUserId()).getUsername());

            if (users.get(post.getUserId()).getImage() != null) {
                post.setProfilePic(users.get(post.getUserId()).getImage());
            }

            post.setImages(images.get(post.getPostId()));
        }
        notifyDataSetChanged();
    }

    public class PostResultsHolder extends RecyclerView.ViewHolder {

        private CircleImageView profilePic;
        private ImageView image1;
        private ImageView image2;
        private ImageView image3;
        private ImageView image4;
        private TextView username;
        private TextView caption;
        private TextView numComments;

        public PostResultsHolder(@NonNull View itemView) {
            super(itemView);

            profilePic = itemView.findViewById(R.id.live_feed_profile_pic);
            image1 = itemView.findViewById(R.id.live_feed_image1);
            image2 = itemView.findViewById(R.id.live_feed_image2);
            image3 = itemView.findViewById(R.id.live_feed_image3);
            image4 = itemView.findViewById(R.id.live_feed_image4);
            username = itemView.findViewById(R.id.live_feed_post_username);
            caption = itemView.findViewById(R.id.live_feed_post_caption);
            numComments = itemView.findViewById(R.id.live_feed_num_comments);
        }
    }
}
