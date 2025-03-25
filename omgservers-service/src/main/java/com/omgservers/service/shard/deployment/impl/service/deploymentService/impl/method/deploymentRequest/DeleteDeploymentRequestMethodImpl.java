package com.omgservers.service.shard.deployment.impl.service.deploymentService.impl.method.deploymentRequest;

import com.omgservers.schema.module.deployment.deploymentRequest.DeleteDeploymentRequestRequest;
import com.omgservers.schema.module.deployment.deploymentRequest.DeleteDeploymentRequestResponse;
import com.omgservers.service.operation.server.ChangeContext;
import com.omgservers.service.operation.server.ChangeWithContextOperation;
import com.omgservers.service.operation.server.CheckShardOperation;
import com.omgservers.service.shard.deployment.impl.operation.deploymentRequest.DeleteDeploymentRequestOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = lombok.AccessLevel.PACKAGE)
class DeleteDeploymentRequestMethodImpl implements DeleteDeploymentRequestMethod {

    final DeleteDeploymentRequestOperation deleteDeploymentRequestOperation;
    final ChangeWithContextOperation changeWithContextOperation;
    final CheckShardOperation checkShardOperation;

    @Override
    public Uni<DeleteDeploymentRequestResponse> execute(
            final DeleteDeploymentRequestRequest request) {
        log.trace("{}", request);

        final var deploymentId = request.getDeploymentId();
        final var id = request.getId();
        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shardModel -> changeWithContextOperation.<Boolean>changeWithContext(
                                (changeContext, sqlConnection) -> deleteDeploymentRequestOperation
                                        .execute(changeContext,
                                                sqlConnection,
                                                shardModel.shard(),
                                                deploymentId,
                                                id))
                        .map(ChangeContext::getResult))
                .map(DeleteDeploymentRequestResponse::new);
    }
}
