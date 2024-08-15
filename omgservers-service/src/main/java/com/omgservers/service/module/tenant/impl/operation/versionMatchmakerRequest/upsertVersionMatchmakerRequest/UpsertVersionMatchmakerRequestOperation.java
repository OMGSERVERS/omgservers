package com.omgservers.service.module.tenant.impl.operation.versionMatchmakerRequest.upsertVersionMatchmakerRequest;

import com.omgservers.schema.model.versionMatchmakerRequest.VersionMatchmakerRequestModel;
import com.omgservers.service.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface UpsertVersionMatchmakerRequestOperation {
    Uni<Boolean> upsertVersionMatchmakerRequest(ChangeContext<?> changeContext,
                                                SqlConnection sqlConnection,
                                                int shard,
                                                VersionMatchmakerRequestModel versionMatchmakerRequest);
}
