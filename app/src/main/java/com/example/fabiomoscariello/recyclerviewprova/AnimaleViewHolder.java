package com.example.fabiomoscariello.recyclerviewprova;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
//
public class AnimaleViewHolder extends RecyclerView.ViewHolder {
    private TextView animaleTextView;
    private ImageView animaleImageView;
    public AnimaleViewHolder(View v) {
        super(v);
        animaleTextView = v.findViewById(R.id.animaleTextView_id);
        animaleImageView= v.findViewById(R.id.animale_imageView);
    }
    public void bind(String animale){
        animaleTextView.setText(animale);
    }
}
