package com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.method;

import com.omgservers.schema.entrypoint.developer.CreateTokenDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.CreateTokenDeveloperResponse;
import com.omgservers.schema.shard.user.CreateTokenRequest;
import com.omgservers.schema.shard.user.CreateTokenResponse;
import com.omgservers.service.operation.alias.GetIdByUserOperation;
import com.omgservers.service.shard.user.UserShard;
import io.quarkus.security.identity.SecurityIdentity;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class CreateTokenMethodImpl implements CreateTokenMethod {

    final UserShard userShard;

    final GetIdByUserOperation getIdByUserOperation;

    final SecurityIdentity securityIdentity;

    @Override
    public Uni<CreateTokenDeveloperResponse> execute(final CreateTokenDeveloperRequest request) {
        log.debug("{}", request);

        return getIdByUserOperation.execute(request.getUser())
                .flatMap(userId -> {
                    final var password = request.getPassword();
                    return createToken(userId, password)
                            .invoke(token -> log.info("Token issued for developer user \"{}\"", userId));
                })
                .map(CreateTokenDeveloperResponse::new);
    }

    Uni<String> createToken(final Long userId, final String password) {
        final var createTokenRequest = new CreateTokenRequest(userId, password);
        return userShard.getService().execute(createTokenRequest)
                .map(CreateTokenResponse::getRawToken);
    }
}
