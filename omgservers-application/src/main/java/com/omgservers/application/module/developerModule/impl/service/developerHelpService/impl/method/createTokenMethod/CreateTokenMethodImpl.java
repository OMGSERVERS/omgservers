package com.omgservers.application.module.developerModule.impl.service.developerHelpService.impl.method.createTokenMethod;

import com.omgservers.dto.developerModule.CreateTokenDeveloperRequest;
import com.omgservers.dto.developerModule.CreateTokenDeveloperResponse;
import com.omgservers.application.module.userModule.UserModule;
import com.omgservers.dto.userModule.CreateTokenInternalRequest;
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
        CreateTokenDeveloperRequest.validate(request);

        final var userId = request.getUserId();
        final var password = request.getPassword();
        final var createTokenRequest = new CreateTokenInternalRequest(userId, password);
        return userModule.getTokenInternalService().createToken(createTokenRequest)
                .map(response -> {
                    // TODO: does role have to be "Developer" only, block others?
                    final var rawToken = response.getRawToken();
                    final var createTokenApiResponse = new CreateTokenDeveloperResponse(rawToken);
                    return createTokenApiResponse;
                });
    }
}
