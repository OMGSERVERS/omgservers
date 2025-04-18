package com.omgservers.service.shard.tenant.impl.operation.tenantVersion;

import com.omgservers.schema.model.tenantVersion.TenantVersionProjectionModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.util.List;

public interface SelectActiveTenantVersionProjectionsByTenantIdOperation {
    Uni<List<TenantVersionProjectionModel>> execute(SqlConnection sqlConnection,
                                                    int slot,
                                                    Long tenantId);
}
