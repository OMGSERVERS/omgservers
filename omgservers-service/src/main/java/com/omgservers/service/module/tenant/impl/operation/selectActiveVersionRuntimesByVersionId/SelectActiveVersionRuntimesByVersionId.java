package com.omgservers.service.module.tenant.impl.operation.selectActiveVersionRuntimesByVersionId;

import com.omgservers.model.versionRuntime.VersionRuntimeModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.util.List;

public interface SelectActiveVersionRuntimesByVersionId {
    Uni<List<VersionRuntimeModel>> selectActiveVersionRuntimesByVersionId(SqlConnection sqlConnection,
                                                                          int shard,
                                                                          Long tenantId,
                                                                          Long versionId);
}
