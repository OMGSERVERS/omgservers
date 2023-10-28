package com.omgservers.module.tenant.impl.operation.selectVersionMatchmaker;

import com.omgservers.model.versionMatchmaker.VersionMatchmakerModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface SelectVersionMatchmakerOperation {
    Uni<VersionMatchmakerModel> selectVersionMatchmaker(SqlConnection sqlConnection,
                                                        int shard,
                                                        Long tenantId,
                                                        Long id);
}
