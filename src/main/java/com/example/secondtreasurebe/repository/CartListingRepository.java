package com.example.secondtreasurebe.repository;

import com.example.secondtreasurebe.model.CartListing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartListingRepository extends JpaRepository<CartListing, String> {
    List<CartListing> findAllByUserId(int userId);
}