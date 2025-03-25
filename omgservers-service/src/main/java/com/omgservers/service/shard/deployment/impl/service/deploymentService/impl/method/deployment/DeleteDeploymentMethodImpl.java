package com.omgservers.service.shard.deployment.impl.service.deploymentService.impl.method.deployment;

import com.omgservers.schema.module.deployment.deployment.DeleteDeploymentRequest;
import com.omgservers.schema.module.deployment.deployment.DeleteDeploymentResponse;
import com.omgservers.service.operation.server.ChangeContext;
import com.omgservers.service.operation.server.ChangeWithContextOperation;
import com.omgservers.service.operation.server.CheckShardOperation;
import com.omgservers.service.shard.deployment.impl.operation.deployment.DeleteDeploymentOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class DeleteDeploymentMethodImpl implements DeleteDeploymentMethod {

    final ChangeWithContextOperation changeWithContextOperation;
    final DeleteDeploymentOperation deleteDeploymentOperation;
    final CheckShardOperation checkShardOperation;

    @Override
    public Uni<DeleteDeploymentResponse> execute(final DeleteDeploymentRequest request) {
        log.trace("{}", request);

        final var id = request.getId();

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shardModel -> changeWithContextOperation.<Boolean>changeWithContext(
                                (changeContext, sqlConnection) ->
                                        deleteDeploymentOperation.execute(changeContext,
                                                sqlConnection,
                                                shardModel.shard(),
                                                id))
                        .map(ChangeContext::getResult))
                .map(DeleteDeploymentResponse::new);
    }
}
