package com.omgservers.connector.operation;

import com.omgservers.schema.model.user.UserRoleEnum;

public interface GetSecurityAttributeOperation {

    UserRoleEnum getUserRole();

    Long getClientId();

    <T> T getSubject();
}
