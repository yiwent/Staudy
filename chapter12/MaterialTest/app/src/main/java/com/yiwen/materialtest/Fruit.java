package com.yiwen.materialtest;

/**
 * User: Yiwen(https://github.com/yiwent)
 * Date: 2017-04-02
 * Time: 23:51
 * FIXME
 */
public class Fruit{
    private String name;
    private int imageId;

    public Fruit(String name, int imageId) {
        this.name = name;
        this.imageId = imageId;
    }

    public String getName() {
        return name;
    }

    public int getImageId() {
        return imageId;
    }
}
