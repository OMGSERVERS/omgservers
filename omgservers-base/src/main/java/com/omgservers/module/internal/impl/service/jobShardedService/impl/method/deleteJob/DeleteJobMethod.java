package com.omgservers.module.internal.impl.service.jobShardedService.impl.method.deleteJob;

import com.omgservers.dto.internal.DeleteJobShardedRequest;
import com.omgservers.dto.internal.DeleteJobShardedResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteJobMethod {
    Uni<DeleteJobShardedResponse> deleteJob(DeleteJobShardedRequest request);
}
