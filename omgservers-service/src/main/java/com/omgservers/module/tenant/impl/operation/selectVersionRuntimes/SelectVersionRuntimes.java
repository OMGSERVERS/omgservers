package com.omgservers.module.tenant.impl.operation.selectVersionRuntimes;

import com.omgservers.model.versionRuntime.VersionRuntimeModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.util.List;

public interface SelectVersionRuntimes {
    Uni<List<VersionRuntimeModel>> selectVersionRuntimes(SqlConnection sqlConnection,
                                                         int shard,
                                                         Long tenantId,
                                                         Long versionId,
                                                         Boolean deleted);
}
