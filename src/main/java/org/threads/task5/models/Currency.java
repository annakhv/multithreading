package org.threads.task5.models;

public enum Currency {
    EUR("EUR"),USD("USD"),GEL("GEL");
    private String value;

    Currency(String value) {
        this.value = value;
    }
}
