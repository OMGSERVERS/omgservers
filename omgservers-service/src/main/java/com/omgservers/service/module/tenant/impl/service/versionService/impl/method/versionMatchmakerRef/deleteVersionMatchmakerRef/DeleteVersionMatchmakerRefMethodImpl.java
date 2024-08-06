package com.omgservers.service.module.tenant.impl.service.versionService.impl.method.versionMatchmakerRef.deleteVersionMatchmakerRef;

import com.omgservers.schema.module.tenant.DeleteVersionMatchmakerRefRequest;
import com.omgservers.schema.module.tenant.DeleteVersionMatchmakerRefResponse;
import com.omgservers.service.module.tenant.impl.operation.versionMatchmakerRef.deleteVersionMatchmakerRef.DeleteVersionMatchmakerRefOperation;
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
class DeleteVersionMatchmakerRefMethodImpl implements DeleteVersionMatchmakerRefMethod {

    final DeleteVersionMatchmakerRefOperation deleteVersionMatchmakerRefOperation;
    final ChangeWithContextOperation changeWithContextOperation;
    final CheckShardOperation checkShardOperation;

    @Override
    public Uni<DeleteVersionMatchmakerRefResponse> deleteVersionMatchmakerRef(
            final DeleteVersionMatchmakerRefRequest request) {
        log.debug("Delete version matchmaker ref, request={}", request);

        final var tenantId = request.getTenantId();
        final var id = request.getId();

        return Uni.createFrom().voidItem()
                .flatMap(voidItem -> checkShardOperation.checkShard(request.getRequestShardKey()))
                .flatMap(shardModel -> changeWithContextOperation.<Boolean>changeWithContext(
                                (changeContext, sqlConnection) ->
                                        deleteVersionMatchmakerRefOperation.deleteVersionMatchmakerRef(changeContext,
                                                sqlConnection,
                                                shardModel.shard(),
                                                tenantId,
                                                id))
                        .map(ChangeContext::getResult))
                .map(DeleteVersionMatchmakerRefResponse::new);
    }
}
