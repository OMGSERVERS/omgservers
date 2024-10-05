package com.omgservers.service.module.tenant.impl.operation.tenantFilesArchive;

import com.omgservers.schema.model.tenantFilesArchive.TenantFilesArchiveProjectionModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.util.List;

public interface SelectActiveTenantFilesArchiveProjectionsByTenantIdOperation {
    Uni<List<TenantFilesArchiveProjectionModel>> execute(SqlConnection sqlConnection,
                                                         int shard,
                                                         Long tenantId);
}
