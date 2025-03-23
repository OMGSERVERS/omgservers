package com.omgservers.service.shard.tenant.impl.operation.tenantLobbyResource;

import com.omgservers.schema.model.tenantLobbyResource.TenantLobbyResourceModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.util.List;

public interface SelectActiveTenantLobbyResourcesByTenantDeploymentIdOperation {
    Uni<List<TenantLobbyResourceModel>> execute(SqlConnection sqlConnection,
                                                int shard,
                                                Long tenantId,
                                                Long tenantDeploymentId);
}
