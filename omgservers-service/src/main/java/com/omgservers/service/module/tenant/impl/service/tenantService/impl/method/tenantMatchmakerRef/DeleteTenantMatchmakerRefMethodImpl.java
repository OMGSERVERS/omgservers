package com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenantMatchmakerRef;

import com.omgservers.schema.module.tenant.tenantMatchmakerRef.DeleteTenantMatchmakerRefRequest;
import com.omgservers.schema.module.tenant.tenantMatchmakerRef.DeleteTenantMatchmakerRefResponse;
import com.omgservers.service.module.tenant.impl.operation.tenantMatchmakerRef.DeleteTenantMatchmakerRefOperation;
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
class DeleteTenantMatchmakerRefMethodImpl implements DeleteTenantMatchmakerRefMethod {

    final DeleteTenantMatchmakerRefOperation deleteTenantMatchmakerRefOperation;
    final ChangeWithContextOperation changeWithContextOperation;
    final CheckShardOperation checkShardOperation;

    @Override
    public Uni<DeleteTenantMatchmakerRefResponse> execute(
            final DeleteTenantMatchmakerRefRequest request) {
        log.debug("Delete tenant matchmaker ref, request={}", request);

        final var tenantId = request.getTenantId();
        final var id = request.getId();

        return Uni.createFrom().voidItem()
                .flatMap(voidItem -> checkShardOperation.checkShard(request.getRequestShardKey()))
                .flatMap(shardModel -> changeWithContextOperation.<Boolean>changeWithContext(
                                (changeContext, sqlConnection) ->
                                        deleteTenantMatchmakerRefOperation.execute(changeContext,
                                                sqlConnection,
                                                shardModel.shard(),
                                                tenantId,
                                                id))
                        .map(ChangeContext::getResult))
                .map(DeleteTenantMatchmakerRefResponse::new);
    }
}
