package com.omgservers.service.shard.user.operation.testInterface;

import com.omgservers.schema.model.user.UserModel;
import com.omgservers.service.shard.user.impl.operation.user.ValidateCredentialsOperation;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class ValidateCredentialsOperationTestInterface {
    private static final long TIMEOUT = 1L;

    final ValidateCredentialsOperation validateCredentialsOperation;

    public UserModel validateCredentials(final UserModel user,
                                         final String password) {
        return validateCredentialsOperation.execute(user, password)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }
}
