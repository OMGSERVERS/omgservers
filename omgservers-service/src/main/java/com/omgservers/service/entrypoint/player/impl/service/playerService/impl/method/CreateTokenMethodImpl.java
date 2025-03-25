package com.omgservers.service.entrypoint.player.impl.service.playerService.impl.method;

import com.omgservers.schema.entrypoint.player.CreateTokenPlayerRequest;
import com.omgservers.schema.entrypoint.player.CreateTokenPlayerResponse;
import com.omgservers.schema.module.user.CreateTokenRequest;
import com.omgservers.schema.module.user.CreateTokenResponse;
import com.omgservers.service.factory.user.PlayerModelFactory;
import com.omgservers.service.shard.tenant.TenantShard;
import com.omgservers.service.shard.user.UserShard;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class CreateTokenMethodImpl implements CreateTokenMethod {

    final TenantShard tenantShard;
    final UserShard userShard;

    final PlayerModelFactory playerModelFactory;

    @Override
    public Uni<CreateTokenPlayerResponse> execute(final CreateTokenPlayerRequest request) {
        log.trace("{}", request);

        final var userId = request.getUserId();
        final var password = request.getPassword();

        return createToken(userId, password)
                .invoke(token -> log.info("Token issued for player user \"{}\"", userId))
                .map(CreateTokenPlayerResponse::new);
    }

    Uni<String> createToken(final Long userId, final String password) {
        final var createTokenRequest = new CreateTokenRequest(userId, password);
        return userShard.getService().createToken(createTokenRequest)
                .map(CreateTokenResponse::getRawToken);
    }

}
