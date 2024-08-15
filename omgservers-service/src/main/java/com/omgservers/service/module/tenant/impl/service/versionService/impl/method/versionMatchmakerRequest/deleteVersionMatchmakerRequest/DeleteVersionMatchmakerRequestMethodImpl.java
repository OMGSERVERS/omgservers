package com.omgservers.service.module.tenant.impl.service.versionService.impl.method.versionMatchmakerRequest.deleteVersionMatchmakerRequest;

import com.omgservers.schema.module.tenant.DeleteVersionMatchmakerRequestRequest;
import com.omgservers.schema.module.tenant.DeleteVersionMatchmakerRequestResponse;
import com.omgservers.service.module.tenant.impl.operation.versionMatchmakerRequest.deleteVersionMatchmakerRequest.DeleteVersionMatchmakerRequestOperation;
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
class DeleteVersionMatchmakerRequestMethodImpl implements DeleteVersionMatchmakerRequestMethod {

    final DeleteVersionMatchmakerRequestOperation deleteVersionMatchmakerRequestOperation;
    final ChangeWithContextOperation changeWithContextOperation;
    final CheckShardOperation checkShardOperation;

    @Override
    public Uni<DeleteVersionMatchmakerRequestResponse> deleteVersionMatchmakerRequest(
            final DeleteVersionMatchmakerRequestRequest request) {
        log.debug("Delete version matchmaker request, request={}", request);

        final var tenantId = request.getTenantId();
        final var id = request.getId();

        return Uni.createFrom().voidItem()
                .flatMap(voidItem -> checkShardOperation.checkShard(request.getRequestShardKey()))
                .flatMap(shardModel -> changeWithContextOperation.<Boolean>changeWithContext(
                                (changeContext, sqlConnection) ->
                                        deleteVersionMatchmakerRequestOperation.deleteVersionMatchmakerRequest(changeContext,
                                                sqlConnection,
                                                shardModel.shard(),
                                                tenantId,
                                                id))
                        .map(ChangeContext::getResult))
                .map(DeleteVersionMatchmakerRequestResponse::new);
    }
}
