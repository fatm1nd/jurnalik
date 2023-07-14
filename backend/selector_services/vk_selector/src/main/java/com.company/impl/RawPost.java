package com.company.impl;

import com.company.RawInfo;

import java.util.ArrayList;
import java.util.List;

public class RawPost implements RawInfo {
    public RawPost(List<RawItem> items, int post_id, int group_id){
        this.post_id = post_id;
        this.items.addAll(items);
        this.group_id = group_id;
    }

    private final List<RawItem> items = new ArrayList<>();
    private final int post_id;

    private final int group_id;

    public List<RawItem> getItems() {
        return items;
    }

    public int getPost_id() {
        return post_id;
    }

    public int getGroup_id() {return group_id;}
}
