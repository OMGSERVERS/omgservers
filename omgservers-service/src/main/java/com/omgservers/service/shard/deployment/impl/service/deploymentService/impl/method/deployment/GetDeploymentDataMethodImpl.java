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

        final int slot = shardModel.slot();
        final var deploymentId = request.getDeploymentId();
        final var deploymentData = new DeploymentDataDto();

        return pgPool.withTransaction(sqlConnection -> fillData(sqlConnection,
                        slot,
                        deploymentId,
                        deploymentData))
                .map(GetDeploymentDataResponse::new);
    }

    Uni<DeploymentDataDto> fillData(final SqlConnection sqlConnection,
                                    final int slot,
                                    final Long deploymentId,
                                    final DeploymentDataDto deploymentData) {
        return fillDeployment(sqlConnection, slot, deploymentId, deploymentData)
                .flatMap(voidItem -> fillDeploymentLobbyResources(sqlConnection, slot, deploymentId, deploymentData))
                .flatMap(voidItem -> fillDeploymentMatchmakerResources(sqlConnection, slot, deploymentId,
                        deploymentData))
                .replaceWith(deploymentData);
    }

    Uni<Void> fillDeployment(final SqlConnection sqlConnection,
                             final int slot,
                             final Long deploymentId,
                             final DeploymentDataDto deploymentData) {
        return selectDeploymentOperation.execute(sqlConnection,
                        slot,
                        deploymentId)
                .invoke(deploymentData::setDeployment)
                .replaceWithVoid();
    }

    Uni<Void> fillDeploymentLobbyResources(final SqlConnection sqlConnection,
                                           final int slot,
                                           final Long deploymentId,
                                           final DeploymentDataDto deploymentData) {
        return selectActiveDeploymentLobbyResourcesByDeploymentIdOperation.execute(sqlConnection,
                        slot,
                        deploymentId)
                .invoke(deploymentData::setLobbyResources)
                .replaceWithVoid();
    }

    Uni<Void> fillDeploymentMatchmakerResources(final SqlConnection sqlConnection,
                                                final int slot,
                                                final Long deploymentId,
                                                final DeploymentDataDto deploymentData) {
        return selectActiveDeploymentMatchmakerResourcesByDeploymentIdOperation.execute(sqlConnection,
                        slot,
                        deploymentId)
                .invoke(deploymentData::setMatchmakerResources)
                .replaceWithVoid();
    }
}
