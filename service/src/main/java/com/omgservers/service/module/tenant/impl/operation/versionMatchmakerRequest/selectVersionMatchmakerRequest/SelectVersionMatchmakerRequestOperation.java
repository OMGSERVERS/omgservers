package com.omgservers.service.module.tenant.impl.operation.versionMatchmakerRequest.selectVersionMatchmakerRequest;

import com.omgservers.schema.model.versionMatchmakerRequest.VersionMatchmakerRequestModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface SelectVersionMatchmakerRequestOperation {
    Uni<VersionMatchmakerRequestModel> selectVersionMatchmakerRequest(SqlConnection sqlConnection,
                                                                      int shard,
                                                                      Long tenantId,
                                                                      Long id);
}
