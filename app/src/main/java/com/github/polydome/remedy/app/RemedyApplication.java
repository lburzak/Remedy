package com.github.polydome.remedy.app;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.inject.Named;
import java.util.Collection;
import java.util.Collections;

@SpringBootApplication
@EnableMongoRepositories(basePackages = "com.github.polydome.remedy.api.repository")
@ComponentScan(basePackages = {"com.github.polydome.remedy.api", "com.github.polydome.remedy.csioz"})
@EnableScheduling
public class RemedyApplication extends AbstractMongoClientConfiguration {

    @Override
    protected String getDatabaseName() {
        return "remedy";
    }

    @Override
    protected Collection<String> getMappingBasePackages() {
        return Collections.singleton("com.github.polydome.remedy.api.repository");
    }

    @Bean
    @Named("registryUrl")
    public String registryUrl(@Value("${productDataSource.registryUrl}") String registryUrl) {
        return registryUrl;
    }

    public static void main(String[] args) {
        SpringApplication.run(RemedyApplication.class, args);
    }

}
