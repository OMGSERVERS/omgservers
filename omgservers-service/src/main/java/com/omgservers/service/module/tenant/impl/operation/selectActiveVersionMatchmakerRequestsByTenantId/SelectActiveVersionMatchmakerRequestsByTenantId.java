package com.omgservers.service.module.tenant.impl.operation.selectActiveVersionMatchmakerRequestsByTenantId;

import com.omgservers.model.versionMatchmakerRequest.VersionMatchmakerRequestModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.util.List;

public interface SelectActiveVersionMatchmakerRequestsByTenantId {
    Uni<List<VersionMatchmakerRequestModel>> selectActiveVersionMatchmakerRequestsByTenantId(
            SqlConnection sqlConnection,
            int shard,
            Long tenantId);
}
