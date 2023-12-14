package com.omgservers.service.module.tenant.impl.operation.selectActiveVersionMatchmakersByTenantId;

import com.omgservers.model.versionMatchmaker.VersionMatchmakerModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.util.List;

public interface SelectActiveVersionMatchmakersByTenantId {
    Uni<List<VersionMatchmakerModel>> selectActiveVersionMatchmakersTenantId(SqlConnection sqlConnection,
                                                                             int shard,
                                                                             Long tenantId);
}
