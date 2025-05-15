package com.omgservers.service.shard.deployment.impl.service.deploymentService.impl.method.deployment;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.deployment.deployment.DeleteDeploymentRequest;
import com.omgservers.schema.shard.deployment.deployment.DeleteDeploymentResponse;
import com.omgservers.service.operation.server.ChangeContext;
import com.omgservers.service.operation.server.ChangeWithContextOperation;
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

    @Override
    public Uni<DeleteDeploymentResponse> execute(final ShardModel shardModel,
                                                 final DeleteDeploymentRequest request) {
        log.debug("{}", request);

        final var id = request.getId();

        return changeWithContextOperation.<Boolean>changeWithContext(
                        (changeContext, sqlConnection) ->
                                deleteDeploymentOperation.execute(changeContext,
                                        sqlConnection,
                                        shardModel.slot(),
                                        id))
                .map(ChangeContext::getResult)
                .map(DeleteDeploymentResponse::new);
    }
}
