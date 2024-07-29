package com.omgservers.service.module.tenant.impl.service.versionService.impl.method.versionLobbyRequest.deleteVersionLobbyRequest;

import com.omgservers.schema.module.tenant.DeleteVersionLobbyRequestRequest;
import com.omgservers.schema.module.tenant.DeleteVersionLobbyRequestResponse;
import com.omgservers.service.module.system.SystemModule;
import com.omgservers.service.module.tenant.impl.operation.versionLobbyRequest.deleteVersionLobbyRequest.DeleteVersionLobbyRequestOperation;
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
class DeleteVersionLobbyRequestMethodImpl implements DeleteVersionLobbyRequestMethod {

    final SystemModule systemModule;

    final DeleteVersionLobbyRequestOperation deleteVersionLobbyRequestOperation;
    final ChangeWithContextOperation changeWithContextOperation;
    final CheckShardOperation checkShardOperation;

    @Override
    public Uni<DeleteVersionLobbyRequestResponse> deleteVersionLobbyRequest(
            final DeleteVersionLobbyRequestRequest request) {
        log.debug("Delete version lobby request, request={}", request);

        final var tenantId = request.getTenantId();
        final var id = request.getId();

        return Uni.createFrom().voidItem()
                .flatMap(voidItem -> checkShardOperation.checkShard(request.getRequestShardKey()))
                .flatMap(shardModel -> changeWithContextOperation.<Boolean>changeWithContext(
                                (changeContext, sqlConnection) ->
                                        deleteVersionLobbyRequestOperation.deleteVersionLobbyRequest(changeContext,
                                                sqlConnection,
                                                shardModel.shard(),
                                                tenantId,
                                                id))
                        .map(ChangeContext::getResult))
                .map(DeleteVersionLobbyRequestResponse::new);
    }
}
