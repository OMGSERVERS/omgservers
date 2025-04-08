package com.omgservers.service.shard.user.impl.operation.user;

import com.omgservers.schema.model.user.UserModel;
import io.smallrye.mutiny.Uni;

public interface ValidateCredentialsOperation {
    Uni<UserModel> execute(UserModel user, String password);
}
