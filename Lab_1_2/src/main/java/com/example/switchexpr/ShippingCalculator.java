package com.example.switchexpr;

public class ShippingCalculator {

    public static void main(String[] args) {
        var calc = new ShippingCalculator();

        for (ShippingTier tier : ShippingTier.values()) {
            double orderTotal = 350.00;
            double base = calc.baseShippingFee(tier);
            double discount = calc.discountRate(tier, orderTotal);
            double finalFee = base * (1 - discount);

            System.out.printf("%-10s base=$%.2f  discount=%.0f%%  final=$%.2f%n",
                    tier, base, discount * 100, finalFee);
        }
    }

    public enum ShippingTier { STANDARD, EXPRESS, OVERNIGHT, SAME_DAY }

    /**
     * TODO 16: Rewrite this method using a switch EXPRESSION (arrow form).
     * The method should return a base shipping fee (double) per tier.
     * Use the arrow form: case X -> value;
     * Make it exhaustive -- handle every enum constant.
     */
    public double baseShippingFee(ShippingTier tier) {
        // Switch expression (arrow form) returning the fee for each tier.
        return switch (tier) {
            case STANDARD  -> 4.99;
            case EXPRESS   -> 12.99;
            case OVERNIGHT -> 24.99;
            case SAME_DAY  -> 39.99;
        };
    }

    /**
     * TODO 17: Implement this method.
     * Return a discount rate (0.0 to 1.0) based on ShippingTier.
     * - STANDARD and EXPRESS: no discount (0.0)
     * - OVERNIGHT: 10% discount if the order total > 200, else 5%
     * - SAME_DAY: 15% discount if order total > 500, else 8%
     *
     * Use a switch expression. For OVERNIGHT and SAME_DAY, use a block with yield.
     */
    public double discountRate(ShippingTier tier, double orderTotal) {
        return switch (tier) {
            case STANDARD, EXPRESS -> 0.0;  // multiple labels on one arrow case

            case OVERNIGHT -> {
                // Use yield to return from a block case
                if (orderTotal > 200) {
                    yield 0.10;
                } else {
                    yield 0.05;
                }
            }

            case SAME_DAY -> {
                // TODO: implement the SAME_DAY logic using yield
                if(orderTotal>500){
                    yield 0.15;
                } else{
                    yield 0.08;
                }
            }
        };
    }
}