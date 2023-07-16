package com.omgservers.application.module.userModule.impl.operation.decodeTokenOperation;

import com.omgservers.application.module.userModule.model.user.UserTokenModel;

public interface DecodeTokenOperation {
    UserTokenModel decodeToken(String rawToken);
}
