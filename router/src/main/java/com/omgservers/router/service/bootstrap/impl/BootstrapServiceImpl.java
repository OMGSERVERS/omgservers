package com.omgservers.router.service.bootstrap.impl;

import com.omgservers.schema.entrypoint.router.CreateTokenRouterRequest;
import com.omgservers.schema.entrypoint.router.CreateTokenRouterResponse;
import com.omgservers.router.components.TokenContainer;
import com.omgservers.router.configuration.RouterPriorityConfiguration;
import com.omgservers.router.integration.ServiceIntegration;
import com.omgservers.router.operation.getConfig.GetConfigOperation;
import com.omgservers.router.service.bootstrap.BootstrapService;
import io.opentelemetry.instrumentation.annotations.WithSpan;
import io.quarkus.runtime.StartupEvent;
import io.smallrye.mutiny.Uni;
import jakarta.annotation.Priority;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class BootstrapServiceImpl implements BootstrapService {

    private static final Duration BACKOFF_INTERVAL = Duration.ofSeconds(1);
    private static final Duration MAX_BACKOFF = Duration.ofSeconds(1);

    final ServiceIntegration serviceIntegration;
    final GetConfigOperation getConfigOperation;

    final TokenContainer tokenContainer;

    @WithSpan
    void startup(
            @Observes @Priority(RouterPriorityConfiguration.START_UP_BOOTSTRAP_PRIORITY) final StartupEvent event) {
        bootstrap()
                .onFailure().retry().withBackOff(BACKOFF_INTERVAL, MAX_BACKOFF).indefinitely()
                .await().indefinitely();
    }

    @Override
    public Uni<Void> bootstrap() {
        return Uni.createFrom().voidItem()
                .flatMap(voidItem -> bootstrapServiceToken())
                .onFailure().transform(t -> {
                    log.error("Bootstrap failed, {}:{}", t.getClass().getSimpleName(), t.getMessage());
                    return t;
                });
    }

    Uni<Void> bootstrapServiceToken() {
        final var userId = getConfigOperation.getRouterConfig().userId();
        log.info("Bootstrap service token, userId={}", userId);

        final var password = getConfigOperation.getRouterConfig().password();
        final var request = new CreateTokenRouterRequest(userId, password);
        return serviceIntegration.getServiceService().createToken(request)
                .map(CreateTokenRouterResponse::getRawToken)
                .invoke(tokenContainer::set)
                .invoke(rawToken -> log.info("Service token was initialized"))
                .replaceWithVoid();
    }
}
