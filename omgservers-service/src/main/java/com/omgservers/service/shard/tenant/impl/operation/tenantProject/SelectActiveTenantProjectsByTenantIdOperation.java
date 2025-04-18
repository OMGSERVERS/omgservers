package com.omgservers.service.shard.tenant.impl.operation.tenantProject;

import com.omgservers.schema.model.project.TenantProjectModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.util.List;

public interface SelectActiveTenantProjectsByTenantIdOperation {
    Uni<List<TenantProjectModel>> execute(SqlConnection sqlConnection,
                                          int slot,
                                          Long tenantId);
}
