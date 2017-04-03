package com.yiwen.materialtest;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * User: Yiwen(https://github.com/yiwent)
 * Date: 2017-04-03
 * Time: 00:05
 * FIXME
 */
public class FruitAdapter extends RecyclerView.Adapter<FruitAdapter.ViewHonder> {
    private Context     mContex;
    private List<Fruit> mFruirList;

    public FruitAdapter(List<Fruit> mFruitList) {
        this.mFruirList = mFruitList;

    }

    @Override
    public ViewHonder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContex == null) {
            mContex = parent.getContext();
        }
        View view = LayoutInflater.from(mContex).inflate(R.layout.fruit_item, parent, false);
        final ViewHonder honder = new ViewHonder(view);
        honder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = honder.getAdapterPosition();
                Fruit fruit = mFruirList.get(position);
                Intent intent = new Intent(mContex, FruitActivity.class);
                intent.putExtra(FruitActivity.FRUIT_NAME, fruit.getName());
                intent.putExtra(FruitActivity.FRUIT_IMAGE_ID, fruit.getImageId());
                mContex.startActivity(intent);
            }
        });
        return honder;
    }

    @Override
    public void onBindViewHolder(ViewHonder holder, int position) {
        Fruit fruit = mFruirList.get(position);
        holder.mTextView.setText(fruit.getName());
        Glide.with(mContex).load(fruit.getImageId()).into(holder.mImageView);
    }

    @Override
    public int getItemCount() {
        return mFruirList.size();
    }

    static class ViewHonder extends RecyclerView.ViewHolder {
        CardView  mCardView;
        ImageView mImageView;
        TextView  mTextView;

        public ViewHonder(View view) {
            super(view);
            mCardView = (CardView) view;
            mImageView = (ImageView) view.findViewById(R.id.fruit_image);
            mTextView = (TextView) view.findViewById(R.id.fruit_name);
        }
    }
}
