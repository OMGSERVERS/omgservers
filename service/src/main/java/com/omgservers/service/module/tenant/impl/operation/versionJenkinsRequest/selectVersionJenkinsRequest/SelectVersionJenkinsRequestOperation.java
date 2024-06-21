package com.omgservers.service.module.tenant.impl.operation.versionJenkinsRequest.selectVersionJenkinsRequest;

import com.omgservers.model.versionJenkinsRequest.VersionJenkinsRequestModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface SelectVersionJenkinsRequestOperation {
    Uni<VersionJenkinsRequestModel> selectVersionJenkinsRequest(SqlConnection sqlConnection,
                                                                int shard,
                                                                Long tenantId,
                                                                Long id);
}
