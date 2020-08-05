package com.github.polydome.remedy.service;

import com.github.polydome.remedy.repository.PackagingRepository;
import com.github.polydome.remedy.repository.ProductRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ProductUpdater {
    private final ProductDataSource productDataSource;
    private final PackagingRepository packagingRepository;
    private final ProductRepository productRepository;

    public ProductUpdater(ProductDataSource productDataSource, PackagingRepository packagingRepository, ProductRepository productRepository) {
        this.productDataSource = productDataSource;
        this.packagingRepository = packagingRepository;
        this.productRepository = productRepository;
    }

    @Scheduled(cron = "${productDataSource.cron}", zone = "${productDataSource.zone}")
    public void updateProducts() {
        productDataSource.packagings().subscribe(packagingRepository::save);
        productDataSource.products().subscribe(productRepository::save);
        productDataSource.update();
    }
}
