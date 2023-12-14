package com.omgservers.service.module.tenant.impl.operation.selectActiveVersionRuntimesByTenantId;

import com.omgservers.model.versionRuntime.VersionRuntimeModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.util.List;

public interface SelectActiveVersionRuntimesByTenantId {
    Uni<List<VersionRuntimeModel>> selectActiveVersionRuntimesByTenantId(SqlConnection sqlConnection,
                                                                         int shard,
                                                                         Long tenantId);
}
