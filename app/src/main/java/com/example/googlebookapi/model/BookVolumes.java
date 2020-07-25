package com.example.googlebookapi.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class BookVolumes {

    @SerializedName("kind")
    @Expose
    private String kind;
    @SerializedName("totalItems")
    @Expose
    private Integer totalItems;
    @SerializedName("items")
    @Expose
    private ArrayList<Item> items;


    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public Integer getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(Integer totalItems) {
        this.totalItems = totalItems;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "BookVolumes{" +
                "kind='" + kind + '\'' +
                ", totalItems=" + totalItems +
                ", items=" + items +
                '}';
    }
}
