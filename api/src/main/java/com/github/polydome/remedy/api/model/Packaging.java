package com.github.polydome.remedy.api.model;

import lombok.Data;

@Data
public class Packaging {
    private final long id;
    private final int productId;
    private final int size;
    private final String unit;
    private final String ean;
}
