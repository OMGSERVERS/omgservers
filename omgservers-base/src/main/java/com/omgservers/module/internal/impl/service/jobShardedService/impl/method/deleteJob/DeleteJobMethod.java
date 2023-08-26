package com.omgservers.module.internal.impl.service.jobShardedService.impl.method.deleteJob;

import com.omgservers.dto.internalModule.DeleteJobShardRequest;
import com.omgservers.dto.internalModule.DeleteJobShardedResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteJobMethod {
    Uni<DeleteJobShardedResponse> deleteJob(DeleteJobShardRequest request);
}
