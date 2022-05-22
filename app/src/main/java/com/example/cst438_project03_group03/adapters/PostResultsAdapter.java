package com.example.cst438_project03_group03.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.example.cst438_project03_group03.ImageActivity;
import com.example.cst438_project03_group03.R;
import com.example.cst438_project03_group03.ViewCommentsActivity;
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

/**
 * Class: PostResultsAdapter.java
 * Description: Recycler view adapter for posts.
 */
public class PostResultsAdapter extends RecyclerView.Adapter<PostResultsAdapter.PostResultsHolder> {

    private final Context context;
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

    public PostResultsAdapter(Context context) {
        this.context = context;
    }

    @Override
    public void onBindViewHolder(@NonNull PostResultsHolder holder, int position) {
        PostInfo post = posts.get(position);
        ImagesPagerAdapter adapter;

        if (post.getProfilePic() != null) {
            String imageUrl = post.getProfilePic()
                    .replace("http://", "https://");

            holder.profilePic.setVisibility(View.VISIBLE);
            Glide.with(holder.itemView)
                    .load(imageUrl)
                    .into(holder.profilePic);
        } else {
            holder.profilePic.setImageResource(R.mipmap.ic_blank_profile_pic);
        }

        if (post.getUsername() != null) {
            holder.username.setText(post.getUsername());
        } else {
            holder.username.setText("");
        }

        if (!post.getImages().isEmpty()) {
            adapter = new ImagesPagerAdapter(context, post.getImages());
            holder.viewPager.setAdapter(adapter);
            holder.viewPager.setCurrentItem(0);
        }

        if (post.getCaption() != null) {
            holder.caption.setText(post.getCaption());
        }

        if (post.getNumComments() > 0) {
            holder.numComments.setText(post.getNumComments() + "");
        } else {
            holder.numComments.setText("0");
        }

        holder.viewAllCommentsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context.getApplicationContext(), ViewCommentsActivity.class);
                intent.putExtra("postId", post.getPostId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public void setPosts(List<PostInfo> posts) {
        this.posts = posts;
        notifyDataSetChanged();
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
            if (!users.isEmpty()) {
                post.setUsername(users.get(post.getUserId()).getUsername());
                if (users.get(post.getUserId()).getImage() != null) {
                    post.setProfilePic(users.get(post.getUserId()).getImage());
                }
            }
            post.setImages(images.get(post.getPostId()));
        }
        notifyDataSetChanged();
    }

    /**
     * Class: PostResultsHolder
     * Description: Recycler view holder for individual post information.
     */
    public class PostResultsHolder extends RecyclerView.ViewHolder {

        private CircleImageView profilePic;
        private TextView username;
        private TextView caption;
        private TextView numComments;
        private Button viewAllCommentsBtn;
        private ViewPager viewPager;

        public PostResultsHolder(@NonNull View itemView) {
            super(itemView);

            profilePic = itemView.findViewById(R.id.live_feed_profile_pic);
            username = itemView.findViewById(R.id.live_feed_post_username);
            caption = itemView.findViewById(R.id.live_feed_post_caption);
            numComments = itemView.findViewById(R.id.live_feed_num_comments);
            viewAllCommentsBtn = itemView.findViewById(R.id.view_all_comments_button);
            viewPager = itemView.findViewById(R.id.live_feed_vp);
        }
    }
}
