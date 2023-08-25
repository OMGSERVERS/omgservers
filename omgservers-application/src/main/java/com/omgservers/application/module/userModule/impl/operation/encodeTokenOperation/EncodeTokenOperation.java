package com.omgservers.application.module.userModule.impl.operation.encodeTokenOperation;

import com.omgservers.model.user.UserTokenModel;

public interface EncodeTokenOperation {
    String encodeToken(UserTokenModel userTokenModel);
}
