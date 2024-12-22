package com.omgservers.service.entrypoint.support.impl.service.supportService.impl.method;

import com.omgservers.schema.entrypoint.support.CreateTokenSupportRequest;
import com.omgservers.schema.entrypoint.support.CreateTokenSupportResponse;
import com.omgservers.schema.model.alias.AliasModel;
import com.omgservers.schema.module.alias.FindAliasRequest;
import com.omgservers.schema.module.alias.FindAliasResponse;
import com.omgservers.schema.module.user.CreateTokenRequest;
import com.omgservers.schema.module.user.CreateTokenResponse;
import com.omgservers.service.configuration.DefaultAliasConfiguration;
import com.omgservers.service.module.alias.AliasModule;
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

    final AliasModule aliasModule;
    final UserModule userModule;

    @Override
    public Uni<CreateTokenSupportResponse> execute(final CreateTokenSupportRequest request) {
        log.trace("Requested, {}", request);

        final var user = request.getUser();
        return getIdByUser(user)
                .flatMap(userId -> {
                    final var password = request.getPassword();
                    return createToken(userId, password)
                            .invoke(token -> log.info("A token was issued for support user {}", userId))
                            .map(CreateTokenSupportResponse::new);
                });
    }

    Uni<Long> getIdByUser(final String user) {
        try {
            final var userId = Long.valueOf(user);
            return Uni.createFrom().item(userId);
        } catch (NumberFormatException e) {
            return findUserAlias(user)
                    .map(AliasModel::getEntityId);
        }
    }

    Uni<AliasModel> findUserAlias(final String alias) {
        final var request = new FindAliasRequest(DefaultAliasConfiguration.GLOBAL_SHARD_KEY, alias);
        return aliasModule.getService().execute(request)
                .map(FindAliasResponse::getAlias);
    }

    Uni<String> createToken(final Long userId, final String password) {
        final var createTokenRequest = new CreateTokenRequest(userId, password);
        return userModule.getService().createToken(createTokenRequest)
                .map(CreateTokenResponse::getRawToken);
    }
}
