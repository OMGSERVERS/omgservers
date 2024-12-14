package com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenantImage;

import com.omgservers.schema.module.tenant.tenantImage.FindTenantImageRequest;
import com.omgservers.schema.module.tenant.tenantImage.FindTenantImageResponse;
import com.omgservers.service.module.tenant.impl.operation.tenantImage.SelectTenantImageByTenantVersionIdAndQualifierOperation;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class FindTenantImageMethodImpl implements FindTenantImageMethod {

    final SelectTenantImageByTenantVersionIdAndQualifierOperation
            selectTenantImageByTenantVersionIdAndQualifierOperation;
    final CheckShardOperation checkShardOperation;
    final PgPool pgPool;

    @Override
    public Uni<FindTenantImageResponse> execute(final FindTenantImageRequest request) {
        log.trace("Requested, {}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shardModel -> {
                    final var tenantId = request.getTenantId();
                    final var tenantVersionId = request.getTenantVersionId();
                    final var qualifier = request.getQualifier();
                    return pgPool.withTransaction(
                            sqlConnection -> selectTenantImageByTenantVersionIdAndQualifierOperation
                                    .execute(sqlConnection,
                                            shardModel.shard(),
                                            tenantId,
                                            tenantVersionId,
                                            qualifier));
                })
                .map(FindTenantImageResponse::new);
    }
}
