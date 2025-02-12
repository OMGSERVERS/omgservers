package com.omgservers.service.operation.security;

import com.omgservers.schema.model.user.UserRoleEnum;

public interface GetSecurityAttributeOperation {

    Long getRuntimeId();

    UserRoleEnum getUserRole();

    <T> T getSubject();
}
