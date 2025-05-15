package com.omgservers.service.shard.user.impl.service.userService.impl.method.user;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.user.SyncUserRequest;
import com.omgservers.schema.shard.user.SyncUserResponse;
import com.omgservers.service.operation.server.ChangeContext;
import com.omgservers.service.operation.server.ChangeWithContextOperation;
import com.omgservers.service.shard.user.impl.operation.user.UpsertUserOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SyncUserMethodImpl implements SyncUserMethod {

    final ChangeWithContextOperation changeWithContextOperation;
    final UpsertUserOperation upsertUserOperation;

    @Override
    public Uni<SyncUserResponse> syncUser(final ShardModel shardModel,
                                          final SyncUserRequest request) {
        log.debug("{}", request);

        final var user = request.getUser();
        return changeWithContextOperation.<Boolean>changeWithContext(
                        (changeContext, sqlConnection) -> upsertUserOperation.execute(
                                changeContext,
                                sqlConnection,
                                shardModel.slot(),
                                user))
                .map(ChangeContext::getResult)
                .map(SyncUserResponse::new);
    }
}
