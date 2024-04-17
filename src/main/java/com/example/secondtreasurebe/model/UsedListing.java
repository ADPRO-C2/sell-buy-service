package com.example.secondtreasurebe.model;

public class UsedListing extends Listing {
        public UsedListing(){
            super();
        }
    
        @Override
        public String getType() {
            return "used";
        }
    }