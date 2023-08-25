package com.omgservers.application.module.userModule.impl.operation.validateCredentialsOperation;

import com.omgservers.model.user.UserModel;
import io.smallrye.mutiny.Uni;

public interface ValidateCredentialsOperation {
    Uni<UserModel> validateCredentials(UserModel user, String password);
}
