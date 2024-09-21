package com.omgservers.service.module.tenant.impl.operation.tenantProject;

import com.omgservers.schema.model.project.TenantProjectModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface SelectTenantProjectOperation {
    Uni<TenantProjectModel> execute(SqlConnection sqlConnection,
                                    int shard,
                                    Long tenantId,
                                    Long id);
}
