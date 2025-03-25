package com.omgservers.service.shard.deployment.impl.service.deploymentService.impl.method.deploymentMatchmakerResource;

import com.omgservers.schema.module.deployment.deploymentMatchmakerResource.UpdateDeploymentMatchmakerResourceStatusRequest;
import com.omgservers.schema.module.deployment.deploymentMatchmakerResource.UpdateDeploymentMatchmakerResourceStatusResponse;
import com.omgservers.service.operation.server.ChangeContext;
import com.omgservers.service.operation.server.ChangeWithContextOperation;
import com.omgservers.service.operation.server.CheckShardOperation;
import com.omgservers.service.shard.deployment.impl.operation.deploymentMatchmakerResource.UpdateDeploymentMatchmakerResourceStatusOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class UpdateDeploymentMatchmakerResourceStatusMethodImpl implements UpdateDeploymentMatchmakerResourceStatusMethod {

    final UpdateDeploymentMatchmakerResourceStatusOperation updateDeploymentMatchmakerResourceStatusOperation;
    final ChangeWithContextOperation changeWithContextOperation;
    final CheckShardOperation checkShardOperation;

    @Override
    public Uni<UpdateDeploymentMatchmakerResourceStatusResponse> execute(final UpdateDeploymentMatchmakerResourceStatusRequest request) {
        log.trace("{}", request);

        final var deploymentId = request.getDeploymentId();
        final var id = request.getId();
        final var status = request.getStatus();
        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shardModel -> {
                    final var shard = shardModel.shard();
                    return changeWithContextOperation.<Boolean>changeWithContext((changeContext, sqlConnection) ->
                            updateDeploymentMatchmakerResourceStatusOperation.execute(changeContext,
                                    sqlConnection,
                                    shard,
                                    deploymentId,
                                    id,
                                    status
                            ));
                })
                .map(ChangeContext::getResult)
                .map(UpdateDeploymentMatchmakerResourceStatusResponse::new);
    }
}
