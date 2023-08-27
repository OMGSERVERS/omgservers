package com.omgservers.module.version.impl.service.versionShardedService.impl.method.deleteVersion;

import com.omgservers.dto.version.DeleteVersionShardedRequest;
import com.omgservers.dto.version.DeleteVersionShardedResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteVersionMethod {
    Uni<DeleteVersionShardedResponse> deleteVersion(DeleteVersionShardedRequest request);
}
