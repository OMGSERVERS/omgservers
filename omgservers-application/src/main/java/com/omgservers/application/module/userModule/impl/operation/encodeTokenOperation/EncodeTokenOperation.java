package com.omgservers.application.module.userModule.impl.operation.encodeTokenOperation;

import com.omgservers.application.module.userModule.model.user.UserTokenModel;

public interface EncodeTokenOperation {
    String encodeToken(UserTokenModel userTokenModel);
}
