package com.omgservers.service.module.player.impl.service.playerService.impl.method.createToken;

import com.omgservers.model.dto.player.CreateTokenPlayerRequest;
import com.omgservers.model.dto.player.CreateTokenPlayerResponse;
import com.omgservers.service.factory.PlayerModelFactory;
import com.omgservers.service.module.tenant.TenantModule;
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

    final TenantModule tenantModule;
    final UserModule userModule;

    final PlayerModelFactory playerModelFactory;

    @Override
    public Uni<CreateTokenPlayerResponse> createToken(final CreateTokenPlayerRequest request) {
        log.debug("Create token, request={}", request);

        final var userId = request.getUserId();
        final var password = request.getPassword();

        return userModule.getShortcutService().createToken(userId, password)
                .map(CreateTokenPlayerResponse::new);
    }

}
