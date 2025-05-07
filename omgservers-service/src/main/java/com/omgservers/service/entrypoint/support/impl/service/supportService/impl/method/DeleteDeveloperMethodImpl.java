package com.omgservers.service.entrypoint.support.impl.service.supportService.impl.method;

import com.omgservers.schema.entrypoint.support.DeleteDeveloperSupportRequest;
import com.omgservers.schema.entrypoint.support.DeleteDeveloperSupportResponse;
import com.omgservers.schema.shard.user.DeleteUserRequest;
import com.omgservers.schema.shard.user.DeleteUserResponse;
import com.omgservers.service.operation.alias.GetIdByUserOperation;
import com.omgservers.service.shard.user.UserShard;
import io.quarkus.security.identity.SecurityIdentity;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class DeleteDeveloperMethodImpl implements DeleteDeveloperMethod {

    final UserShard userShard;

    final GetIdByUserOperation getIdByUserOperation;

    final SecurityIdentity securityIdentity;

    @Override
    public Uni<DeleteDeveloperSupportResponse> execute(final DeleteDeveloperSupportRequest request) {
        log.info("Requested, {}", request);

        return getIdByUserOperation.execute(request.getUser())
                .flatMap(userId -> {
                    final var deleteUserRequest = new DeleteUserRequest(userId);
                    return userShard.getService().execute(deleteUserRequest)
                            .map(DeleteUserResponse::getDeleted)
                            .invoke(deleted -> {
                                if (deleted) {
                                    log.info("The developer user \"{}\" was deleted", userId);
                                }
                            })
                            .map(DeleteDeveloperSupportResponse::new);
                });
    }
}
