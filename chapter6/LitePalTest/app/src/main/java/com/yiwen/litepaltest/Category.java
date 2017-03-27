package com.yiwen.litepaltest;

/**
 * Created by Administrator on 2017/3/26.
 */

public class Category {
    private int id;
    private String categoryname;
    private  int categorycode;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategoryname() {
        return categoryname;
    }

    public void setCategoryname(String categoryname) {
        this.categoryname = categoryname;
    }

    public int getCategorycode() {
        return categorycode;
    }

    public void setCategorycode(int categorycode) {
        this.categorycode = categorycode;
    }
}
