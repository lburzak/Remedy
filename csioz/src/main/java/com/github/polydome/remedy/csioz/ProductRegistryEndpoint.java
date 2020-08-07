package com.github.polydome.remedy.csioz;

import java.io.IOException;
import java.io.InputStream;

public interface ProductRegistryEndpoint {
    InputStream fetchRegistryXml() throws IOException;
}
