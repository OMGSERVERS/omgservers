package com.omgservers.service.factory;

import com.omgservers.model.token.TokenModel;
import com.omgservers.model.user.UserTokenContainerModel;
import com.omgservers.service.operation.generateId.GenerateIdOperation;
import io.quarkus.elytron.security.common.BcryptUtil;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class TokenModelFactory {

    final GenerateIdOperation generateIdOperation;

    public TokenModel create(final UserTokenContainerModel tokenContainer) {
        final var idempotencyKey = UUID.randomUUID().toString();
        return create(tokenContainer, idempotencyKey);
    }

    public TokenModel create(final UserTokenContainerModel tokenContainer,
                             final String idempotencyKey) {
        final var now = Instant.now().truncatedTo(ChronoUnit.MILLIS);

        final var tokenHash = BcryptUtil.bcryptHash(tokenContainer.getRawToken());
        final var tokenObject = tokenContainer.getTokenObject();

        final var token = new TokenModel();
        token.setId(tokenObject.getId());
        token.setIdempotencyKey(idempotencyKey);
        token.setUserId(tokenObject.getUserId());
        token.setCreated(now);
        token.setModified(now);
        token.setExpire(now.plusSeconds(tokenContainer.getLifetime()));
        token.setHash(tokenHash);
        token.setDeleted(false);

        return token;
    }
}
