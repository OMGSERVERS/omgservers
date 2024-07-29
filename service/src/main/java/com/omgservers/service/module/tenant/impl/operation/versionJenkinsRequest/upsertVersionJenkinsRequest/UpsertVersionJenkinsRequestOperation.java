package com.omgservers.service.module.tenant.impl.operation.versionJenkinsRequest.upsertVersionJenkinsRequest;

import com.omgservers.schema.model.versionJenkinsRequest.VersionJenkinsRequestModel;
import com.omgservers.service.server.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface UpsertVersionJenkinsRequestOperation {
    Uni<Boolean> upsertVersionJenkinsRequest(ChangeContext<?> changeContext,
                                             SqlConnection sqlConnection,
                                             int shard,
                                             VersionJenkinsRequestModel versionJenkinsRequest);
}
