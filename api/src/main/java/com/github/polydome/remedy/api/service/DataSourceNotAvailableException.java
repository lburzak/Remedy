package com.github.polydome.remedy.api.service;

public class DataSourceNotAvailableException extends RuntimeException {
    public DataSourceNotAvailableException(String message) {
        super(message);
    }
}
