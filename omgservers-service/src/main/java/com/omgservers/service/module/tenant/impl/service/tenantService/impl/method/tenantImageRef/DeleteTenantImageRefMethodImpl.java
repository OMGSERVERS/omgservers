package com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenantImageRef;

import com.omgservers.schema.module.tenant.tenantImageRef.DeleteTenantImageRefRequest;
import com.omgservers.schema.module.tenant.tenantImageRef.DeleteTenantImageRefResponse;
import com.omgservers.service.module.tenant.impl.operation.tenantImageRef.DeleteTenantImageRefOperation;
import com.omgservers.service.operation.changeWithContext.ChangeContext;
import com.omgservers.service.operation.changeWithContext.ChangeWithContextOperation;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class DeleteTenantImageRefMethodImpl implements DeleteTenantImageRefMethod {

    final DeleteTenantImageRefOperation deleteTenantImageRefOperation;
    final ChangeWithContextOperation changeWithContextOperation;
    final CheckShardOperation checkShardOperation;

    @Override
    public Uni<DeleteTenantImageRefResponse> execute(final DeleteTenantImageRefRequest request) {
        log.debug("Delete tenant image ref, request={}", request);

        final var tenantId = request.getTenantId();
        final var id = request.getId();

        return Uni.createFrom().voidItem()
                .flatMap(voidItem -> checkShardOperation.checkShard(request.getRequestShardKey()))
                .flatMap(shardModel -> changeWithContextOperation.<Boolean>changeWithContext(
                                (changeContext, sqlConnection) ->
                                        deleteTenantImageRefOperation.execute(changeContext,
                                                sqlConnection,
                                                shardModel.shard(),
                                                tenantId,
                                                id))
                        .map(ChangeContext::getResult))
                .map(DeleteTenantImageRefResponse::new);
    }
}
