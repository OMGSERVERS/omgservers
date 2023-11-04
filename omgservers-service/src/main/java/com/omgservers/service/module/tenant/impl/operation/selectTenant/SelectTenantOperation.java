package com.omgservers.service.module.tenant.impl.operation.selectTenant;

import com.omgservers.model.tenant.TenantModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface SelectTenantOperation {
    Uni<TenantModel> selectTenant(SqlConnection sqlConnection,
                                  int shard,
                                  Long id,
                                  Boolean deleted);
}
