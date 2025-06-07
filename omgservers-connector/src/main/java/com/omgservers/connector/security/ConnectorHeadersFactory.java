package com.omgservers.connector.security;

import com.omgservers.connector.exception.ServerSideInternalException;
import com.omgservers.connector.operation.ExecuteStateOperation;
import com.omgservers.connector.operation.GetUserAgentOperation;
import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import io.quarkus.rest.client.reactive.ReactiveClientHeadersFactory;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.MultivaluedHashMap;
import jakarta.ws.rs.core.MultivaluedMap;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Slf4j
@ApplicationScoped
public class ConnectorHeadersFactory extends ReactiveClientHeadersFactory {

    final ExecuteStateOperation executeStateOperation;
    final GetUserAgentOperation getUserAgentOperation;

    public ConnectorHeadersFactory(final ExecuteStateOperation executeStateOperation,
                                   final GetUserAgentOperation getUserAgentOperation) {
        this.executeStateOperation = executeStateOperation;
        this.getUserAgentOperation = getUserAgentOperation;
    }

    @Override
    public Uni<MultivaluedMap<String, String>> getHeaders(final MultivaluedMap<String, String> incomingHeaders,
                                                          final MultivaluedMap<String, String> clientOutgoingHeaders) {
        final var multivaluedHashMap = new MultivaluedHashMap<String, String>();

        final var connectorToken = executeStateOperation.getConnectorToken();
        if (Objects.nonNull(connectorToken)) {
            multivaluedHashMap.add("Authorization", "Bearer " + connectorToken);
        } else {
            throw new ServerSideInternalException(ExceptionQualifierEnum.INTERNAL_EXCEPTION_OCCURRED,
                    "connector token not issued yet");
        }

        final var userAgent = getUserAgentOperation.execute();
        multivaluedHashMap.add("User-Agent", userAgent);

        return Uni.createFrom().item(multivaluedHashMap);
    }
}
