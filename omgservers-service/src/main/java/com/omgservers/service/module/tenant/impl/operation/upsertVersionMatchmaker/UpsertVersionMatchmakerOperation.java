package com.omgservers.service.module.tenant.impl.operation.upsertVersionMatchmaker;

import com.omgservers.model.versionMatchmaker.VersionMatchmakerModel;
import com.omgservers.service.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface UpsertVersionMatchmakerOperation {
    Uni<Boolean> upsertVersionMatchmaker(ChangeContext<?> changeContext,
                                         SqlConnection sqlConnection,
                                         int shard,
                                         VersionMatchmakerModel versionMatchmaker);
}
