package com.github.polydome.remedy.model;

import lombok.Data;

@Data
public class Product {
    private final int id;
    private final String name;
    private final String commonName;
    private final String potency;
    private final String form;
    private final String activeSubstance;
    private final String atc;
}
