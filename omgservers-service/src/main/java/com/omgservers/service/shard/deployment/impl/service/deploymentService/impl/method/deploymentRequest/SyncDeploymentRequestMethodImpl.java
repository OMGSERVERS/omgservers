package com.omgservers.service.shard.deployment.impl.service.deploymentService.impl.method.deploymentRequest;

import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.module.deployment.deploymentRequest.SyncDeploymentRequestRequest;
import com.omgservers.schema.module.deployment.deploymentRequest.SyncDeploymentRequestResponse;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.operation.server.ChangeContext;
import com.omgservers.service.operation.server.ChangeWithContextOperation;
import com.omgservers.service.shard.deployment.impl.operation.deployment.VerifyDeploymentExistsOperation;
import com.omgservers.service.shard.deployment.impl.operation.deploymentRequest.UpsertDeploymentRequestOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SyncDeploymentRequestMethodImpl implements SyncDeploymentRequestMethod {

    final UpsertDeploymentRequestOperation upsertDeploymentRequestOperation;
    final VerifyDeploymentExistsOperation verifyDeploymentExistsOperation;
    final ChangeWithContextOperation changeWithContextOperation;

    @Override
    public Uni<SyncDeploymentRequestResponse> execute(final ShardModel shardModel,
                                                      final SyncDeploymentRequestRequest request) {
        log.trace("{}", request);

        final var deploymentRequest = request.getDeploymentRequest();
        final var deploymentId = deploymentRequest.getDeploymentId();

        return changeWithContextOperation.<Boolean>changeWithContext(
                        (context, sqlConnection) -> verifyDeploymentExistsOperation
                                .execute(sqlConnection, shardModel.shard(), deploymentId)
                                .flatMap(exists -> {
                                    if (exists) {
                                        return upsertDeploymentRequestOperation.execute(
                                                context,
                                                sqlConnection,
                                                shardModel.shard(),
                                                deploymentRequest);
                                    } else {
                                        throw new ServerSideNotFoundException(
                                                ExceptionQualifierEnum.PARENT_NOT_FOUND,
                                                "deployment does not exist or was deleted, " +
                                                        "id=" + deploymentId);
                                    }
                                }))
                .map(ChangeContext::getResult)
                .map(SyncDeploymentRequestResponse::new);
    }
}
