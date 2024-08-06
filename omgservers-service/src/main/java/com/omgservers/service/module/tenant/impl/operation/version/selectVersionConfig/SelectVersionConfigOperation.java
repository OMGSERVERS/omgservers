package com.omgservers.service.module.tenant.impl.operation.version.selectVersionConfig;

import com.omgservers.schema.model.version.VersionConfigDto;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface SelectVersionConfigOperation {
    Uni<VersionConfigDto> selectVersionConfig(SqlConnection sqlConnection,
                                              int shard,
                                              Long tenantId,
                                              Long versionId);
}
