package com.github.polydome.remedy.csioz.endpoint;

import java.io.IOException;
import java.io.InputStream;

public interface ProductRegistryEndpoint {
    InputStream fetchRegistryXml() throws IOException;
}
