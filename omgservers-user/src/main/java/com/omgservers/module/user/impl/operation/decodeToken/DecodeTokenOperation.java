package com.omgservers.module.user.impl.operation.decodeToken;

import com.omgservers.model.user.UserTokenModel;

public interface DecodeTokenOperation {
    UserTokenModel decodeToken(String rawToken);
}
