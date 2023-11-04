package com.omgservers.module.tenant.impl.operation.selectVersionMatchmakers;

import com.omgservers.model.versionMatchmaker.VersionMatchmakerModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.util.List;

public interface SelectVersionMatchmakers {
    Uni<List<VersionMatchmakerModel>> selectVersionMatchmakers(SqlConnection sqlConnection,
                                                               int shard,
                                                               Long tenantId,
                                                               Long versionId,
                                                               Boolean deleted);
}
