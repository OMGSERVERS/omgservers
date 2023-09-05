package com.omgservers.module.user.impl.service.userInternalService.impl.method.syncUser;

import com.omgservers.operation.changeWithContext.ChangeContext;
import com.omgservers.dto.user.SyncUserShardedRequest;
import com.omgservers.dto.user.SyncUserShardedResponse;
import com.omgservers.module.user.impl.operation.upsertUser.UpsertUserOperation;
import com.omgservers.operation.changeWithContext.ChangeWithContextOperation;
import com.omgservers.operation.checkShard.CheckShardOperation;
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
    public Uni<SyncUserShardedResponse> syncUser(final SyncUserShardedRequest request) {
        SyncUserShardedRequest.validate(request);

        final var user = request.getUser();
        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shardModel -> changeWithContextOperation.<Boolean>changeWithContext(
                        (changeContext, sqlConnection) -> upsertUserOperation.upsertUser(
                                changeContext,
                                sqlConnection,
                                shardModel.shard(),
                                user)))
                .map(ChangeContext::getResult)
                .map(SyncUserShardedResponse::new);
    }
}
