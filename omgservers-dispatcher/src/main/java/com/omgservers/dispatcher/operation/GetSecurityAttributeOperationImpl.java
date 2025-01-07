package com.omgservers.dispatcher.operation;

import com.omgservers.dispatcher.security.SecurityAttributesEnum;
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
                .<Long>getAttribute(SecurityAttributesEnum.RUNTIME_ID.getAttributeName());
        return runtimeId;
    }

    @Override
    public UserRoleEnum getUserRole() {
        final var userRole = securityIdentity
                .<UserRoleEnum>getAttribute(SecurityAttributesEnum.USER_ROLE.getAttributeName());
        return userRole;
    }

    @Override
    public <T> T getSubject() {
        final var subject = securityIdentity
                .<T>getAttribute(SecurityAttributesEnum.SUBJECT.getAttributeName());
        return subject;
    }
}
