package com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.method.createToken;

import com.omgservers.model.dto.developer.CreateTokenDeveloperRequest;
import com.omgservers.model.dto.developer.CreateTokenDeveloperResponse;
import com.omgservers.model.dto.user.CreateTokenRequest;
import com.omgservers.service.module.user.UserModule;
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
    public Uni<CreateTokenDeveloperResponse> createToken(final CreateTokenDeveloperRequest request) {
        log.debug("Create tenant, request={}", request);

        final var userId = request.getUserId();
        final var password = request.getPassword();
        final var createTokenRequest = new CreateTokenRequest(userId, password);
        return userModule.getTokenService().createToken(createTokenRequest)
                .map(response -> {
                    // TODO: does role have to be "Developer" only, block others?
                    final var rawToken = response.getRawToken();
                    final var createTokenApiResponse = new CreateTokenDeveloperResponse(rawToken);
                    return createTokenApiResponse;
                });
    }
}
