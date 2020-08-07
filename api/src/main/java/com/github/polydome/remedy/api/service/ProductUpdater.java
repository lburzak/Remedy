package com.github.polydome.remedy.api.service;

import com.github.polydome.remedy.api.repository.PackagingRepository;
import com.github.polydome.remedy.api.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ProductUpdater {
    private final ProductDataSource productDataSource;
    private final PackagingRepository packagingRepository;
    private final ProductRepository productRepository;
    private final Logger logger = LoggerFactory.getLogger(ProductUpdater.class);

    public ProductUpdater(ProductDataSource productDataSource, PackagingRepository packagingRepository, ProductRepository productRepository) {
        this.productDataSource = productDataSource;
        this.packagingRepository = packagingRepository;
        this.productRepository = productRepository;
    }

    @Scheduled(cron = "${productDataSource.cron}", zone = "${productDataSource.zone}")
    public void updateProducts() {
        logger.info("Updating products data...");

        productDataSource.fetchProducts(
                productRepository::save,
                packagingRepository::save
        );

        logger.info("Products updated");
    }
}
