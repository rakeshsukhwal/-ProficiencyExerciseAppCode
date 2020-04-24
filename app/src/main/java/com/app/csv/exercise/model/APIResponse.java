package com.app.csv.exercise.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * The data class for API response
 * */
public class APIResponse {
    @SerializedName("title")
    private String title;

    @SerializedName("rows")
    private List<Item> items = null;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}
