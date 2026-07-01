package com.example.order;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class OrderServiceTest {

    private final OrderService service = new OrderService();

    @Test
    void standardCustomerTotalWithShipping() {
        var items = List.of(
                new OrderItem("SKU-A", 2, 10.00),  // line total = 20.00
                new OrderItem("SKU-B", 1, 30.00)   // line total = 30.00
        );
        // total = 50.00, below free-shipping threshold → +9.99 shipping
        assertEquals(59.99, service.calculateTotal(items, false, null), 0.001);
    }

    @Test
    void premiumCustomerGetsDiscount() {
        var items = List.of(
                new OrderItem("SKU-A", 4, 50.00)   // line total = 200.00
        );
        // 200 * 0.9 = 180.00, above shipping threshold
        assertEquals(180.00, service.calculateTotal(items, true, null), 0.001);
    }

    @Test
    void nullItemsThrowsException() {
        assertThrows(IllegalArgumentException.class,
                () -> service.calculateTotal(null, false, null));
    }

    @Test
    void filterKeepsOnlyItemsAboveMinValue() {
        var items = List.of(
                new OrderItem("SKU-A", 1, 5.00),   // 5.00 — below threshold
                new OrderItem("SKU-B", 2, 15.00),  // 30.00 — above threshold
                new OrderItem("SKU-C", 3, 10.00)   // 30.00 — above threshold
        );
        var filtered = service.itemsAboveMinLineTotal(items, 20.00);
        assertEquals(2, filtered.size());
    }
}