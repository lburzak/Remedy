package com.github.polydome.remedy.api.service;

import com.github.polydome.remedy.api.model.Packaging;
import com.github.polydome.remedy.api.model.Product;
import reactor.core.publisher.Flux;

public interface ProductDataSource {
    void update();
    Flux<Product> products();
    Flux<Packaging> packagings();
}
