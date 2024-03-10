package com.omgservers.service.module.user.impl.operation.encodeToken;

import com.omgservers.model.user.UserTokenModel;

public interface EncodeTokenOperation {
    String encodeToken(UserTokenModel userToken);
}
