package com.example.hasee.expandablelistviewdemo.gson;

import java.util.List;

/**
 * Created by 浩比 on 2018/12/3.
 */

public class Result {

    private String stat;

    List<Data> data;

    public String getStat() {
        return stat;
    }

    public void setStat(String stat) {
        this.stat = stat;
    }

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }
}
