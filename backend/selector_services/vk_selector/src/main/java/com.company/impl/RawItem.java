package com.company.impl;

import com.company.RawInfo;

public class RawItem implements RawInfo {
    public RawItem(String item, String type){
        this.item = item;
        this.type = type;
    }
    private final String item;
    private final String type;

    public String getItem() {
        return item;
    }

    public String getType() {
        return type;
    }
}
