package com.omgservers.service.shard.user.impl.service.userService.impl.method.user.syncUser;

import com.omgservers.schema.module.user.SyncUserRequest;
import com.omgservers.schema.module.user.SyncUserResponse;
import com.omgservers.service.shard.user.impl.operation.user.upsertUser.UpsertUserOperation;
import com.omgservers.service.operation.server.ChangeContext;
import com.omgservers.service.operation.server.ChangeWithContextOperation;
import com.omgservers.service.operation.server.CheckShardOperation;
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
    final CheckShardOperation checkShardOperation;

    @Override
    public Uni<SyncUserResponse> syncUser(final SyncUserRequest request) {
        log.trace("{}", request);

        final var user = request.getUser();
        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shardModel -> changeWithContextOperation.<Boolean>changeWithContext(
                        (changeContext, sqlConnection) -> upsertUserOperation.upsertUser(
                                changeContext,
                                sqlConnection,
                                shardModel.shard(),
                                user)))
                .map(ChangeContext::getResult)
                .map(SyncUserResponse::new);
    }
}
