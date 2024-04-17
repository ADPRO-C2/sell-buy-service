package com.example.secondtreasurebe.model;

public class NewListing extends Listing {
    public NewListing(){
        super();
    };

    @Override
    public String getType() {
        return "new";
    }
}