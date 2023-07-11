package com.company.impl;

import com.company.RawInfo;

import java.util.ArrayList;
import java.util.List;

public class RawPost implements RawInfo {
    public RawPost(List<RawItem> items, int post_id){
        this.post_id = post_id;
        this.items.addAll(items);
//        this.sourse_id = "vk_" + vk_group;
    }

    private final List<RawItem> items = new ArrayList<>();
    private final int post_id;
//    private final String sourse_id;

    public List<RawItem> getItems() {
        return items;
    }

//    public String getSourse_id() {
//        return sourse_id;
//    }
    public int getPost_id() {
        return post_id;
    }
}
