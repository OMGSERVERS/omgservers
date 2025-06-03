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

    volatile HeadersProvider headersProvider;

    public ConnectorHeadersFactory(final ExecuteStateOperation executeStateOperation,
                                   final GetUserAgentOperation getUserAgentOperation) {
        this.getUserAgentOperation = getUserAgentOperation;
        this.executeStateOperation = executeStateOperation;
    }

    @Override
    public Uni<MultivaluedMap<String, String>> getHeaders(final MultivaluedMap<String, String> incomingHeaders,
                                                          final MultivaluedMap<String, String> clientOutgoingHeaders) {
        if (headersProvider == null) {
            synchronized (this) {
                if (headersProvider == null) {
                    headersProvider = createHeadersProvider();
                }
            }
        }

        return headersProvider.provide();
    }

    HeadersProvider createHeadersProvider() {
        final var userAgent = getUserAgentOperation.execute();
        final var connectorToken = executeStateOperation.getConnectorToken();

        if (Objects.nonNull(connectorToken)) {
            return new HeadersProvider(userAgent, connectorToken);
        } else {
            throw new ServerSideInternalException(ExceptionQualifierEnum.INTERNAL_EXCEPTION_OCCURRED,
                    "connector token not issued yet");
        }
    }

    static class HeadersProvider {

        final String userAgent;
        final String bearerToken;

        public HeadersProvider(final String userAgent,
                               final String bearerToken) {
            this.userAgent = userAgent;
            this.bearerToken = bearerToken;
        }

        Uni<MultivaluedMap<String, String>> provide() {
            final var multivaluedHashMap = new MultivaluedHashMap<String, String>();
            multivaluedHashMap.add("User-Agent", userAgent);
            multivaluedHashMap.add("Authorization", "Bearer " + bearerToken);
            return Uni.createFrom().item(multivaluedHashMap);
        }
    }
}
