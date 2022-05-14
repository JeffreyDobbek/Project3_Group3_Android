package com.example.cst438_project03_group03.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.example.cst438_project03_group03.ImageActivity;
import com.example.cst438_project03_group03.R;
import com.example.cst438_project03_group03.models.ImageInfo;

import java.util.ArrayList;
import java.util.List;

public class ImagesPagerAdapter extends PagerAdapter {

    private Context context;
    private List<ImageInfo> images = new ArrayList<>();
    LayoutInflater layoutInflater;

    public ImagesPagerAdapter(Context context, List<ImageInfo> images) {
        this.context = context;
        this.images = images;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = layoutInflater.inflate(R.layout.image_item, container, false);
        ImageView imageView = view.findViewById(R.id.image_pager_iv);

        if (images.get(position).getImage() != null) {
            Glide.with(view)
                    .load(images.get(position).getImage())
                    .into(imageView);
        }

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ImageActivity.class);
                intent.putExtra("imageId", images.get(position).getImageId());
                intent.putExtra("image", images.get(position).getImage());
                intent.putExtra("numLikes", images.get(position).getNumLikes());
                context.startActivity(intent);
            }
        });
        container.addView(view, position);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
