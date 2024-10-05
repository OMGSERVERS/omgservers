package com.omgservers.service.module.tenant.impl.operation.tenantFilesArchive;

import com.omgservers.schema.model.tenantFilesArchive.TenantFilesArchiveModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface SelectTenantFilesArchiveByTenantVersionIdOperation {
    Uni<TenantFilesArchiveModel> execute(SqlConnection sqlConnection,
                                         int shard,
                                         Long tenantId,
                                         Long tenantVersionId);
}
