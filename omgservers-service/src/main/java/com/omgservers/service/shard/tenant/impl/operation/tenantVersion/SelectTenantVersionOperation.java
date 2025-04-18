package com.omgservers.service.shard.tenant.impl.operation.tenantVersion;

import com.omgservers.schema.model.tenantVersion.TenantVersionModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface SelectTenantVersionOperation {
    Uni<TenantVersionModel> execute(SqlConnection sqlConnection,
                                    int slot,
                                    Long tenantId,
                                    Long id);
}
