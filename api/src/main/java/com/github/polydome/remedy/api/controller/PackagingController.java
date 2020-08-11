package com.github.polydome.remedy.api.controller;

import com.github.polydome.remedy.api.model.Packaging;
import com.github.polydome.remedy.api.repository.PackagingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/packaging")
public class PackagingController {
    private final PackagingRepository packagingRepository;
    private final Logger logger = LoggerFactory.getLogger(PackagingController.class);

    @Autowired
    public PackagingController(PackagingRepository packagingRepository) {
        this.packagingRepository = packagingRepository;
    }

    @GetMapping
    @ResponseBody
    public Packaging getPackagings(@RequestParam(required = false) String ean) {
        Packaging packaging = packagingRepository.findFirstByEan(ean);

        if (packaging == null) {
            throw new EntityNotFoundException(String.format("Packaging identified by [ean=%s] not found", ean));
        }

        return packaging;
    }
}
