package com.omgservers.service.shard.deployment.impl.operation.deploymentLobbyResource;

import com.omgservers.schema.model.deploymentLobbyResource.DeploymentLobbyResourceModel;
import com.omgservers.service.operation.server.SelectListOperation;
import com.omgservers.service.shard.deployment.impl.mapper.DeploymentLobbyResourceModelMapper;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SelectActiveDeploymentLobbyResourcesByDeploymentIdOperationImpl
        implements SelectActiveDeploymentLobbyResourcesByDeploymentIdOperation {

    final SelectListOperation selectListOperation;

    final DeploymentLobbyResourceModelMapper deploymentLobbyResourceModelMapper;

    @Override
    public Uni<List<DeploymentLobbyResourceModel>> execute(final SqlConnection sqlConnection,
                                                           final int slot,
                                                           final Long deploymentId) {
        return selectListOperation.selectList(
                sqlConnection,
                slot,
                """
                        select
                            id, idempotency_key, deployment_id, created, modified, lobby_id, status, config, deleted
                        from $slot.tab_deployment_lobby_resource
                        where deployment_id = $1 and deleted = false
                        order by id asc
                        """,
                List.of(deploymentId),
                "Deployment lobby resource",
                deploymentLobbyResourceModelMapper::execute);
    }
}
