package com.omgservers.service.module.tenant.impl.operation.selectActiveVersionRuntimes;

import com.omgservers.model.versionRuntime.VersionRuntimeModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.util.List;

public interface SelectActiveVersionRuntimes {
    Uni<List<VersionRuntimeModel>> selectActiveVersionRuntimes(SqlConnection sqlConnection,
                                                               int shard,
                                                               Long tenantId,
                                                               Long versionId);
}
