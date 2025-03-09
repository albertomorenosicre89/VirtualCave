package com.alberto.virtualcave.services;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import org.springframework.stereotype.Service;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

@Service
public class CurrencyApiWireMock {

    private static WireMockServer wireMockServer;
    private static final int PORT = 8081;

    public CurrencyApiWireMock() {
        //Hay que poner la ruta desde el proyecto padre si es dentro de un submodulo
        String wireMockFilesPath = "price/service/src/test/resources/wiremock";

        // Configurar el servidor WireMock en el puerto 8081
        wireMockServer = new WireMockServer(
                WireMockConfiguration.options()
                        .usingFilesUnderDirectory(wireMockFilesPath)  // Usar la ruta del submódulo
                        .port(PORT)
        );

        wireMockServer.start();

        configureWireMockStubs();
    }

    private static void configureWireMockStubs() {
        // Mockear la ruta GET /v1/currencies
        wireMockServer.stubFor(get(urlEqualTo("/v1/currencies"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("[\n" +
                                "  {\n" +
                                "    \"symbol\": \"€\",\n" +
                                "    \"code\": \"EUR\",\n" +
                                "    \"decimals\": 2\n" +
                                "  },\n" +
                                "  {\n" +
                                "    \"symbol\": \"$\",\n" +
                                "    \"code\": \"USD\",\n" +
                                "    \"decimals\": 2\n" +
                                "  }\n" +
                                "]"))); // Archivo JSON en src/test/resources/mappings/__files/

        // Mockear la ruta GET /v1/currencies/{currencyCode}
        wireMockServer.stubFor(get(urlPathMatching("/v1/currencies/EUR"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\n" +
                                "  \"symbol\": \"€\",\n" +
                                "  \"code\": \"EUR\",\n" +
                                "  \"decimals\": 2\n" +
                                "}")));


        wireMockServer.stubFor(get(urlPathMatching("/v1/currencies/USD"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\n" +
                                "  \"symbol\": \"$\",\n" +
                                "  \"code\": \"USD\",\n" +
                                "  \"decimals\": 2\n" +
                                "}")));
    }
}
