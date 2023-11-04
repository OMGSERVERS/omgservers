package com.omgservers.service.module.user.impl.operation.validateCredentials;

import com.omgservers.model.user.UserModel;
import io.smallrye.mutiny.Uni;

import java.time.Duration;

public interface ValidateCredentialsOperation {
    Uni<UserModel> validateCredentials(UserModel user, String password);

    default UserModel validateCredentials(long timeout, UserModel user, String password) {
        return validateCredentials(user, password)
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
