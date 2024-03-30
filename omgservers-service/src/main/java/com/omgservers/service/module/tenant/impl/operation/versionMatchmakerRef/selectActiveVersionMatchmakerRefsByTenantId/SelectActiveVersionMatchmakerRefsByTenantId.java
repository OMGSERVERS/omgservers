package com.omgservers.service.module.tenant.impl.operation.versionMatchmakerRef.selectActiveVersionMatchmakerRefsByTenantId;

import com.omgservers.model.versionMatchmakerRef.VersionMatchmakerRefModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.util.List;

public interface SelectActiveVersionMatchmakerRefsByTenantId {
    Uni<List<VersionMatchmakerRefModel>> selectActiveVersionMatchmakerRefsByTenantId(SqlConnection sqlConnection,
                                                                                     int shard,
                                                                                     Long tenantId);
}
