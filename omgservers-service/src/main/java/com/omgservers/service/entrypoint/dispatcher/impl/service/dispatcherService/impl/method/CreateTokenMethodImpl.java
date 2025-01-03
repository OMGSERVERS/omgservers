package com.omgservers.service.entrypoint.dispatcher.impl.service.dispatcherService.impl.method;

import com.omgservers.schema.entrypoint.dispatcher.CreateTokenDispatcherRequest;
import com.omgservers.schema.entrypoint.dispatcher.CreateTokenDispatcherResponse;
import com.omgservers.schema.module.user.CreateTokenRequest;
import com.omgservers.schema.module.user.CreateTokenResponse;
import com.omgservers.service.module.alias.AliasModule;
import com.omgservers.service.module.user.UserModule;
import com.omgservers.service.operation.getIdByUser.GetIdByUserOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class CreateTokenMethodImpl implements CreateTokenMethod {

    final AliasModule aliasModule;
    final UserModule userModule;

    final GetIdByUserOperation getIdByUserOperation;

    @Override
    public Uni<CreateTokenDispatcherResponse> execute(final CreateTokenDispatcherRequest request) {
        log.trace("Requested, {}", request);

        final var user = request.getUser();
        return getIdByUserOperation.execute(user)
                .flatMap(userId -> {
                    final var password = request.getPassword();
                    return createToken(userId, password)
                            .invoke(token -> log.info("A token was issued for the dispatcher user {}.", user))
                            .map(CreateTokenDispatcherResponse::new);
                });
    }

    Uni<String> createToken(final Long userId, final String password) {
        final var createTokenRequest = new CreateTokenRequest(userId, password);
        return userModule.getService().createToken(createTokenRequest)
                .map(CreateTokenResponse::getRawToken);
    }
}
