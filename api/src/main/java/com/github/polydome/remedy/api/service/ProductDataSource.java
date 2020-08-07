package com.github.polydome.remedy.api.service;

import com.github.polydome.remedy.api.model.Packaging;
import com.github.polydome.remedy.api.model.Product;

import java.util.function.Consumer;

public interface ProductDataSource {
    void fetchProducts(Consumer<Product> onProduct, Consumer<Packaging> onPackaging) throws DataSourceNotAvailableException;
}
