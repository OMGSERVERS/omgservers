package com.omgservers.service.operation.security;

import com.omgservers.schema.model.user.UserRoleEnum;

public interface GetSecurityAttributeOperation {

    Long getRuntimeId();

    Long getUserId();

    UserRoleEnum getUserRole();

    <T> T getSubject();
}
