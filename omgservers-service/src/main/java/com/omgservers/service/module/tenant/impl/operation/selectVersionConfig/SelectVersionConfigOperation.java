package com.omgservers.service.module.tenant.impl.operation.selectVersionConfig;

import com.omgservers.model.version.VersionConfigModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.time.Duration;

public interface SelectVersionConfigOperation {
    Uni<VersionConfigModel> selectVersionConfig(SqlConnection sqlConnection,
                                                int shard,
                                                Long tenantId,
                                                Long versionId);
}
