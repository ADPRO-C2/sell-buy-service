package com.example.secondtreasurebe.strategydp;

import com.example.secondtreasurebe.model.Listing;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class SortByNameTest {
    private Listing listing1, listing2, listing3;

    @BeforeEach
    void setUp() {
        listing1 = new Listing();
        listing1.setUserId("eb558e9f-1c39-460e-8860-71af6af63ba7");
        listing1.setName("Kemeja Linen Blend");
        listing1.setStock(10);
        listing1.setDescription("Kerah terbuka, bahan nyaman dipakai.");
        listing1.setPhotoUrl("https://image.uniqlo.com/UQ/ST3/id/imagesgoods/467247/item/idgoods_09_467247.jpg?width=750");
        listing1.setPrice(299000);
        listing1.setRateCondition(0);

        listing2 = new Listing();
        listing2.setUserId("eb558e9f-1c39-460e-8860-71af6af63ba7");
        listing2.setName("T-Shirt Kerah Bulat");
        listing2.setStock(50);
        listing2.setDescription("Enak dipakai");
        listing2.setPhotoUrl("https://image.uniqlo.com/UQ/ST3/id/imagesgoods/424873/item/idgoods_08_424873.jpg?width=320");
        listing2.setPrice(149000);
        listing2.setRateCondition(2);

        listing3 = new Listing();
        listing3.setUserId("eb558e9f-1c39-460e-8860-71af6af63ba7");
        listing3.setName("Atasan Cantik");
        listing3.setStock(50);
        listing3.setDescription("Enak dipakai");
        listing3.setPhotoUrl("https://image.uniqlo.com/UQ/ST3/id/imagesgoods/424873/item/idgoods_08_424873.jpg?width=320");
        listing3.setPrice(149000);
        listing3.setRateCondition(2);
    }

    @Test
    public void testSortByName() {
        List<Listing> listings = new ArrayList<>();
        listings.add(listing1);
        listings.add(listing2);
        listings.add(listing3);

        SortByNameStrategy sorter = new SortByNameStrategy();
        List<Listing> sortedListings = sorter.sort(listings);

        assertEquals("Atasan Cantik", sortedListings.get(0).getName());
        assertEquals("Kemeja Linen Blend", sortedListings.get(1).getName());
        assertEquals("T-Shirt Kerah Bulat", sortedListings.get(2).getName());
    }
}
