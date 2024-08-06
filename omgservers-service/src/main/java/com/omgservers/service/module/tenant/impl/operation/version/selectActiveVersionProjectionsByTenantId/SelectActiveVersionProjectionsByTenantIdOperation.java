package com.omgservers.service.module.tenant.impl.operation.version.selectActiveVersionProjectionsByTenantId;

import com.omgservers.schema.model.version.VersionProjectionModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.util.List;

public interface SelectActiveVersionProjectionsByTenantIdOperation {
    Uni<List<VersionProjectionModel>> selectActiveVersionProjectionsByTenantId(SqlConnection sqlConnection,
                                                                               int shard,
                                                                               Long tenantId);
}
