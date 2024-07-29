package com.omgservers.service.module.tenant.impl.service.versionService.impl.method.versionImageRef.syncVersionImageRef;

import com.omgservers.schema.module.tenant.versionImageRef.SyncVersionImageRefRequest;
import com.omgservers.schema.module.tenant.versionImageRef.SyncVersionImageRefResponse;
import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.module.tenant.impl.operation.version.hasVersion.HasVersionOperation;
import com.omgservers.service.module.tenant.impl.operation.versionImageRef.upsertVersionImageRef.UpsertVersionImageRefOperation;
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
class SyncVersionImageRefMethodImpl implements SyncVersionImageRefMethod {

    final UpsertVersionImageRefOperation upsertVersionImageRefOperation;
    final ChangeWithContextOperation changeWithContextOperation;
    final CheckShardOperation checkShardOperation;
    final HasVersionOperation hasVersionOperation;

    final PgPool pgPool;

    @Override
    public Uni<SyncVersionImageRefResponse> syncVersionImageRef(final SyncVersionImageRefRequest request) {
        log.debug("Sync version image ref, request={}", request);

        final var shardKey = request.getRequestShardKey();
        final var versionImageRef = request.getVersionImageRef();
        final var tenantId = versionImageRef.getTenantId();
        final var versionId = versionImageRef.getVersionId();

        return Uni.createFrom().voidItem()
                .flatMap(voidItem -> checkShardOperation.checkShard(shardKey))
                .flatMap(shardModel -> {
                    final var shard = shardModel.shard();
                    return changeWithContextOperation.<Boolean>changeWithContext(
                                    (changeContext, sqlConnection) -> hasVersionOperation
                                            .hasVersion(sqlConnection, shard, tenantId, versionId)
                                            .flatMap(has -> {
                                                if (has) {
                                                    return upsertVersionImageRefOperation
                                                            .upsertVersionImageRef(changeContext,
                                                                    sqlConnection,
                                                                    shardModel.shard(),
                                                                    versionImageRef);
                                                } else {
                                                    throw new ServerSideNotFoundException(
                                                            ExceptionQualifierEnum.PARENT_NOT_FOUND,
                                                            "version does not exist or was deleted, id=" + versionId);
                                                }
                                            })

                            )
                            .map(ChangeContext::getResult);
                })
                .map(SyncVersionImageRefResponse::new);
    }
}
