package com.omgservers.service.shard.deployment.impl.service.deploymentService.impl.method.deployment;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.deployment.deployment.GetDeploymentDataRequest;
import com.omgservers.schema.shard.deployment.deployment.GetDeploymentDataResponse;
import com.omgservers.schema.shard.deployment.deployment.dto.DeploymentDataDto;
import com.omgservers.service.shard.deployment.impl.operation.deployment.SelectDeploymentOperation;
import com.omgservers.service.shard.deployment.impl.operation.deploymentLobbyResource.SelectActiveDeploymentLobbyResourcesByDeploymentIdOperation;
import com.omgservers.service.shard.deployment.impl.operation.deploymentMatchmakerResource.SelectActiveDeploymentMatchmakerResourcesByDeploymentIdOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class GetDeploymentDataMethodImpl implements GetDeploymentDataMethod {

    final SelectActiveDeploymentMatchmakerResourcesByDeploymentIdOperation
            selectActiveDeploymentMatchmakerResourcesByDeploymentIdOperation;
    final SelectActiveDeploymentLobbyResourcesByDeploymentIdOperation
            selectActiveDeploymentLobbyResourcesByDeploymentIdOperation;
    final SelectDeploymentOperation
            selectDeploymentOperation;

    final PgPool pgPool;

    @Override
    public Uni<GetDeploymentDataResponse> execute(final ShardModel shardModel,
                                                  final GetDeploymentDataRequest request) {
        log.trace("{}", request);

        final int shard = shardModel.shard();
        final var deploymentId = request.getDeploymentId();
        final var deploymentData = new DeploymentDataDto();

        return pgPool.withTransaction(sqlConnection -> fillData(sqlConnection,
                        shard,
                        deploymentId,
                        deploymentData))
                .map(GetDeploymentDataResponse::new);
    }

    Uni<DeploymentDataDto> fillData(final SqlConnection sqlConnection,
                                    final int shard,
                                    final Long deploymentId,
                                    final DeploymentDataDto deploymentData) {
        return fillDeployment(sqlConnection, shard, deploymentId, deploymentData)
                .flatMap(voidItem -> fillDeploymentLobbyResources(sqlConnection, shard, deploymentId, deploymentData))
                .flatMap(voidItem -> fillDeploymentMatchmakerResources(sqlConnection, shard, deploymentId, deploymentData))
                .replaceWith(deploymentData);
    }

    Uni<Void> fillDeployment(final SqlConnection sqlConnection,
                             final int shard,
                             final Long deploymentId,
                             final DeploymentDataDto deploymentData) {
        return selectDeploymentOperation.execute(sqlConnection,
                        shard,
                        deploymentId)
                .invoke(deploymentData::setDeployment)
                .replaceWithVoid();
    }

    Uni<Void> fillDeploymentLobbyResources(final SqlConnection sqlConnection,
                                           final int shard,
                                           final Long deploymentId,
                                           final DeploymentDataDto deploymentData) {
        return selectActiveDeploymentLobbyResourcesByDeploymentIdOperation.execute(sqlConnection,
                        shard,
                        deploymentId)
                .invoke(deploymentData::setLobbyResources)
                .replaceWithVoid();
    }

    Uni<Void> fillDeploymentMatchmakerResources(final SqlConnection sqlConnection,
                                                final int shard,
                                                final Long deploymentId,
                                                final DeploymentDataDto deploymentData) {
        return selectActiveDeploymentMatchmakerResourcesByDeploymentIdOperation.execute(sqlConnection,
                        shard,
                        deploymentId)
                .invoke(deploymentData::setMatchmakerResources)
                .replaceWithVoid();
    }
}
