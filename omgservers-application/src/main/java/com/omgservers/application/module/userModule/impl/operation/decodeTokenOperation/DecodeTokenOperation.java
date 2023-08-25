package com.omgservers.application.module.userModule.impl.operation.decodeTokenOperation;

import com.omgservers.model.user.UserTokenModel;

public interface DecodeTokenOperation {
    UserTokenModel decodeToken(String rawToken);
}
