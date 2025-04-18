package com.omgservers.service.shard.tenant.impl.operation.tenantProject;

import com.omgservers.schema.model.project.TenantProjectModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface SelectTenantProjectOperation {
    Uni<TenantProjectModel> execute(SqlConnection sqlConnection,
                                    int slot,
                                    Long tenantId,
                                    Long id);
}
