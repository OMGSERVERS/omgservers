package com.omgservers.service.entrypoint.support.impl.service.supportService.impl.method;

import com.omgservers.schema.entrypoint.support.CreateTokenSupportRequest;
import com.omgservers.schema.entrypoint.support.CreateTokenSupportResponse;
import com.omgservers.schema.module.user.CreateTokenRequest;
import com.omgservers.schema.module.user.CreateTokenResponse;
import com.omgservers.service.shard.alias.AliasShard;
import com.omgservers.service.shard.user.UserShard;
import com.omgservers.service.operation.alias.GetIdByUserOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class CreateTokenMethodImpl implements CreateTokenMethod {

    final AliasShard aliasShard;
    final UserShard userShard;

    final GetIdByUserOperation getIdByUserOperation;

    @Override
    public Uni<CreateTokenSupportResponse> execute(final CreateTokenSupportRequest request) {
        log.trace("{}", request);

        final var user = request.getUser();
        return getIdByUserOperation.execute(user)
                .flatMap(userId -> {
                    final var password = request.getPassword();
                    return createToken(userId, password)
                            .invoke(token -> log.info("Token issued for support user \"{}\"", user))
                            .map(CreateTokenSupportResponse::new);
                });
    }

    Uni<String> createToken(final Long userId, final String password) {
        final var createTokenRequest = new CreateTokenRequest(userId, password);
        return userShard.getService().execute(createTokenRequest)
                .map(CreateTokenResponse::getRawToken);
    }
}
