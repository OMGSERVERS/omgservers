package com.omgservers.application.module.developerModule.impl.service.developerHelpService.impl.method.createTokenDeveloperMethod;

import com.omgservers.application.module.developerModule.impl.service.developerHelpService.request.CreateTokenHelpRequest;
import com.omgservers.application.module.developerModule.impl.service.developerHelpService.response.CreateTokenHelpResponse;
import com.omgservers.application.module.userModule.UserModule;
import com.omgservers.application.module.userModule.impl.service.tokenInternalService.request.CreateTokenInternalRequest;
import io.smallrye.mutiny.Uni;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import jakarta.enterprise.context.ApplicationScoped;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class CreateTokenMethodImpl implements CreateTokenMethod {

    final UserModule userModule;

    @Override
    public Uni<CreateTokenHelpResponse> createToken(final CreateTokenHelpRequest request) {
        CreateTokenHelpRequest.validate(request);

        final var user = request.getUser();
        final var password = request.getPassword();
        final var createTokenRequest = new CreateTokenInternalRequest(user, password);
        return userModule.getTokenInternalService().createToken(createTokenRequest)
                .map(response -> {
                    // TODO: does role have to be "Developer" only, block others?
                    final var rawToken = response.getRawToken();
                    final var createTokenApiResponse = new CreateTokenHelpResponse(rawToken);
                    return createTokenApiResponse;
                });
    }
}
