package com.omgservers.connector.operation;

import com.omgservers.connector.security.SecurityAttributesEnum;
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
    public UserRoleEnum getUserRole() {
        final var userRole = securityIdentity
                .<UserRoleEnum>getAttribute(SecurityAttributesEnum.USER_ROLE.getAttributeName());
        return userRole;
    }

    @Override
    public Long getClientId() {
        final var clientId = securityIdentity
                .<Long>getAttribute(SecurityAttributesEnum.CLIENT_ID.getAttributeName());
        return clientId;
    }

    @Override
    public <T> T getSubject() {
        final var subject = securityIdentity
                .<T>getAttribute(SecurityAttributesEnum.SUBJECT.getAttributeName());
        return subject;
    }
}
