package com.omgservers.application.factory;

import com.omgservers.base.operation.generateId.GenerateIdOperation;
import com.omgservers.model.user.UserModel;
import com.omgservers.model.user.UserRoleEnum;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class UserModelFactory {

    final GenerateIdOperation generateIdOperation;

    public UserModel create(final UserRoleEnum role,
                            final String passwordHash) {
        final var id = generateIdOperation.generateId();
        return create(id, role, passwordHash);
    }

    public UserModel create(final Long id,
                            final UserRoleEnum role,
                            final String passwordHash) {
        Instant now = Instant.now();

        UserModel user = new UserModel();
        user.setId(id);
        user.setCreated(now);
        user.setModified(now);
        user.setRole(role);
        user.setPasswordHash(passwordHash);
        return user;
    }
}
