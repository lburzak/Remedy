package com.github.polydome.remedy.csioz.http;

import com.github.polydome.remedy.csioz.ProductRegistryEndpoint;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.io.InputStream;

public class CsiozProductRegistryEndpoint implements ProductRegistryEndpoint {
    private final OkHttpClient httpClient;
    private final String registryUrl;

    public CsiozProductRegistryEndpoint(OkHttpClient httpClient, String registryUrl) {
        this.httpClient = httpClient;
        this.registryUrl = registryUrl;
    }

    @Override
    public InputStream fetchRegistryXml() throws IOException {
        Request request = new Request.Builder()
                .get()
                .url(registryUrl)
                .build();

        Response response = httpClient.newCall(request).execute();
        return response.body().byteStream();
    }
}
