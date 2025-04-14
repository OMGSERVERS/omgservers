package com.omgservers.service.entrypoint.support.impl.service.supportService.impl.method;

import com.omgservers.schema.entrypoint.support.DeleteDeveloperSupportRequest;
import com.omgservers.schema.entrypoint.support.DeleteDeveloperSupportResponse;
import com.omgservers.schema.shard.user.DeleteUserRequest;
import com.omgservers.schema.shard.user.DeleteUserResponse;
import com.omgservers.service.shard.user.UserShard;
import com.omgservers.service.security.SecurityAttributesEnum;
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

    final SecurityIdentity securityIdentity;

    @Override
    public Uni<DeleteDeveloperSupportResponse> execute(final DeleteDeveloperSupportRequest request) {
        log.info("Requested, {}", request);

        final var userId = securityIdentity
                .<Long>getAttribute(SecurityAttributesEnum.USER_ID.getAttributeName());

        final var developerUserId = request.getUserId();
        final var deleteUserRequest = new DeleteUserRequest(developerUserId);
        return userShard.getService().execute(deleteUserRequest)
                .map(DeleteUserResponse::getDeleted)
                .invoke(deleted -> {
                    if (deleted) {
                        log.info("The developer user \"{}\" was deleted", developerUserId);
                    }
                })
                .map(DeleteDeveloperSupportResponse::new);
    }
}
