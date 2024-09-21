package com.omgservers.service.module.tenant.impl.operation.tenant;

import com.omgservers.schema.model.tenant.TenantModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface SelectTenantOperation {
    Uni<TenantModel> execute(SqlConnection sqlConnection,
                             int shard,
                             Long id);
}
