package com.omgservers.service.entrypoint.support.impl.service.supportService.impl.method;

import com.omgservers.schema.entrypoint.support.DeleteDeveloperSupportRequest;
import com.omgservers.schema.entrypoint.support.DeleteDeveloperSupportResponse;
import com.omgservers.schema.module.user.DeleteUserRequest;
import com.omgservers.schema.module.user.DeleteUserResponse;
import com.omgservers.service.module.user.UserModule;
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
        log.info("Requested, {}, principal={}", request, securityIdentity.getPrincipal().getName());

        final var userId = request.getUserId();
        final var deleteUserRequest = new DeleteUserRequest(userId);
        return userModule.getService().deleteUser(deleteUserRequest)
                .map(DeleteUserResponse::getDeleted)
                .map(DeleteDeveloperSupportResponse::new);
    }
}
