package com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantImage;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.module.tenant.tenantImage.DeleteTenantImageRequest;
import com.omgservers.schema.module.tenant.tenantImage.DeleteTenantImageResponse;
import com.omgservers.service.operation.server.ChangeContext;
import com.omgservers.service.operation.server.ChangeWithContextOperation;
import com.omgservers.service.shard.tenant.impl.operation.tenantImage.DeleteTenantImageOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class DeleteTenantImageMethodImpl implements DeleteTenantImageMethod {

    final DeleteTenantImageOperation deleteTenantImageOperation;
    final ChangeWithContextOperation changeWithContextOperation;

    @Override
    public Uni<DeleteTenantImageResponse> execute(final ShardModel shardModel,
                                                  final DeleteTenantImageRequest request) {
        log.trace("{}", request);

        final var tenantId = request.getTenantId();
        final var id = request.getId();

        return changeWithContextOperation.<Boolean>changeWithContext(
                        (changeContext, sqlConnection) ->
                                deleteTenantImageOperation.execute(changeContext,
                                        sqlConnection,
                                        shardModel.shard(),
                                        tenantId,
                                        id))
                .map(ChangeContext::getResult)
                .map(DeleteTenantImageResponse::new);
    }
}
