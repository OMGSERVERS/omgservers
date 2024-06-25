package com.omgservers.service.entrypoint.support.impl.service.supportService.impl.method.deleteDeveloper;

import com.omgservers.model.dto.support.DeleteDeveloperSupportRequest;
import com.omgservers.model.dto.support.DeleteDeveloperSupportResponse;
import com.omgservers.model.dto.user.DeleteUserRequest;
import com.omgservers.model.dto.user.DeleteUserResponse;
import com.omgservers.service.module.user.UserModule;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class DeleteDeveloperMethodImpl implements DeleteDeveloperMethod {

    final UserModule userModule;

    @Override
    public Uni<DeleteDeveloperSupportResponse> deleteDeveloper(final DeleteDeveloperSupportRequest request) {
        log.debug("Delete developer, request={}", request);

        final var userId = request.getUserId();
        final var deleteUserRequest = new DeleteUserRequest(userId);
        return userModule.getUserService().deleteUser(deleteUserRequest)
                .map(DeleteUserResponse::getDeleted)
                .map(DeleteDeveloperSupportResponse::new);
    }
}
