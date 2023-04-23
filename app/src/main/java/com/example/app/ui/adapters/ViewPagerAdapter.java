package com.example.app.ui.adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import com.example.app.R;

import java.util.ArrayList;

public class ViewPagerAdapter extends PagerAdapter {
    private Context context;
    ArrayList<Uri> Product_image;
    LayoutInflater layoutInflater;

    public ViewPagerAdapter(Context context, ArrayList<Uri> product_image) {
        this.context = context;
        Product_image = product_image;
        layoutInflater = LayoutInflater.from(context); // initialize layoutInflater
    }

    @Override
    public int getCount() {
        return Product_image.size();
    }

    @NonNull
    @Override
    public Object instantiateItem (@NonNull ViewGroup container, int position) {
        View view = layoutInflater.inflate (R.layout.showimageslayout, container, false);
        ImageView imageView = view.findViewById(R.id.UploadImage);
        imageView.setImageURI(Product_image.get(position));
        container.addView(view);
        return view;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull View container, int position, @NonNull Object object) {
        ((RelativeLayout)object).removeView(container);
    }
}