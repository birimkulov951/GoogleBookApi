package com.example.googlebookapi.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class IndustryIdentifier {

    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("identifier")
    @Expose
    private String identifier;



    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    @Override
    public String toString() {
        return "IndustryIdentifier{" +
                "type='" + type + '\'' +
                ", identifier='" + identifier + '\'' +
                '}';
    }
}
