package com.omgservers.service.service.jenkins.operation;

import com.omgservers.service.operation.getServiceConfig.GetServiceConfigOperation;
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
        final var username = getServiceConfigOperation.getServiceConfig().builder().userId();
        final var userToken = getServiceConfigOperation.getServiceConfig().builder().userToken();
        final var plainCredentials = username + ":" + userToken;
        multivaluedHashMap.add("Authorization", "Basic " + Base64.getEncoder().encodeToString(plainCredentials
                .getBytes(StandardCharsets.UTF_8)));
        return Uni.createFrom().item(multivaluedHashMap);
    }
}
