package com.github.polydome.remedy.csioz;

import com.github.polydome.remedy.api.model.Packaging;
import com.github.polydome.remedy.api.model.Product;
import com.github.polydome.remedy.api.service.DataSourceNotAvailableException;
import com.github.polydome.remedy.api.service.ProductDataSource;
import com.github.polydome.remedy.csioz.endpoint.ProductRegistryEndpoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import java.io.IOException;
import java.io.InputStream;
import java.util.function.Consumer;

@Service
public class CsiozProductDataSource implements ProductDataSource {
    private final ProductRegistryEndpoint productRegistryEndpoint;
    private final SAXParser saxParser;

    private final String TAG_PRODUCT = "produktLeczniczy";
    private final String TAG_ACTIVE_SUBSTANCE = "substancjaCzynna";
    private final String TAG_PACKAGING = "opakowanie";
    private final String ATTR_PRODUCT_NAME = "nazwaProduktu";
    private final String ATTR_PRODUCT_COMMON_NAME = "nazwaPowszechnieStosowana";
    private final String ATTR_PRODUCT_POTENCY = "moc";
    private final String ATTR_PRODUCT_FORM = "postac";
    private final String ATTR_PRODUCT_ATC = "kodATC";
    private final String ATTR_PRODUCT_ID = "id";
    private final String ATTR_PACKAGING_SIZE = "wielkosc";
    private final String ATTR_PACKAGING_UNIT = "jednostkaWielkosci";
    private final String ATTR_PACKAGING_EAN = "kodEAN";
    private final String ATTR_PACKAGING_ID = "id";

    @Autowired
    public CsiozProductDataSource(ProductRegistryEndpoint productRegistryEndpoint, SAXParser saxParser) {
        this.productRegistryEndpoint = productRegistryEndpoint;
        this.saxParser = saxParser;
    }

    @Override
    public void fetchProducts(Consumer<Product> onProduct, Consumer<Packaging> onPackaging) {
        InputStream xmlRegistry;
        try {
            xmlRegistry = productRegistryEndpoint.fetchRegistryXml();
        } catch (IOException e) {
            throw new DataSourceNotAvailableException("Unable to fetch products data");
        }

        final Product.ProductBuilder productBuilder = Product.builder();

        try {
            saxParser.parse(xmlRegistry, new DefaultHandler() {
                private boolean insideActiveSubstanceTag;
                int productId;

                @Override
                public void startElement(String uri, String localName, String qName, Attributes attributes) {
                    switch (qName) {
                        case TAG_PRODUCT:
                            productId = Integer.parseInt(attributes.getValue(ATTR_PRODUCT_ID));
                            setupProduct(productId, productBuilder, attributes);
                            break;
                        case TAG_ACTIVE_SUBSTANCE:
                            insideActiveSubstanceTag = true;
                            break;
                        case TAG_PACKAGING:
                            try {
                                onPackaging.accept(createPackaging(productId, attributes));
                            } catch (IllegalArgumentException e) {
                                System.out.printf("Malformed package: size=%s, unit=%s, ean=%s, id=%s%n",
                                    attributes.getValue(ATTR_PACKAGING_SIZE),
                                    attributes.getValue(ATTR_PACKAGING_UNIT),
                                    attributes.getValue(ATTR_PACKAGING_EAN),
                                    Long.parseLong(attributes.getValue(ATTR_PACKAGING_ID))
                                );
                            }
                            break;
                    }
                }

                @Override
                public void endElement(String uri, String localName, String qName) {
                    switch (qName) {
                        case TAG_PRODUCT:
                            onProduct.accept(productBuilder.build());
                            break;
                        case TAG_ACTIVE_SUBSTANCE:
                            insideActiveSubstanceTag = false;
                            break;
                    }
                }

                @Override
                public void characters(char[] ch, int start, int length) {
                    if (insideActiveSubstanceTag)
                        setupActiveSubstance(productBuilder, new String(ch, start, length));
                }
            });
        } catch (IOException e) {
            throw new DataSourceNotAvailableException("Unable to read registry");
        } catch (SAXException e) {
            throw new DataSourceNotAvailableException("Malformed registry");
        }
    }

    private void setupProduct(int productId, Product.ProductBuilder builder, Attributes attributes) {
        builder
                .id(productId)
                .name(attributes.getValue(ATTR_PRODUCT_NAME))
                .commonName(attributes.getValue(ATTR_PRODUCT_COMMON_NAME))
                .potency(attributes.getValue(ATTR_PRODUCT_POTENCY))
                .form(attributes.getValue(ATTR_PRODUCT_FORM))
                .atc(attributes.getValue(ATTR_PRODUCT_ATC));
    }

    private void setupActiveSubstance(Product.ProductBuilder builder, String activeSubstance) {
        builder.activeSubstance(activeSubstance);
    }

    private Packaging createPackaging(int productId, Attributes attributes) {
        return Packaging.builder()
                .productId(productId)
                .size(Integer.parseInt(attributes.getValue(ATTR_PACKAGING_SIZE)))
                .unit(attributes.getValue(ATTR_PACKAGING_UNIT))
                .ean(attributes.getValue(ATTR_PACKAGING_EAN))
                .id(Long.parseLong(attributes.getValue(ATTR_PACKAGING_ID)))
                .build();
    }
}
