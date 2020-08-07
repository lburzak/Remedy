package com.github.polydome.remedy.csioz.di;

import com.squareup.okhttp.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

@Configuration
public class CsiozConfig {
    @Bean
    public SAXParser saxParser(SAXParserFactory factory) throws ParserConfigurationException, SAXException {
        return factory.newSAXParser();
    }

    @Bean SAXParserFactory saxParserFactory() {
        return SAXParserFactory.newInstance();
    }

    @Bean
    OkHttpClient okHttpClient() {
        return new OkHttpClient();
    }
}
