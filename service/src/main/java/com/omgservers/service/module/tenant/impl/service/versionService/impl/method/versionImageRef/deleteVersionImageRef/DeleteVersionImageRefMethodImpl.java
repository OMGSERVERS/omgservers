package com.omgservers.service.module.tenant.impl.service.versionService.impl.method.versionImageRef.deleteVersionImageRef;

import com.omgservers.schema.module.tenant.versionImageRef.DeleteVersionImageRefRequest;
import com.omgservers.schema.module.tenant.versionImageRef.DeleteVersionImageRefResponse;
import com.omgservers.service.module.tenant.impl.operation.versionImageRef.deleteVersionImageRef.DeleteVersionImageRefOperation;
import com.omgservers.service.server.operation.changeWithContext.ChangeContext;
import com.omgservers.service.server.operation.changeWithContext.ChangeWithContextOperation;
import com.omgservers.service.server.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class DeleteVersionImageRefMethodImpl implements DeleteVersionImageRefMethod {

    final DeleteVersionImageRefOperation deleteVersionImageRefOperation;
    final ChangeWithContextOperation changeWithContextOperation;
    final CheckShardOperation checkShardOperation;

    @Override
    public Uni<DeleteVersionImageRefResponse> deleteVersionImageRef(final DeleteVersionImageRefRequest request) {
        log.debug("Delete version image ref, request={}", request);

        final var tenantId = request.getTenantId();
        final var id = request.getId();

        return Uni.createFrom().voidItem()
                .flatMap(voidItem -> checkShardOperation.checkShard(request.getRequestShardKey()))
                .flatMap(shardModel -> changeWithContextOperation.<Boolean>changeWithContext(
                                (changeContext, sqlConnection) ->
                                        deleteVersionImageRefOperation.deleteVersionImageRef(changeContext,
                                                sqlConnection,
                                                shardModel.shard(),
                                                tenantId,
                                                id))
                        .map(ChangeContext::getResult))
                .map(DeleteVersionImageRefResponse::new);
    }
}
