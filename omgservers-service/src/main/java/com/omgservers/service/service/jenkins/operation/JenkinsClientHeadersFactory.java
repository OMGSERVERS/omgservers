package com.omgservers.service.service.jenkins.operation;

import com.omgservers.service.operation.server.GetServiceConfigOperation;
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

    final GetServiceConfigOperation getServiceConfigOperation;

    @Override
    public Uni<MultivaluedMap<String, String>> getHeaders(MultivaluedMap<String, String> incomingHeaders,
                                                          MultivaluedMap<String, String> clientOutgoingHeaders) {
        final var multivaluedHashMap = new MultivaluedHashMap<String, String>();
        final var username = getServiceConfigOperation.getServiceConfig().builder().username();
        final var token = getServiceConfigOperation.getServiceConfig().builder().token();
        final var plainCredentials = username + ":" + token;
        multivaluedHashMap.add("Authorization", "Basic " + Base64.getEncoder().encodeToString(plainCredentials
                .getBytes(StandardCharsets.UTF_8)));
        return Uni.createFrom().item(multivaluedHashMap);
    }
}
