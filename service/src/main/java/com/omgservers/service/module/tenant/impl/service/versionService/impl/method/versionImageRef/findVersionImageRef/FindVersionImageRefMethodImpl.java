package com.omgservers.service.module.tenant.impl.service.versionService.impl.method.versionImageRef.findVersionImageRef;

import com.omgservers.schema.module.tenant.versionImageRef.FindVersionImageRefRequest;
import com.omgservers.schema.module.tenant.versionImageRef.FindVersionImageRefResponse;
import com.omgservers.service.module.tenant.impl.operation.versionImageRef.selectVersionImageRefsByVersionIdAndQualifier.SelectVersionImageRefsByVersionIdAndQualifier;
import com.omgservers.service.server.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class FindVersionImageRefMethodImpl implements FindVersionImageRefMethod {

    final SelectVersionImageRefsByVersionIdAndQualifier selectVersionImageRefsByVersionIdAndQualifier;
    final CheckShardOperation checkShardOperation;
    final PgPool pgPool;

    @Override
    public Uni<FindVersionImageRefResponse> findVersionImageRef(final FindVersionImageRefRequest request) {
        log.debug("Find version image ref, request={}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shardModel -> {
                    final var tenantId = request.getTenantId();
                    final var versionId = request.getVersionId();
                    final var qualifier = request.getQualifier();
                    return pgPool.withTransaction(sqlConnection -> selectVersionImageRefsByVersionIdAndQualifier
                            .selectVersionImageRefsByVersionIdAndQualifier(sqlConnection,
                                    shardModel.shard(),
                                    tenantId,
                                    versionId,
                                    qualifier));
                })
                .map(FindVersionImageRefResponse::new);
    }
}
