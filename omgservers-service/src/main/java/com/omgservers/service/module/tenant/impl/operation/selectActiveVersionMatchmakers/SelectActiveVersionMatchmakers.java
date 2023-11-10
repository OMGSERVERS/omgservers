package com.omgservers.service.module.tenant.impl.operation.selectActiveVersionMatchmakers;

import com.omgservers.model.versionMatchmaker.VersionMatchmakerModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.util.List;

public interface SelectActiveVersionMatchmakers {
    Uni<List<VersionMatchmakerModel>> selectActiveVersionMatchmakers(SqlConnection sqlConnection,
                                                                     int shard,
                                                                     Long tenantId,
                                                                     Long versionId);
}
