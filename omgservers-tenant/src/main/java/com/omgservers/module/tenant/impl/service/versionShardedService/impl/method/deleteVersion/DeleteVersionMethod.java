package com.omgservers.module.tenant.impl.service.versionShardedService.impl.method.deleteVersion;

import com.omgservers.dto.tenant.DeleteVersionShardedRequest;
import com.omgservers.dto.tenant.DeleteVersionShardedResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteVersionMethod {
    Uni<DeleteVersionShardedResponse> deleteVersion(DeleteVersionShardedRequest request);
}
