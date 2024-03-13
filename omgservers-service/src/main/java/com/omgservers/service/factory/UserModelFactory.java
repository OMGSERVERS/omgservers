package com.omgservers.service.factory;

import com.omgservers.model.user.UserModel;
import com.omgservers.model.user.UserRoleEnum;
import com.omgservers.service.operation.generateId.GenerateIdOperation;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class UserModelFactory {

    final GenerateIdOperation generateIdOperation;

    public UserModel create(final UserRoleEnum role,
                            final String passwordHash) {
        final var id = generateIdOperation.generateId();
        final var idempotencyKey = UUID.randomUUID().toString();
        return create(id, role, passwordHash, idempotencyKey);
    }

    public UserModel create(final UserRoleEnum role,
                            final String passwordHash,
                            final String idempotencyKey) {
        final var id = generateIdOperation.generateId();
        return create(id, role, passwordHash, idempotencyKey);
    }

    public UserModel create(final Long id,
                            final UserRoleEnum role,
                            final String passwordHash,
                            final String idempotencyKey) {
        Instant now = Instant.now().truncatedTo(ChronoUnit.MILLIS);

        UserModel user = new UserModel();
        user.setId(id);
        user.setIdempotencyKey(idempotencyKey);
        user.setCreated(now);
        user.setModified(now);
        user.setRole(role);
        user.setPasswordHash(passwordHash);
        user.setDeleted(false);
        return user;
    }
}
