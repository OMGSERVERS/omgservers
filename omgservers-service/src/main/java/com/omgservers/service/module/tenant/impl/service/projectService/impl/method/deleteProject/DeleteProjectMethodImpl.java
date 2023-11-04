package com.omgservers.service.module.tenant.impl.service.projectService.impl.method.deleteProject;

import com.omgservers.model.dto.tenant.DeleteProjectRequest;
import com.omgservers.model.shard.ShardModel;
import com.omgservers.service.module.tenant.impl.operation.deleteProject.DeleteProjectOperation;
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
class DeleteProjectMethodImpl implements DeleteProjectMethod {

    final ChangeWithContextOperation changeWithContextOperation;
    final DeleteProjectOperation deleteProjectOperation;
    final CheckShardOperation checkShardOperation;

    @Override
    public Uni<Void> deleteProject(final DeleteProjectRequest request) {
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
