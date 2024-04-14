package com.omgservers.service.module.tenant.impl.operation.versionJenkinsRequest.selectActiveVersionJenkinsRequestsByVersionId;

import com.omgservers.model.versionJenkinsRequest.VersionJenkinsRequestModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.util.List;

public interface SelectActiveVersionJenkinsRequestsByVersionId {
    Uni<List<VersionJenkinsRequestModel>> selectActiveVersionJenkinsRequestsByVersionId(SqlConnection sqlConnection,
                                                                                        int shard,
                                                                                        Long tenantId,
                                                                                        Long versionId);
}
