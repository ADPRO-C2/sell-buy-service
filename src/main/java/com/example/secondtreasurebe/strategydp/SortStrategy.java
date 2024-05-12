package com.example.secondtreasurebe.strategydp;

import com.example.secondtreasurebe.model.Listing;

import java.util.List;

public interface SortStrategy {
    List<Listing> sort(List<Listing> listings);
}