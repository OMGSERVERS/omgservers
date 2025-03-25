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
class SelectDeploymentLobbyResourceByLobbyIdOperationImpl implements SelectDeploymentLobbyResourceByLobbyIdOperation {

    final SelectObjectOperation selectObjectOperation;

    final DeploymentLobbyResourceModelMapper deploymentLobbyResourceModelMapper;

    @Override
    public Uni<DeploymentLobbyResourceModel> execute(final SqlConnection sqlConnection,
                                                     final int shard,
                                                     final Long deploymentId,
                                                     final Long lobbyId) {
        return selectObjectOperation.selectObject(
                sqlConnection,
                shard,
                """
                        select
                            id, idempotency_key, deployment_id, created, modified, lobby_id, status, deleted
                        from $schema.tab_deployment_lobby_resource
                        where deployment_id = $1 and lobby_id = $2
                        order by id desc
                        limit 1
                        """,
                List.of(deploymentId, lobbyId),
                "Deployment lobby resource",
                deploymentLobbyResourceModelMapper::execute);
    }
}
