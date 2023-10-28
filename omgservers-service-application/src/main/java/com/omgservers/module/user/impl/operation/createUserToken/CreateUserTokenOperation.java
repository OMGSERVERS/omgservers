package com.omgservers.module.user.impl.operation.createUserToken;

import com.omgservers.model.user.UserModel;
import com.omgservers.model.user.UserTokenContainerModel;

public interface CreateUserTokenOperation {
    UserTokenContainerModel createUserToken(UserModel user);
}
