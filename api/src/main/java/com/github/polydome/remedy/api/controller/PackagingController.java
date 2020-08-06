package com.github.polydome.remedy.api.controller;

import com.github.polydome.remedy.api.model.Packaging;
import com.github.polydome.remedy.api.repository.PackagingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/packaging")
public class PackagingController {
    private final PackagingRepository packagingRepository;

    @Autowired
    public PackagingController(PackagingRepository packagingRepository) {
        this.packagingRepository = packagingRepository;
    }

    @GetMapping
    @ResponseBody
    public Packaging getPackagings(@RequestParam(required = false) String ean) {
        return packagingRepository.findFirstByEan(ean);
    }
}
