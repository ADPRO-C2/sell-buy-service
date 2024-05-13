package com.example.secondtreasurebe.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.secondtreasurebe.model.Listing;

import java.util.List;

@Repository
public interface ListingRepository extends JpaRepository<Listing, String> {
    List<Listing> findByUserId(int listingId);
}

