package com.github.polydome.remedy.api.repository;

import com.github.polydome.remedy.api.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product, Integer> {

}
