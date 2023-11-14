package com.omgservers.service.module.tenant.impl.service.versionService.impl.method.syncVersionRuntime;

import com.omgservers.model.dto.tenant.SyncVersionRuntimeRequest;
import com.omgservers.model.dto.tenant.SyncVersionRuntimeResponse;
import com.omgservers.service.exception.ServerSideConflictException;
import com.omgservers.service.module.tenant.impl.operation.hasVersion.HasVersionOperation;
import com.omgservers.service.module.tenant.impl.operation.upsertVersionRuntime.UpsertVersionRuntimeOperation;
import com.omgservers.service.operation.changeWithContext.ChangeContext;
import com.omgservers.service.operation.changeWithContext.ChangeWithContextOperation;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SyncVersionRuntimeMethodImpl implements SyncVersionRuntimeMethod {

    final UpsertVersionRuntimeOperation upsertVersionRuntimeOperation;
    final ChangeWithContextOperation changeWithContextOperation;
    final CheckShardOperation checkShardOperation;
    final HasVersionOperation hasVersionOperation;

    final PgPool pgPool;

    @Override
    public Uni<SyncVersionRuntimeResponse> syncVersionRuntime(final SyncVersionRuntimeRequest request) {
        final var shardKey = request.getRequestShardKey();
        final var versionRuntime = request.getVersionRuntime();
        final var tenantId = versionRuntime.getTenantId();
        final var versionId = versionRuntime.getVersionId();

        return Uni.createFrom().voidItem()
                .flatMap(voidItem -> checkShardOperation.checkShard(shardKey))
                .flatMap(shardModel -> {
                    final var shard = shardModel.shard();
                    return changeWithContextOperation.<Boolean>changeWithContext(
                                    (changeContext, sqlConnection) -> hasVersionOperation
                                            .hasVersion(sqlConnection, shard, tenantId, versionId)
                                            .flatMap(has -> {
                                                if (has) {
                                                    return upsertVersionRuntimeOperation
                                                            .upsertVersionRuntime(changeContext,
                                                                    sqlConnection,
                                                                    shardModel.shard(),
                                                                    versionRuntime);
                                                } else {
                                                    throw new ServerSideConflictException(
                                                            "version does not exist or was deleted, id=" + versionId);
                                                }
                                            })

                            )
                            .map(ChangeContext::getResult);
                })
                .map(SyncVersionRuntimeResponse::new);
    }
}
