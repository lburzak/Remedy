package com.github.polydome.remedy.service;

import com.github.polydome.remedy.model.Packaging;
import com.github.polydome.remedy.model.Product;
import reactor.core.publisher.Flux;

public interface ProductDataSource {
    void update();
    Flux<Product> products();
    Flux<Packaging> packagings();
}
