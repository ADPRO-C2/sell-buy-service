package com.example.secondtreasurebe.strategydp;

import com.example.secondtreasurebe.model.Listing;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class SortByPriceTest {
    private Listing listing1, listing2, listing3;

    @BeforeEach
    void setUp() {
        listing1 = new Listing();
        listing1.setUserId(1);
        listing1.setName("Kemeja Linen Blend");
        listing1.setStock(10);
        listing1.setDescription("Kerah terbuka, bahan nyaman dipakai.");
        listing1.setPhotoUrl("https://image.uniqlo.com/UQ/ST3/id/imagesgoods/467247/item/idgoods_09_467247.jpg?width=750");
        listing1.setPrice(299000);
        listing1.setRateCondition(0);

        listing2 = new Listing();
        listing2.setUserId(1);
        listing2.setName("T-Shirt Kerah Bulat");
        listing2.setStock(50);
        listing2.setDescription("Enak dipakai");
        listing2.setPhotoUrl("https://image.uniqlo.com/UQ/ST3/id/imagesgoods/424873/item/idgoods_08_424873.jpg?width=320");
        listing2.setPrice(199000);
        listing2.setRateCondition(2);

        listing3 = new Listing();
        listing3.setUserId(1);
        listing3.setName("Atasan Cantik");
        listing3.setStock(50);
        listing3.setDescription("Enak dipakai");
        listing3.setPhotoUrl("https://image.uniqlo.com/UQ/ST3/id/imagesgoods/424873/item/idgoods_08_424873.jpg?width=320");
        listing3.setPrice(399000);
        listing3.setRateCondition(2);
    }

    @Test
    public void testSortByPrice() {
        List<Listing> listings = new ArrayList<>();
        listings.add(listing1);
        listings.add(listing2);
        listings.add(listing3);

        SortByPriceStrategy sorter = new SortByPriceStrategy();
        List<Listing> sortedListings = sorter.sort(listings);

        assertEquals(199000, sortedListings.get(0).getPrice());
        assertEquals(299000, sortedListings.get(1).getPrice());
        assertEquals(399000, sortedListings.get(2).getPrice());
    }
}
