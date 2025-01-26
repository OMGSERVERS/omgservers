package com.omgservers.service.service.jenkins.operation;

import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.service.exception.ServerSideInternalException;
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
        final var tokenOptional = getServiceConfigOperation.getServiceConfig().builder().token();
        if (tokenOptional.isPresent()) {
            final var token = tokenOptional.get();
            final var plainCredentials = username + ":" + token;
            multivaluedHashMap.add("Authorization", "Basic " + Base64.getEncoder().encodeToString(plainCredentials
                    .getBytes(StandardCharsets.UTF_8)));
            return Uni.createFrom().item(multivaluedHashMap);
        } else {
            throw new ServerSideInternalException(ExceptionQualifierEnum.WRONG_CONFIGURATION,
                    "builder token is not set");
        }
    }
}
