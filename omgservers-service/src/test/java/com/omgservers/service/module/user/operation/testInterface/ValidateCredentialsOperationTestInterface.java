package com.omgservers.service.module.user.operation.testInterface;

import com.omgservers.model.user.UserModel;
import com.omgservers.service.module.user.impl.operation.validateCredentials.ValidateCredentialsOperation;
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
        return validateCredentialsOperation.validateCredentials(user, password)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }
}
