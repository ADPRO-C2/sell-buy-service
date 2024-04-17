package com.example.secondtreasurebe.factory;

import com.example.secondtreasurebe.model.UsedListing;
import com.example.secondtreasurebe.model.NewListing;
import com.example.secondtreasurebe.model.Listing;

public class ListingFactoryImpl implements ListingFactory {
    @Override
    public Listing createListing(int rateCondition) {
        Listing createdListing = null;
        if (rateCondition < 0 || rateCondition > 3) {
            throw new IllegalArgumentException("Invalid rate condition: " + rateCondition);
        }else if (rateCondition == 0) {
            createdListing =  new NewListing();
        } else{
            createdListing =  new UsedListing();
        }

        return createdListing;
    }
}
