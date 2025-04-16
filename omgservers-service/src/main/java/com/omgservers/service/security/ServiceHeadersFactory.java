package com.omgservers.service.security;

import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.service.exception.ServerSideInternalException;
import com.omgservers.service.operation.server.GetServiceConfigOperation;
import com.omgservers.service.server.state.StateService;
import com.omgservers.service.server.state.dto.GetServiceTokenRequest;
import io.quarkus.rest.client.reactive.ReactiveClientHeadersFactory;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.MultivaluedHashMap;
import jakarta.ws.rs.core.MultivaluedMap;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Slf4j
@ApplicationScoped
public class ServiceHeadersFactory extends ReactiveClientHeadersFactory {

    final GetServiceConfigOperation getServiceConfigOperation;
    final StateService stateService;

    volatile HeadersProvider headersProvider;

    public ServiceHeadersFactory(final StateService stateService,
                                 final GetServiceConfigOperation getServiceConfigOperation) {
        this.getServiceConfigOperation = getServiceConfigOperation;
        this.stateService = stateService;
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
        final var userAgent = getServiceConfigOperation.getServiceConfig().server().uri().getHost();
        final var serviceToken = stateService.execute(new GetServiceTokenRequest()).getServiceToken();

        if (Objects.nonNull(serviceToken)) {
            return new HeadersProvider(userAgent, serviceToken);
        } else {
            throw new ServerSideInternalException(ExceptionQualifierEnum.INTERNAL_EXCEPTION_OCCURRED,
                    "service token not issued yet");
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
