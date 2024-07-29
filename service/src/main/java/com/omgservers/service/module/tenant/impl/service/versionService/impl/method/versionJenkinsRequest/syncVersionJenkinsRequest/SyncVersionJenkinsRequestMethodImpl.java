package com.omgservers.service.module.tenant.impl.service.versionService.impl.method.versionJenkinsRequest.syncVersionJenkinsRequest;

import com.omgservers.schema.module.tenant.versionJenkinsRequest.SyncVersionJenkinsRequestRequest;
import com.omgservers.schema.module.tenant.versionJenkinsRequest.SyncVersionJenkinsRequestResponse;
import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.module.tenant.impl.operation.version.hasVersion.HasVersionOperation;
import com.omgservers.service.module.tenant.impl.operation.versionJenkinsRequest.upsertVersionJenkinsRequest.UpsertVersionJenkinsRequestOperation;
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
class SyncVersionJenkinsRequestMethodImpl implements SyncVersionJenkinsRequestMethod {

    final UpsertVersionJenkinsRequestOperation upsertVersionJenkinsRequestOperation;
    final ChangeWithContextOperation changeWithContextOperation;
    final CheckShardOperation checkShardOperation;
    final HasVersionOperation hasVersionOperation;

    final PgPool pgPool;

    @Override
    public Uni<SyncVersionJenkinsRequestResponse> syncVersionJenkinsRequest(
            final SyncVersionJenkinsRequestRequest request) {
        log.debug("Sync version jenkins request, request={}", request);

        final var shardKey = request.getRequestShardKey();
        final var versionJenkinsRequest = request.getVersionJenkinsRequest();
        final var tenantId = versionJenkinsRequest.getTenantId();
        final var versionId = versionJenkinsRequest.getVersionId();

        return Uni.createFrom().voidItem()
                .flatMap(voidItem -> checkShardOperation.checkShard(shardKey))
                .flatMap(shardModel -> {
                    final var shard = shardModel.shard();
                    return changeWithContextOperation.<Boolean>changeWithContext(
                                    (changeContext, sqlConnection) -> hasVersionOperation
                                            .hasVersion(sqlConnection, shard, tenantId, versionId)
                                            .flatMap(has -> {
                                                if (has) {
                                                    return upsertVersionJenkinsRequestOperation
                                                            .upsertVersionJenkinsRequest(changeContext,
                                                                    sqlConnection,
                                                                    shardModel.shard(),
                                                                    versionJenkinsRequest);
                                                } else {
                                                    throw new ServerSideNotFoundException(
                                                            ExceptionQualifierEnum.PARENT_NOT_FOUND,
                                                            "version does not exist or was deleted, id=" + versionId);
                                                }
                                            })

                            )
                            .map(ChangeContext::getResult);
                })
                .map(SyncVersionJenkinsRequestResponse::new);
    }
}
