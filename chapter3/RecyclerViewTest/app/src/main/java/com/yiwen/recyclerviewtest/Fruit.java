package com.yiwen.recyclerviewtest;

/**
 * Created by Administrator on 2017/3/22.
 */

public class Fruit {
    private String name;
    private int imageId;
    public Fruit(String name,int imageId){
        this.name=name;
        this.imageId=imageId;
    }

    public int getImageId() {
        return imageId;
    }

    public String getName() {
        return name;
    }
}
