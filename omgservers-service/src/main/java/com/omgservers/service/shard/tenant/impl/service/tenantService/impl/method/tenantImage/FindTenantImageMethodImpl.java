package com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantImage;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.tenant.tenantImage.FindTenantImageRequest;
import com.omgservers.schema.shard.tenant.tenantImage.FindTenantImageResponse;
import com.omgservers.service.shard.tenant.impl.operation.tenantImage.SelectTenantImageByTenantVersionIdAndQualifierOperation;
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
    final PgPool pgPool;

    @Override
    public Uni<FindTenantImageResponse> execute(final ShardModel shardModel,
                                                final FindTenantImageRequest request) {
        log.debug("{}", request);

        final var tenantId = request.getTenantId();
        final var tenantVersionId = request.getTenantVersionId();
        final var qualifier = request.getQualifier();
        return pgPool.withTransaction(sqlConnection ->
                        selectTenantImageByTenantVersionIdAndQualifierOperation.execute(sqlConnection,
                                shardModel.slot(),
                                tenantId,
                                tenantVersionId,
                                qualifier))
                .map(FindTenantImageResponse::new);
    }
}
