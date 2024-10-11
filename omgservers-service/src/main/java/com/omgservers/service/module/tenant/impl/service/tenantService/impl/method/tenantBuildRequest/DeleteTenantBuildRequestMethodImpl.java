package com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenantBuildRequest;

import com.omgservers.schema.module.tenant.tenantBuildRequest.DeleteTenantBuildRequestRequest;
import com.omgservers.schema.module.tenant.tenantBuildRequest.DeleteTenantBuildRequestResponse;
import com.omgservers.service.module.tenant.impl.operation.tenantBuildRequest.DeleteTenantBuildRequestOperation;
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
class DeleteTenantBuildRequestMethodImpl implements DeleteTenantBuildRequestMethod {

    final DeleteTenantBuildRequestOperation deleteTenantBuildRequestOperation;
    final ChangeWithContextOperation changeWithContextOperation;
    final CheckShardOperation checkShardOperation;

    @Override
    public Uni<DeleteTenantBuildRequestResponse> execute(
            final DeleteTenantBuildRequestRequest request) {
        log.debug("Delete tenant build request, request={}", request);

        final var tenantId = request.getTenantId();
        final var id = request.getId();

        return Uni.createFrom().voidItem()
                .flatMap(voidItem -> checkShardOperation.checkShard(request.getRequestShardKey()))
                .flatMap(shardModel -> changeWithContextOperation.<Boolean>changeWithContext(
                                (changeContext, sqlConnection) ->
                                        deleteTenantBuildRequestOperation.execute(changeContext,
                                                sqlConnection,
                                                shardModel.shard(),
                                                tenantId,
                                                id))
                        .map(ChangeContext::getResult))
                .map(DeleteTenantBuildRequestResponse::new);
    }
}
