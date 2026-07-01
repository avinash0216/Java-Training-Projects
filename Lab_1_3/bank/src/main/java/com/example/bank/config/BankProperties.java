package com.example.bank.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import java.math.BigDecimal;

// @ConfigurationProperties binds all properties under the "bank" prefix
// to the fields of this class at application startup.
// The field names use camelCase in Java; Spring Boot automatically maps
// kebab-case property names (transaction-limit) to camelCase (transactionLimit).
@EnableConfigurationProperties(BankProperties.class)
@ConfigurationProperties(prefix = "bank")
public class BankProperties {

    // Defaults are used only if the property is absent from every config source.
    private BigDecimal transactionLimit = new BigDecimal("10000");
    private BigDecimal dailyLimit = new BigDecimal("25000");
    private String environmentLabel = "unknown";

    public BankProperties(){

    }

    // --- TODO 5: getters and setters ---
    // Spring Boot's binding mechanism requires standard JavaBean setters to
    // write the values, and getters for other classes to read them.
    // Use IntelliJ's generator: Alt+Insert (Windows/Linux) or Cmd+N (Mac)
    // → "Getter and Setter" → select all fields.
    public BigDecimal getTransactionLimit() { return transactionLimit; }
    public void setTransactionLimit(BigDecimal transactionLimit) { this.transactionLimit = transactionLimit; }

    public BigDecimal getDailyLimit() { return dailyLimit; }
    public void setDailyLimit(BigDecimal dailyLimit) { this.dailyLimit = dailyLimit; }

    public String getEnvironmentLabel() { return environmentLabel; }
    public void setEnvironmentLabel(String environmentLabel) { this.environmentLabel = environmentLabel; }
}