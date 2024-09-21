package com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenantImageRef;

import com.omgservers.schema.module.tenant.tenantImageRef.FindTenantImageRefRequest;
import com.omgservers.schema.module.tenant.tenantImageRef.FindTenantImageRefResponse;
import com.omgservers.service.module.tenant.impl.operation.tenantImageRef.SelectTenantImageRefsByTenantVersionIdAndQualifierOperation;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class FindTenantImageRefMethodImpl implements FindTenantImageRefMethod {

    final SelectTenantImageRefsByTenantVersionIdAndQualifierOperation
            selectTenantImageRefsByTenantVersionIdAndQualifierOperation;
    final CheckShardOperation checkShardOperation;
    final PgPool pgPool;

    @Override
    public Uni<FindTenantImageRefResponse> execute(final FindTenantImageRefRequest request) {
        log.debug("Find tenant image ref, request={}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shardModel -> {
                    final var tenantId = request.getTenantId();
                    final var versionId = request.getVersionId();
                    final var qualifier = request.getTenantImageRefQualifier();
                    return pgPool.withTransaction(sqlConnection -> selectTenantImageRefsByTenantVersionIdAndQualifierOperation
                            .execute(sqlConnection,
                                    shardModel.shard(),
                                    tenantId,
                                    versionId,
                                    qualifier));
                })
                .map(FindTenantImageRefResponse::new);
    }
}
