package com.example.catalog.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long categoryId;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "description", length = 500)
    private String description;

    @Column(name = "created_date")
    private LocalDate createdDate;

    // TODO 1: Add the @OneToMany mapping to the products field below.
    // Use mappedBy = "category" (this refers to the field name on Product, not the column name).
    // Use FetchType.LAZY -- this is the default but declare it explicitly so the intent is clear.
    // Use cascade = CascadeType.ALL so saving a Category also saves its Products.
    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Product> products = new ArrayList<>();

    // Constructors

    public Category() {}

    public Category(String name, String description) {
        this.name = name;
        this.description = description;
    }

    // Getters and setters

    public Long getCategoryId() { return categoryId; }
    public void setCategoryId(Long categoryId) { this.categoryId = categoryId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public LocalDate getCreatedDate() { return createdDate; }
    public void setCreatedDate(LocalDate createdDate) { this.createdDate = createdDate; }

    public List<Product> getProducts() { return products; }
    public void setProducts(List<Product> products) { this.products = products; }
}