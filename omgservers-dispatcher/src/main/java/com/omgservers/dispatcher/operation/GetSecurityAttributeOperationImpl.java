package com.omgservers.dispatcher.operation;

import com.omgservers.dispatcher.security.ServiceSecurityAttributesEnum;
import com.omgservers.schema.model.user.UserRoleEnum;
import io.quarkus.security.identity.SecurityIdentity;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class GetSecurityAttributeOperationImpl implements GetSecurityAttributeOperation {

    final SecurityIdentity securityIdentity;

    @Override
    public Long getRuntimeId() {
        final var runtimeId = securityIdentity
                .<Long>getAttribute(ServiceSecurityAttributesEnum.RUNTIME_ID.getAttributeName());
        return runtimeId;
    }

    @Override
    public UserRoleEnum getUserRole() {
        final var userRole = securityIdentity
                .<UserRoleEnum>getAttribute(ServiceSecurityAttributesEnum.USER_ROLE.getAttributeName());
        return userRole;
    }

    @Override
    public Long getSubject() {
        final var subject = securityIdentity
                .<Long>getAttribute(ServiceSecurityAttributesEnum.SUBJECT.getAttributeName());
        return subject;
    }
}
