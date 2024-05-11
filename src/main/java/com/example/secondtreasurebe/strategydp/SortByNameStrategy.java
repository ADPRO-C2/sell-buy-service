package com.example.secondtreasurebe.strategydp;

import com.example.secondtreasurebe.model.Listing;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class SortByNameStrategy implements SortStrategy {
    @Override
    public List<Listing> sort(List<Listing> listings) {
        Collections.sort(listings, Comparator.comparing(Listing::getName));
        return listings;
    }
}