package com.omgservers.service.shard.deployment.impl.operation.deploymentMatchmakerResource;

import com.omgservers.schema.model.deploymentMatchmakerResource.DeploymentMatchmakerResourceModel;
import com.omgservers.schema.model.deploymentMatchmakerResource.DeploymentMatchmakerResourceStatusEnum;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.util.List;

public interface SelectActiveDeploymentMatchmakerResourcesByDeploymentIdAndStatusOperation {
    Uni<List<DeploymentMatchmakerResourceModel>> execute(
            SqlConnection sqlConnection,
            int shard,
            Long deploymentId,
            DeploymentMatchmakerResourceStatusEnum status);
}
