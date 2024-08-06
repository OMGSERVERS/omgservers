package com.omgservers.service.server.service.jenkins.operation;

import com.omgservers.service.server.operation.getConfig.GetConfigOperation;
import io.quarkus.rest.client.reactive.ReactiveClientHeadersFactory;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.MultivaluedHashMap;
import jakarta.ws.rs.core.MultivaluedMap;
import lombok.AllArgsConstructor;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@ApplicationScoped
@AllArgsConstructor
public class JenkinsClientHeadersFactory extends ReactiveClientHeadersFactory {

    final GetConfigOperation getConfigOperation;

    @Override
    public Uni<MultivaluedMap<String, String>> getHeaders(MultivaluedMap<String, String> incomingHeaders,
                                                          MultivaluedMap<String, String> clientOutgoingHeaders) {
        final var multivaluedHashMap = new MultivaluedHashMap<String, String>();
        final var username = getConfigOperation.getServiceConfig().builder().userId();
        final var userToken = getConfigOperation.getServiceConfig().builder().userToken();
        final var plainCredentials = username + ":" + userToken;
        multivaluedHashMap.add("Authorization", "Basic " + Base64.getEncoder().encodeToString(plainCredentials
                .getBytes(StandardCharsets.UTF_8)));
        return Uni.createFrom().item(multivaluedHashMap);
    }
}
