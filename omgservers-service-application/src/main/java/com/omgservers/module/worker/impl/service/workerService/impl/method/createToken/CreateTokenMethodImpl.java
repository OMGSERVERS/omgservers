package com.omgservers.module.worker.impl.service.workerService.impl.method.createToken;

import com.omgservers.model.dto.user.CreateTokenRequest;
import com.omgservers.model.dto.worker.CreateTokenWorkerRequest;
import com.omgservers.model.dto.worker.CreateTokenWorkerResponse;
import com.omgservers.module.user.UserModule;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class CreateTokenMethodImpl implements CreateTokenMethod {

    final UserModule userModule;

    @Override
    public Uni<CreateTokenWorkerResponse> createToken(final CreateTokenWorkerRequest request) {
        final var userId = request.getUserId();
        final var password = request.getPassword();
        final var createTokenRequest = new CreateTokenRequest(userId, password);
        return userModule.getTokenService().createToken(createTokenRequest)
                .map(response -> {
                    final var tokenObject = response.getTokenObject();
                    final var rawToken = response.getRawToken();
                    final var lifetime = response.getLifetime();
                    return new CreateTokenWorkerResponse(tokenObject, rawToken, lifetime);
                });
    }
}
