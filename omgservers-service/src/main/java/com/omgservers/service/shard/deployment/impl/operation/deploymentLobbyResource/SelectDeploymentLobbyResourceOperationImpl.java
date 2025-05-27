package com.omgservers.service.shard.deployment.impl.operation.deploymentLobbyResource;

import com.omgservers.schema.model.deploymentLobbyResource.DeploymentLobbyResourceModel;
import com.omgservers.service.operation.server.SelectObjectOperation;
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
class SelectDeploymentLobbyResourceOperationImpl implements SelectDeploymentLobbyResourceOperation {

    final SelectObjectOperation selectObjectOperation;

    final DeploymentLobbyResourceModelMapper deploymentLobbyResourceModelMapper;

    @Override
    public Uni<DeploymentLobbyResourceModel> execute(final SqlConnection sqlConnection,
                                                     final int slot,
                                                     final Long deploymentId,
                                                     final Long id) {
        return selectObjectOperation.selectObject(
                sqlConnection,
                slot,
                """
                        select
                            id, idempotency_key, deployment_id, created, modified, lobby_id, status, config, deleted
                        from $slot.tab_deployment_lobby_resource
                        where deployment_id = $1 and id = $2
                        limit 1
                        """,
                List.of(deploymentId, id),
                "Deployment lobby resource",
                deploymentLobbyResourceModelMapper::execute);
    }
}
