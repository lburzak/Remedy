package com.github.polydome.remedy.api.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Product {
    private final int id;
    private final String name;
    private final String commonName;
    private final String potency;
    private final String form;
    private final String activeSubstance;
    private final String atc;
}
