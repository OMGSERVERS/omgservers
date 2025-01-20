package com.omgservers.service.shard.tenant.impl.operation.tenantFilesArchive;

import com.omgservers.schema.model.tenantFilesArchive.TenantFilesArchiveModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface SelectTenantFilesArchiveOperation {
    Uni<TenantFilesArchiveModel> execute(SqlConnection sqlConnection,
                                         int shard,
                                         Long tenantId,
                                         Long id);
}
