package com.omgservers.service.entrypoint.support.impl.service.supportService.impl.method;

import com.omgservers.schema.entrypoint.support.DeleteDeveloperSupportRequest;
import com.omgservers.schema.entrypoint.support.DeleteDeveloperSupportResponse;
import com.omgservers.schema.module.user.DeleteUserRequest;
import com.omgservers.schema.module.user.DeleteUserResponse;
import com.omgservers.service.module.user.UserModule;
import com.omgservers.service.security.ServiceSecurityAttributesEnum;
import io.quarkus.security.identity.SecurityIdentity;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class DeleteDeveloperMethodImpl implements DeleteDeveloperMethod {

    final UserModule userModule;

    final SecurityIdentity securityIdentity;

    @Override
    public Uni<DeleteDeveloperSupportResponse> execute(final DeleteDeveloperSupportRequest request) {
        log.debug("Requested, {}, principal={}", request, securityIdentity.getPrincipal().getName());

        final var userId = securityIdentity
                .<Long>getAttribute(ServiceSecurityAttributesEnum.USER_ID.getAttributeName());

        final var developerUserId = request.getUserId();
        final var deleteUserRequest = new DeleteUserRequest(developerUserId);
        return userModule.getService().deleteUser(deleteUserRequest)
                .map(DeleteUserResponse::getDeleted)
                .invoke(deleted -> {
                    if (deleted) {
                        log.info("The developer user \"{}\" was deleted by the user {}", developerUserId, userId);
                    }
                })
                .map(DeleteDeveloperSupportResponse::new);
    }
}
