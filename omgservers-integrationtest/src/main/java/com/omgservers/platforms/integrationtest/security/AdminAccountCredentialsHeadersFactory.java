package com.omgservers.platforms.integrationtest.security;

import com.omgservers.platforms.integrationtest.operations.getConfigOperation.GetConfigOperation;
import io.quarkus.rest.client.reactive.ReactiveClientHeadersFactory;
import io.smallrye.mutiny.Uni;
import lombok.extern.slf4j.Slf4j;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.MultivaluedHashMap;
import jakarta.ws.rs.core.MultivaluedMap;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Slf4j
@ApplicationScoped
public class AdminAccountCredentialsHeadersFactory extends ReactiveClientHeadersFactory {

    final String authorizationHeaderValue;

    public AdminAccountCredentialsHeadersFactory(GetConfigOperation getConfigOperation) {
        var username = getConfigOperation.getConfig().tester().adminUsername();
        var password = getConfigOperation.getConfig().tester().adminPassword();
        var credential = username + ":" + password;
        authorizationHeaderValue = "Basic " + Base64.getUrlEncoder().encodeToString(credential.
                getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public Uni<MultivaluedMap<String, String>> getHeaders(MultivaluedMap<String, String> incomingHeaders,
                                                          MultivaluedMap<String, String> clientOutgoingHeaders) {
        return Uni.createFrom().item(() -> {
            MultivaluedHashMap<String, String> outgoingHeaders = new MultivaluedHashMap<>();
            outgoingHeaders.add("Authorization", authorizationHeaderValue);
            return outgoingHeaders;
        });
    }
}
