package com.omgservers.service.shard.deployment.impl.operation.deploymentMatchmakerResource;

import com.omgservers.schema.model.deploymentMatchmakerResource.DeploymentMatchmakerResourceStatusEnum;
import com.omgservers.service.operation.server.ChangeContext;
import com.omgservers.service.operation.server.ChangeObjectOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.ZoneOffset;
import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class UpdateDeploymentMatchmakerResourceStatusOperationImpl implements UpdateDeploymentMatchmakerResourceStatusOperation {

    final ChangeObjectOperation changeObjectOperation;

    @Override
    public Uni<Boolean> execute(final ChangeContext<?> changeContext,
                                final SqlConnection sqlConnection,
                                final int shard,
                                final Long deploymentId,
                                final Long id,
                                final DeploymentMatchmakerResourceStatusEnum status) {
        return changeObjectOperation.execute(changeContext, sqlConnection, shard,
                """
                        update $shard.tab_deployment_matchmaker_resource
                        set modified = $3, status = $4
                        where deployment_id = $1 and id = $2
                        """,
                List.of(
                        deploymentId,
                        id,
                        Instant.now().atOffset(ZoneOffset.UTC),
                        status
                ),
                () -> null,
                () -> null
        );
    }
}
