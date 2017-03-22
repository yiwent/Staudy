package com.yiwen.listviewtest;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Administrator on 2017/3/22.
 */

public class FruitAdater extends ArrayAdapter {
    private int resourceId;
    public FruitAdater(Context context, int textViewResouceId, List<Fruit> objects) {
        super(context, textViewResouceId, objects);
        resourceId=textViewResouceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Fruit fruit= (Fruit) getItem(position);
        View view;
        ViewHolder viewHolder;
        if (convertView==null){
            view= LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
            viewHolder=new ViewHolder();
            viewHolder.fruitImage= (ImageView) view.findViewById(R.id.imageView);
            viewHolder.fruitName= (TextView) view.findViewById(R.id.textView);
            view.setTag(viewHolder);
        }else {
            view=convertView;
            viewHolder= (ViewHolder) view.getTag();
        }


        viewHolder.fruitImage.setImageResource(fruit.getImageId());
        viewHolder.fruitName.setText( fruit.getName());
        return  view;
    }
    class ViewHolder{
        ImageView fruitImage;
        TextView fruitName;
    }
}
