package com.omgservers.module.user.impl.operation.encodeToken;

import com.omgservers.model.user.UserTokenModel;

public interface EncodeTokenOperation {
    String encodeToken(UserTokenModel userTokenModel);
}
