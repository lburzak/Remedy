package com.github.polydome.remedy.csioz;

import com.github.polydome.remedy.api.model.Packaging;
import com.github.polydome.remedy.api.model.Product;
import com.github.polydome.remedy.csioz.endpoint.ProductRegistryEndpoint;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.ByteArrayInputStream;
import java.util.Collection;
import java.util.LinkedList;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CsiozProductDataSourceTest {
    CsiozProductDataSource SUT;

    final Product product1 = Product.builder()
            .name("Zoledronic acid Fresenius Kabi")
            .commonName("Acidum zoledronicum")
            .potency("4 mg/5 ml")
            .form("koncentrat do sporządzania roztworu do infuzji")
            .atc("M05BA08")
            .id(100000014)
            .activeSubstance("Acidum zoledronicum")
            .build();

    final Product product2 = Product.builder()
            .name("Edelan")
            .commonName("Mometasoni furoas")
            .potency("1 mg/g")
            .form("krem")
            .atc("D07AC13")
            .id(100000020)
            .activeSubstance("Mometasoni furoas")
            .build();

    final Packaging packaging1 = Packaging.builder()
            .size(1)
            .unit("fiol. 5 ml")
            .ean("05909991023652")
            .id(2)
            .productId(product1.getId())
            .build();

    final Packaging packaging2 = Packaging.builder()
            .size(1)
            .unit("tuba 15 g")
            .ean("05909991023683")
            .id(5)
            .productId(product2.getId())
            .build();

    final Packaging packaging3 = Packaging.builder()
            .size(1)
            .unit("tuba 30 g")
            .ean("05909991023690")
            .id(6)
            .productId(product2.getId())
            .build();

    @BeforeEach
    void setUpOne() throws ParserConfigurationException, SAXException {
        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
        SAXParser saxParser = saxParserFactory.newSAXParser();
        ProductRegistryEndpoint productRegistryEndpoint = createProductRegistryEndpointStub();
        SUT = new CsiozProductDataSource(productRegistryEndpoint, saxParser);
    }

    @Test
    public void fetchProducts_validRegistry_productsAndPackagingsEmitted() {
        Collection<Product> emittedProducts = new LinkedList<>();
        Collection<Packaging> emittedPackagings = new LinkedList<>();

        SUT.fetchProducts(emittedProducts::add, emittedPackagings::add);

        Assertions.assertArrayEquals(emittedProducts.toArray(), new Product[]{
                product1, product2
        });

        Assertions.assertArrayEquals(emittedPackagings.toArray(), new Packaging[]{
                packaging1, packaging2, packaging3
        });
    }

    private ProductRegistryEndpoint createProductRegistryEndpointStub() {
        return () -> new ByteArrayInputStream(createValidXmlRegistry().getBytes());
    }

    private String createValidXmlRegistry() {
        return "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<produktyLecznicze stanNaDzien=\"2020-08-05\" xmlns=\"http://rejestrymedyczne.csioz.gov.pl/rpl/eksport-danych-v1.0\">\n" +
                "  <produktLeczniczy nazwaProduktu=\"Zoledronic acid Fresenius Kabi\" rodzajPreparatu=\"ludzki\" nazwaPowszechnieStosowana=\"Acidum zoledronicum\" moc=\"4 mg/5 ml\" postac=\"koncentrat do sporządzania roztworu do infuzji\" podmiotOdpowiedzialny=\"Fresenius Kabi Polska Sp. z o.o.\" typProcedury=\"DCP\" numerPozwolenia=\"20708\" waznoscPozwolenia=\"Bezterminowy\" kodATC=\"M05BA08\" id=\"100000014\">\n" +
                "    <substancjeCzynne>\n" +
                "      <substancjaCzynna>Acidum zoledronicum</substancjaCzynna>\n" +
                "    </substancjeCzynne>\n" +
                "    <opakowania>\n" +
                "      <opakowanie wielkosc=\"1\" jednostkaWielkosci=\"fiol. 5 ml\" kodEAN=\"05909991023652\" kategoriaDostepnosci=\"Rpz\" skasowane=\"NIE\" numerEu=\"\" dystrybutorRownolegly=\"\" id=\"2\" />\n" +
                "    </opakowania>\n" +
                "  </produktLeczniczy>\n" +
                "  <produktLeczniczy nazwaProduktu=\"Edelan\" rodzajPreparatu=\"ludzki\" nazwaPowszechnieStosowana=\"Mometasoni furoas\" moc=\"1 mg/g\" postac=\"krem\" podmiotOdpowiedzialny=\"Zakłady Farmaceutyczne POLPHARMA S.A.\" typProcedury=\"NAR\" numerPozwolenia=\"20899\" waznoscPozwolenia=\"Bezterminowy\" kodATC=\"D07AC13\" id=\"100000020\">\n" +
                "    <substancjeCzynne>\n" +
                "      <substancjaCzynna>Mometasoni furoas</substancjaCzynna>\n" +
                "    </substancjeCzynne>\n" +
                "    <opakowania>\n" +
                "      <opakowanie wielkosc=\"1\" jednostkaWielkosci=\"tuba 15 g\" kodEAN=\"05909991023683\" kategoriaDostepnosci=\"Rp\" skasowane=\"NIE\" numerEu=\"\" dystrybutorRownolegly=\"\" id=\"5\" />\n" +
                "      <opakowanie wielkosc=\"1\" jednostkaWielkosci=\"tuba 30 g\" kodEAN=\"05909991023690\" kategoriaDostepnosci=\"Rp\" skasowane=\"NIE\" numerEu=\"\" dystrybutorRownolegly=\"\" id=\"6\" />\n" +
                "    </opakowania>\n" +
                "  </produktLeczniczy>\n" +
                "</produktyLecznicze>";
    }
}