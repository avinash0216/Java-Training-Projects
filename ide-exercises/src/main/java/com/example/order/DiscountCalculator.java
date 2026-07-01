package com.example.order;

public class DiscountCalculator {

    // Calculate loyalty discount based on years of membership
    // 0-1 years: 0%, 2-3 years: 5%, 4-6 years: 10%, 7+ years: 15%
    public double loyaltyDiscount(int yearsOfMembership) {
        return switch (yearsOfMembership) {
            case 0, 1 -> 0.0;
            case 2, 3 -> 0.05;
            case 4, 5, 6 -> 0.10;
            default -> 0.15;
        };
    }
    /**
     * Calculates the final price after applying both a loyalty discount
     * and a volume discount. The volume discount applies when quantity >= 10
     * and is 8%. Discounts are additive, not compounded.
     * Returns a value rounded to 2 decimal places.
     */
    public double finalPrice(double unitPrice, int quantity, int yearsOfMembership) {
        double basePrice = unitPrice * quantity;
        double loyaltyDisc = loyaltyDiscount(yearsOfMembership);
        double volumeDisc = quantity >= 10 ? 0.08 : 0.0;
        double totalDisc = loyaltyDisc + volumeDisc;
        double discountedPrice = basePrice * (1 - totalDisc);
        return Math.round(discountedPrice * 100.0) / 100.0;
    }

}