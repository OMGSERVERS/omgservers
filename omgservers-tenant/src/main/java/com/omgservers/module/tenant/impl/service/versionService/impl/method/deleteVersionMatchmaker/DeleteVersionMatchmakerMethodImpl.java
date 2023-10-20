package com.omgservers.module.tenant.impl.service.versionService.impl.method.deleteVersionMatchmaker;

import com.omgservers.dto.tenant.DeleteVersionMatchmakerRequest;
import com.omgservers.dto.tenant.DeleteVersionMatchmakerResponse;
import com.omgservers.module.system.SystemModule;
import com.omgservers.module.tenant.impl.operation.deleteVersionMatchmaker.DeleteVersionMatchmakerOperation;
import com.omgservers.operation.changeWithContext.ChangeContext;
import com.omgservers.operation.changeWithContext.ChangeWithContextOperation;
import com.omgservers.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class DeleteVersionMatchmakerMethodImpl implements DeleteVersionMatchmakerMethod {

    final SystemModule systemModule;

    final DeleteVersionMatchmakerOperation deleteVersionMatchmakerOperation;
    final ChangeWithContextOperation changeWithContextOperation;
    final CheckShardOperation checkShardOperation;

    @Override
    public Uni<DeleteVersionMatchmakerResponse> deleteVersionMatchmaker(final DeleteVersionMatchmakerRequest request) {
        final var tenantId = request.getTenantId();
        final var id = request.getId();

        return Uni.createFrom().voidItem()
                .flatMap(voidItem -> checkShardOperation.checkShard(request.getRequestShardKey()))
                .flatMap(shardModel -> changeWithContextOperation.<Boolean>changeWithContext(
                                (changeContext, sqlConnection) ->
                                        deleteVersionMatchmakerOperation.deleteVersionMatchmaker(changeContext,
                                                sqlConnection,
                                                shardModel.shard(),
                                                tenantId,
                                                id))
                        .map(ChangeContext::getResult))
                .map(DeleteVersionMatchmakerResponse::new);
    }
}
