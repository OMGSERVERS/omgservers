package com.omgservers.base.headersFactory;

import com.omgservers.base.impl.operation.getConfigOperation.GetConfigOperation;
import io.quarkus.rest.client.reactive.ReactiveClientHeadersFactory;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.MultivaluedHashMap;
import jakarta.ws.rs.core.MultivaluedMap;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Slf4j
@ApplicationScoped
public class ServiceAccountClientHeadersFactory extends ReactiveClientHeadersFactory {

    final String authorizationHeaderValue;

    public ServiceAccountClientHeadersFactory(GetConfigOperation getConfigOperation) {
        var username = getConfigOperation.getConfig().serviceUsername();
        var password = getConfigOperation.getConfig().servicePassword();
        var credential = username + ":" + password;
        authorizationHeaderValue = "Basic " + Base64.getUrlEncoder()
                .encodeToString(credential.getBytes(StandardCharsets.UTF_8));
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
