package com.example.project.parameter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

// Source class to store the source name and id
public class Source {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
