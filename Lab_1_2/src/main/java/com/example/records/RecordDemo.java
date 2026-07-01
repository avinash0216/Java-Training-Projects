package com.example.records;

public class RecordDemo {

    public static void main(String[] args) {

        Product laptop = new Product("P001", "Laptop Pro", 1299.99, 42);

        // TODO 1: Print the product using its auto-generated toString()
        System.out.println(laptop);

        // TODO 2: Access individual fields using their accessor methods
        // Note: accessors in records match the field name exactly -- laptop.name(), NOT laptop.getName()
        System.out.println("Name: " + laptop.name());
        System.out.println("Price: " + laptop.price());

        // TODO 3: Demonstrate value equality
        Product laptopCopy = new Product("P001", "Laptop Pro", 1299.99, 42);
        System.out.println("Are they equal? " + laptop.equals(laptopCopy));   // should be true
        System.out.println("Same reference? " + (laptop == laptopCopy));       // should be false

        // TODO 4: Attempt to create a Product with a negative price.
        // Wrap it in a try/catch and print the exception message.
        try {
            Product invalid = new Product("P002", "Broken Item", -5.00, 10);
        } catch (IllegalArgumentException e) {
            System.out.println("Caught: " + e.getMessage());
        }

        // TODO 5: Attempt to create a Product with a null id and observe the result.
        try {
            Product invalid = new Product(null, "Invalid Product", 10.00, 5);
        } catch (IllegalArgumentException e) {
            System.out.println("Caught: " + e.getMessage());
        }

        // In RecordDemo.main():

        // TODO 6: Create a product, apply a price reduction using withPrice(),
        // and print both the original and the discounted version.
        // Confirm the original is unchanged.

        Product original = new Product("P003", "Wireless Headset", 249.99, 100);
        Product discounted = original.withPrice(199.99);

        System.out.println("Original:   " + original);
        System.out.println("Discounted: " + discounted);
    }
}