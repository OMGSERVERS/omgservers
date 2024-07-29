package com.omgservers.service.module.tenant.impl.service.versionService.impl.method.versionMatchmakerRequest.syncVersionMatchmakerRequest;

import com.omgservers.schema.module.tenant.SyncVersionMatchmakerRequestRequest;
import com.omgservers.schema.module.tenant.SyncVersionMatchmakerRequestResponse;
import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.module.tenant.impl.operation.version.hasVersion.HasVersionOperation;
import com.omgservers.service.module.tenant.impl.operation.versionMatchmakerRequest.upsertVersionMatchmakerRequest.UpsertVersionMatchmakerRequestOperation;
import com.omgservers.service.server.operation.changeWithContext.ChangeContext;
import com.omgservers.service.server.operation.changeWithContext.ChangeWithContextOperation;
import com.omgservers.service.server.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SyncVersionMatchmakerRequestMethodImpl implements SyncVersionMatchmakerRequestMethod {

    final UpsertVersionMatchmakerRequestOperation upsertVersionMatchmakerRequestOperation;
    final ChangeWithContextOperation changeWithContextOperation;
    final CheckShardOperation checkShardOperation;
    final HasVersionOperation hasVersionOperation;

    final PgPool pgPool;

    @Override
    public Uni<SyncVersionMatchmakerRequestResponse> syncVersionMatchmakerRequest(
            final SyncVersionMatchmakerRequestRequest request) {
        log.debug("Sync version matchmaker request, request={}", request);

        final var shardKey = request.getRequestShardKey();
        final var versionMatchmakerRequest = request.getVersionMatchmakerRequest();
        final var tenantId = versionMatchmakerRequest.getTenantId();
        final var versionId = versionMatchmakerRequest.getVersionId();

        return Uni.createFrom().voidItem()
                .flatMap(voidItem -> checkShardOperation.checkShard(shardKey))
                .flatMap(shardModel -> {
                    final var shard = shardModel.shard();
                    return changeWithContextOperation.<Boolean>changeWithContext(
                                    (changeContext, sqlConnection) -> hasVersionOperation
                                            .hasVersion(sqlConnection, shard, tenantId, versionId)
                                            .flatMap(has -> {
                                                if (has) {
                                                    return upsertVersionMatchmakerRequestOperation
                                                            .upsertVersionMatchmakerRequest(
                                                                    changeContext,
                                                                    sqlConnection,
                                                                    shard,
                                                                    versionMatchmakerRequest);
                                                } else {
                                                    throw new ServerSideNotFoundException(
                                                            ExceptionQualifierEnum.PARENT_NOT_FOUND,
                                                            "version does not exist or was deleted, id=" + versionId);
                                                }
                                            })
                            )
                            .map(ChangeContext::getResult);
                })
                .map(SyncVersionMatchmakerRequestResponse::new);
    }
}
