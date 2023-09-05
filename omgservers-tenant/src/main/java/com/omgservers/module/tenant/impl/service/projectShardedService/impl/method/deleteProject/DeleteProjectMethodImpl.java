package com.omgservers.module.tenant.impl.service.projectShardedService.impl.method.deleteProject;

import com.omgservers.operation.changeWithContext.ChangeContext;
import com.omgservers.dto.tenant.DeleteProjectShardedRequest;
import com.omgservers.model.shard.ShardModel;
import com.omgservers.module.tenant.impl.operation.deleteProject.DeleteProjectOperation;
import com.omgservers.operation.changeWithContext.ChangeWithContextOperation;
import com.omgservers.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class DeleteProjectMethodImpl implements DeleteProjectMethod {

    final ChangeWithContextOperation changeWithContextOperation;
    final DeleteProjectOperation deleteProjectOperation;
    final CheckShardOperation checkShardOperation;

    @Override
    public Uni<Void> deleteProject(final DeleteProjectShardedRequest request) {
        DeleteProjectShardedRequest.validate(request);

        final var tenantId = request.getTenantId();
        final var id = request.getId();

        return Uni.createFrom().voidItem()
                .flatMap(voidItem -> checkShardOperation.checkShard(request.getRequestShardKey()))
                .flatMap(shardModel -> changeFunction(shardModel, tenantId, id))
                //TODO implement response with deleted flag
                .replaceWithVoid();
    }

    Uni<Boolean> changeFunction(ShardModel shardModel, Long tenantId, Long id) {
        return changeWithContextOperation.<Boolean>changeWithContext((changeContext, sqlConnection) ->
                        deleteProjectOperation.deleteProject(changeContext, sqlConnection, shardModel.shard(), tenantId, id))
                .map(ChangeContext::getResult);
    }
}
