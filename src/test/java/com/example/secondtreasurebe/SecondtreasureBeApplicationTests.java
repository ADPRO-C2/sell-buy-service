package com.example.secondtreasurebe;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
class SecondtreasureBeApplicationTests {

    @Test
    void contextLoads() {
        assertDoesNotThrow(() -> SecondtreasureBeApplication.main(new String[]{}));
    }

}
