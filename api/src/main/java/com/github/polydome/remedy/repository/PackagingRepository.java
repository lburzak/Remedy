package com.github.polydome.remedy.repository;

import com.github.polydome.remedy.model.Packaging;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PackagingRepository extends MongoRepository<Packaging, Long> {
    Packaging findFirstByEan(String ean);
}
