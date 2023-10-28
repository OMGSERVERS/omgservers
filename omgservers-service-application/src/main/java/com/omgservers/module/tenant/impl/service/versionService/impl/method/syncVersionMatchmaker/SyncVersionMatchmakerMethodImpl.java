package com.omgservers.module.tenant.impl.service.versionService.impl.method.syncVersionMatchmaker;

import com.omgservers.model.dto.tenant.SyncVersionMatchmakerRequest;
import com.omgservers.model.dto.tenant.SyncVersionMatchmakerResponse;
import com.omgservers.module.tenant.impl.operation.upsertVersionMatchmaker.UpsertVersionMatchmakerOperation;
import com.omgservers.operation.changeWithContext.ChangeContext;
import com.omgservers.operation.changeWithContext.ChangeWithContextOperation;
import com.omgservers.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SyncVersionMatchmakerMethodImpl implements SyncVersionMatchmakerMethod {

    final UpsertVersionMatchmakerOperation upsertVersionMatchmakerOperation;
    final ChangeWithContextOperation changeWithContextOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<SyncVersionMatchmakerResponse> syncVersionMatchmaker(final SyncVersionMatchmakerRequest request) {
        final var versionMatchmaker = request.getVersionMatchmaker();

        return Uni.createFrom().voidItem()
                .flatMap(voidItem -> checkShardOperation.checkShard(request.getRequestShardKey()))
                .flatMap(shardModel -> changeWithContextOperation.<Boolean>changeWithContext(
                                (changeContext, sqlConnection) ->
                                        upsertVersionMatchmakerOperation.upsertVersionMatchmaker(changeContext,
                                                sqlConnection,
                                                shardModel.shard(),
                                                versionMatchmaker
                                        )
                        )
                        .map(ChangeContext::getResult))
                .map(SyncVersionMatchmakerResponse::new);
    }
}
