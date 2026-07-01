package com.example.order;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.*;

class DiscountCalculatorTest {

    private final DiscountCalculator calculator = new DiscountCalculator();

    @Test
    void zeroYearsOfMembershipReturnsZeroDiscount() {
        assertEquals(0.0, calculator.loyaltyDiscount(0), 0.001);
    }

    @Test
    void oneYearOfMembershipReturnsZeroDiscount() {
        assertEquals(0.0, calculator.loyaltyDiscount(1), 0.001);
    }

    @Test
    void twoYearsOfMembershipReturnsFivePercentDiscount() {
        assertEquals(0.05, calculator.loyaltyDiscount(2), 0.001);
    }

    @Test
    void threeYearsOfMembershipReturnsFivePercentDiscount() {
        assertEquals(0.05, calculator.loyaltyDiscount(3), 0.001);
    }

    @Test
    void fourYearsOfMembershipReturnsTenPercentDiscount() {
        assertEquals(0.10, calculator.loyaltyDiscount(4), 0.001);
    }

    @Test
    void fiveYearsOfMembershipReturnsTenPercentDiscount() {
        assertEquals(0.10, calculator.loyaltyDiscount(5), 0.001);
    }

    @Test
    void sixYearsOfMembershipReturnsTenPercentDiscount() {
        assertEquals(0.10, calculator.loyaltyDiscount(6), 0.001);
    }

    @Test
    void sevenYearsOfMembershipReturnsFifteenPercentDiscount() {
        assertEquals(0.15, calculator.loyaltyDiscount(7), 0.001);
    }

    @Test
    void tenYearsOfMembershipReturnsFifteenPercentDiscount() {
        assertEquals(0.15, calculator.loyaltyDiscount(10), 0.001);
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1})
    void noDiscountForNewMembers(int years) {
        assertEquals(0.0, calculator.loyaltyDiscount(years), 0.001);
    }

    @ParameterizedTest
    @ValueSource(ints = {2, 3})
    void fivePercentDiscountForIntermediateMembers(int years) {
        assertEquals(0.05, calculator.loyaltyDiscount(years), 0.001);
    }

    @ParameterizedTest
    @ValueSource(ints = {4, 5, 6})
    void tenPercentDiscountForLoyalMembers(int years) {
        assertEquals(0.10, calculator.loyaltyDiscount(years), 0.001);
    }

    @ParameterizedTest
    @ValueSource(ints = {7, 10, 20, 100})
    void fifteenPercentDiscountForVeteranMembers(int years) {
        assertEquals(0.15, calculator.loyaltyDiscount(years), 0.001);
    }

    // Tests for finalPrice method
    @Test
    void finalPrice_noDiscounts() {
        // basePrice = 10.00, no loyalty, no volume discount
        assertEquals(10.00, calculator.finalPrice(10.0, 1, 0), 0.001);
    }

    @Test
    void finalPrice_loyaltyDiscountOnly() {
        // basePrice = 50.00, loyalty 10% (years=4) -> 45.00
        assertEquals(45.00, calculator.finalPrice(50.0, 1, 4), 0.001);
    }

    @Test
    void finalPrice_volumeDiscountOnly() {
        // basePrice = 100.00, volume discount 8% (qty=10) -> 92.00
        assertEquals(92.00, calculator.finalPrice(10.0, 10, 0), 0.001);
    }

    @Test
    void finalPrice_bothDiscountsApply() {
        // basePrice = 200.00, loyalty 10% (years=5) + volume 8% -> total 18% -> 164.00
        assertEquals(164.00, calculator.finalPrice(20.0, 10, 5), 0.001);
    }

    @Test
    void finalPrice_roundingEdgeCase() {
        // basePrice = 99.9, volume 8% -> discounted = 91.908 -> rounds to 91.91
        assertEquals(91.91, calculator.finalPrice(9.99, 10, 0), 0.001);
    }

}