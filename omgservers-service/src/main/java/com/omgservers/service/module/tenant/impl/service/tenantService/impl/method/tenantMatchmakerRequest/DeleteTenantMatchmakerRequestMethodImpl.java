package com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenantMatchmakerRequest;

import com.omgservers.schema.module.tenant.tenantMatchmakerRequest.DeleteTenantMatchmakerRequestRequest;
import com.omgservers.schema.module.tenant.tenantMatchmakerRequest.DeleteTenantMatchmakerRequestResponse;
import com.omgservers.service.module.tenant.impl.operation.tenantMatchmakerRequest.DeleteTenantMatchmakerRequestOperation;
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
class DeleteTenantMatchmakerRequestMethodImpl implements DeleteTenantMatchmakerRequestMethod {

    final DeleteTenantMatchmakerRequestOperation deleteTenantMatchmakerRequestOperation;
    final ChangeWithContextOperation changeWithContextOperation;
    final CheckShardOperation checkShardOperation;

    @Override
    public Uni<DeleteTenantMatchmakerRequestResponse> execute(
            final DeleteTenantMatchmakerRequestRequest request) {
        log.debug("Delete tenant matchmaker request, request={}", request);

        final var tenantId = request.getTenantId();
        final var id = request.getId();

        return Uni.createFrom().voidItem()
                .flatMap(voidItem -> checkShardOperation.checkShard(request.getRequestShardKey()))
                .flatMap(shardModel -> changeWithContextOperation.<Boolean>changeWithContext(
                                (changeContext, sqlConnection) ->
                                        deleteTenantMatchmakerRequestOperation.execute(changeContext,
                                                sqlConnection,
                                                shardModel.shard(),
                                                tenantId,
                                                id))
                        .map(ChangeContext::getResult))
                .map(DeleteTenantMatchmakerRequestResponse::new);
    }
}