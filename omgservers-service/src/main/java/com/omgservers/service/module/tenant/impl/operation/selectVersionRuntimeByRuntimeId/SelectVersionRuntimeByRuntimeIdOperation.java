package com.omgservers.service.module.tenant.impl.operation.selectVersionRuntimeByRuntimeId;

import com.omgservers.model.versionRuntime.VersionRuntimeModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface SelectVersionRuntimeByRuntimeIdOperation {
    Uni<VersionRuntimeModel> selectVersionRuntimeByRuntimeId(SqlConnection sqlConnection,
                                                             int shard,
                                                             Long tenantId,
                                                             Long versionId,
                                                             Long runtimeId);
}
