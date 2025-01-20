package com.omgservers.service.shard.tenant.impl.operation.tenantVersion;

import com.omgservers.schema.model.tenantVersion.TenantVersionConfigDto;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface SelectTenantVersionConfigOperation {
    Uni<TenantVersionConfigDto> execute(SqlConnection sqlConnection,
                                        int shard,
                                        Long tenantId,
                                        Long tenantVersionId);
}
