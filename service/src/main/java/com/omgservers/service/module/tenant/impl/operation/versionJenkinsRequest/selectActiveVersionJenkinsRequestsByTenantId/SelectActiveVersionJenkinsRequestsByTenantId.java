package com.omgservers.service.module.tenant.impl.operation.versionJenkinsRequest.selectActiveVersionJenkinsRequestsByTenantId;

import com.omgservers.model.versionJenkinsRequest.VersionJenkinsRequestModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.util.List;

public interface SelectActiveVersionJenkinsRequestsByTenantId {
    Uni<List<VersionJenkinsRequestModel>> selectActiveVersionJenkinsRequestsByTenantId(SqlConnection sqlConnection,
                                                                                       int shard,
                                                                                       Long tenantId);
}
