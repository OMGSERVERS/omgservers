package com.omgservers.service.module.user.impl.operation.validateCredentials;

import com.omgservers.model.user.UserModel;
import io.smallrye.mutiny.Uni;

public interface ValidateCredentialsOperation {
    Uni<UserModel> validateCredentials(UserModel user, String password);
}
