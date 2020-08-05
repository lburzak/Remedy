package com.github.polydome.remedy.repository;

import com.github.polydome.remedy.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product, Integer> {

}
