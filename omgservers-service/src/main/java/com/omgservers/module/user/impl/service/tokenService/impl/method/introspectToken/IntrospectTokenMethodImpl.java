package com.omgservers.module.user.impl.service.tokenService.impl.method.introspectToken;

import com.omgservers.dto.user.IntrospectTokenRequest;
import com.omgservers.dto.user.IntrospectTokenResponse;
import com.omgservers.exception.ServerSideConflictException;
import com.omgservers.model.token.TokenModel;
import com.omgservers.model.user.UserTokenModel;
import com.omgservers.module.user.impl.operation.decodeToken.DecodeTokenOperation;
import com.omgservers.module.user.impl.operation.selectToken.SelectTokenOperation;
import com.omgservers.operation.checkShard.CheckShardOperation;
import io.quarkus.elytron.security.common.BcryptUtil;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.time.Instant;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class IntrospectTokenMethodImpl implements IntrospectTokenMethod {

    final DecodeTokenOperation decodeTokenOperation;
    final SelectTokenOperation selectTokenOperation;
    final CheckShardOperation checkShardOperation;
    final PgPool pgPool;

    @Override
    public Uni<IntrospectTokenResponse> introspectToken(final IntrospectTokenRequest request) {
        final var rawToken = request.getRawToken();
        final var tokenObject = decodeTokenOperation.decodeToken(rawToken);
        final var userUuid = tokenObject.getUserId();
        return checkShardOperation.checkShard(userUuid.toString())
                .flatMap(shard -> {
                    final var tokenUuid = tokenObject.getId();
                    return pgPool.withTransaction(sqlConnection -> selectTokenOperation
                            .selectToken(sqlConnection, shard.shard(), tokenUuid)
                            .map(token -> introspect(token, rawToken, tokenObject)));
                })
                .map(lifetime -> new IntrospectTokenResponse(tokenObject, lifetime));
    }

    Long introspect(TokenModel tokenModel, String rawToken, UserTokenModel tokenObject) {
        final var tokenHash = tokenModel.getHash();
        if (BcryptUtil.matches(rawToken, tokenHash)) {
            final var lifetime = Duration.between(Instant.now(), tokenModel.getExpire());
            if (lifetime.isNegative()) {
                log.warn("Token was expired, token={}, lifetime={}", tokenObject, lifetime);
            }
            return lifetime.toSeconds();
        } else {
            throw new ServerSideConflictException(String.format("Token hash mismatch, token=%s", tokenObject));
        }
    }
}
