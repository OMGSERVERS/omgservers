package com.omgservers.service.shard.tenant.impl.operation.tenantDeploymentRef;

import com.omgservers.schema.model.tenantDeploymentRef.TenantDeploymentRefModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.util.List;

public interface SelectActiveTenantDeploymentRefsByStageIdOperation {
    Uni<List<TenantDeploymentRefModel>> execute(SqlConnection sqlConnection,
                                                int shard,
                                                Long tenantId,
                                                Long stageId);
}
