package com.github.polydome.remedy.api.repository;

import com.github.polydome.remedy.api.model.Packaging;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PackagingRepository extends MongoRepository<Packaging, Long> {
    Packaging findFirstByEan(String ean);
}
