package com.example.tfg.OtherFiles.Adapters;

import com.example.tfg.R;
import com.smarteist.autoimageslider.SliderViewAdapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.view.LayoutInflater;
import android.annotation.SuppressLint;

public class SliderAdapter extends SliderViewAdapter<SliderAdapter.SliderViewHolder>{

    private final int[] images;

    public SliderAdapter(int[] images) {
        this.images = images;
    }

    @SuppressLint("InflateParams")
    @Override
    public SliderViewHolder onCreateViewHolder(ViewGroup parent) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.slider_item_layout,null);
        return new SliderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SliderViewHolder viewHolder, int position) {
        viewHolder.imageView.setImageResource(images[position]);
    }

    @Override
    public int getCount() {
        return images.length;
    }

    public static class SliderViewHolder extends ViewHolder {
        private final ImageView imageView;
        public SliderViewHolder(View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.image);
        }
    }
}