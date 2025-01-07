package com.omgservers.dispatcher.operation;

import com.omgservers.schema.model.user.UserRoleEnum;

public interface GetSecurityAttributeOperation {

    Long getRuntimeId();

    UserRoleEnum getUserRole();

    <T> T getSubject();
}
