package com.omgservers.service.entrypoint.support.impl.service.supportService.impl.method;

import com.omgservers.schema.entrypoint.support.CreateDeveloperAliasSupportRequest;
import com.omgservers.schema.entrypoint.support.CreateDeveloperAliasSupportResponse;
import com.omgservers.schema.model.user.UserModel;
import com.omgservers.schema.shard.user.GetUserRequest;
import com.omgservers.schema.shard.user.GetUserResponse;
import com.omgservers.service.factory.user.UserModelFactory;
import com.omgservers.service.operation.alias.CreateUserAliasOperation;
import com.omgservers.service.security.SecurityAttributesEnum;
import com.omgservers.service.shard.user.UserShard;
import io.quarkus.security.identity.SecurityIdentity;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class CreateDeveloperAliasMethodImpl implements CreateDeveloperAliasMethod {

    final UserShard userShard;

    final CreateUserAliasOperation createUserAliasOperation;

    final UserModelFactory userModelFactory;
    final SecurityIdentity securityIdentity;

    @Override
    public Uni<CreateDeveloperAliasSupportResponse> execute(final CreateDeveloperAliasSupportRequest request) {
        log.info("Requested, {}", request);

        final var userId = securityIdentity
                .<Long>getAttribute(SecurityAttributesEnum.USER_ID.getAttributeName());

        final var developerUserId = request.getDeveloperUserId();
        return getUser(developerUserId)
                .flatMap(user -> {
                    final var aliasValue = request.getAlias();
                    return createUserAliasOperation.execute(developerUserId, aliasValue);
                })
                .map(CreateDeveloperAliasSupportResponse::new);
    }

    Uni<UserModel> getUser(final Long userId) {
        final var request = new GetUserRequest(userId);
        return userShard.getService().execute(request)
                .map(GetUserResponse::getUser);
    }
}
