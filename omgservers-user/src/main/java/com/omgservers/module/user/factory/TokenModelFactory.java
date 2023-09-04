package com.omgservers.module.user.factory;

import com.omgservers.model.token.TokenModel;
import com.omgservers.model.user.UserTokenContainerModel;
import com.omgservers.operation.generateId.GenerateIdOperation;
import io.quarkus.elytron.security.common.BcryptUtil;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class TokenModelFactory {

    final GenerateIdOperation generateIdOperation;

    public TokenModel create(UserTokenContainerModel tokenContainer) {
        final var now = Instant.now().truncatedTo(ChronoUnit.MILLIS);

        final var tokenHash = BcryptUtil.bcryptHash(tokenContainer.getRawToken());
        final var tokenObject = tokenContainer.getTokenObject();

        TokenModel tokenModel = new TokenModel();
        tokenModel.setId(tokenObject.getId());
        tokenModel.setUserId(tokenObject.getUserId());
        tokenModel.setCreated(now);
        tokenModel.setExpire(now.plusSeconds(tokenContainer.getLifetime()));
        tokenModel.setHash(tokenHash);

        return tokenModel;
    }


}
