import com.example.secondtreasurebe.model.CartListing;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

public class CartListingTest {
    CartListing.Builder builder;

    @BeforeEach
    void setUp() {
        builder = new CartListing.Builder()
                .listingId("12345")
                .amount(2)
                .userId(1)
                .totalPrice(BigDecimal.valueOf(20.00));
    }

    @Test
    void testCreateValidCartListing() {
        CartListing cartListing = builder.build();

        assertEquals(2, cartListing.getAmount());
        assertEquals(1, cartListing.getUserId());
        assertEquals("12345", cartListing.getListingId());
        assertEquals(BigDecimal.valueOf(20.00), cartListing.getTotalPrice());

        assertNotNull(cartListing);
    }

    @Test
    void testNegativeAmount() {
        assertThrows(IllegalArgumentException.class, () -> builder.amount(-1).build());
    }

    @Test
    void testZeroAmount() {
        assertThrows(IllegalArgumentException.class, () -> builder.amount(0).build());
    }
}
