package com.omgservers.service.entrypoint.support.impl.service.supportService.impl.method;

import com.omgservers.schema.entrypoint.support.CreateDeveloperAliasSupportRequest;
import com.omgservers.schema.entrypoint.support.CreateDeveloperAliasSupportResponse;
import com.omgservers.schema.model.user.UserModel;
import com.omgservers.schema.shard.user.GetUserRequest;
import com.omgservers.schema.shard.user.GetUserResponse;
import com.omgservers.service.operation.alias.CreateUserAliasOperation;
import com.omgservers.service.operation.alias.CreateUserAliasResult;
import com.omgservers.service.shard.user.UserShard;
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

    @Override
    public Uni<CreateDeveloperAliasSupportResponse> execute(final CreateDeveloperAliasSupportRequest request) {
        log.info("Requested, {}", request);

        final var developerUserId = request.getDeveloperUserId();
        return getUser(developerUserId)
                .flatMap(user -> {
                    final var aliasValue = request.getAlias();
                    return createUserAliasOperation.execute(developerUserId, aliasValue);
                })
                .map(CreateUserAliasResult::created)
                .map(CreateDeveloperAliasSupportResponse::new);
    }

    Uni<UserModel> getUser(final Long userId) {
        final var request = new GetUserRequest(userId);
        return userShard.getService().execute(request)
                .map(GetUserResponse::getUser);
    }
}
