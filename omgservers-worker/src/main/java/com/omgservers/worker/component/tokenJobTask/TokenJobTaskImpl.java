package com.omgservers.worker.component.tokenJobTask;

import com.omgservers.model.dto.worker.CreateTokenWorkerRequest;
import com.omgservers.model.dto.worker.CreateTokenWorkerResponse;
import com.omgservers.worker.component.tokenHolder.TokenContainer;
import com.omgservers.worker.module.service.ServiceModule;
import com.omgservers.worker.operation.getConfig.GetConfigOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class TokenJobTaskImpl implements TokenJobTask {

    static final Duration BACK_OFF = Duration.ofSeconds(1);

    final ServiceModule serviceModule;

    final GetConfigOperation getConfigOperation;

    final TokenContainer tokenContainer;

    @Override
    public Uni<Void> executeTask() {
        return refreshToken();
    }

    Uni<Void> refreshToken() {
        return createToken()
                .invoke(token -> {
                    tokenContainer.set(token);
                    log.info("Token was refreshed");
                })
                .replaceWithVoid()
                .onFailure()
                .retry().withBackOff(BACK_OFF, BACK_OFF)
                .indefinitely();
    }

    Uni<String> createToken() {
        final var userId = getConfigOperation.getWorkerConfig().userId();
        final var password = getConfigOperation.getWorkerConfig().password();
        final var request = new CreateTokenWorkerRequest(userId, password);
        return serviceModule.getWorkerService().createToken(request)
                .map(CreateTokenWorkerResponse::getRawToken)
                .map(token -> "Bearer " + token);
    }
}
