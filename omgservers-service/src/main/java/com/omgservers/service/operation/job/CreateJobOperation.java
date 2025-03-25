package com.omgservers.service.operation.job;

import com.omgservers.schema.model.job.JobQualifierEnum;
import io.smallrye.mutiny.Uni;

public interface CreateJobOperation {
    Uni<Boolean> execute(JobQualifierEnum qualifier,
                         Long shardKey,
                         Long entityId,
                         String idempotencyKey);

    Uni<Boolean> execute(JobQualifierEnum qualifier,
                         Long entityId,
                         String idempotencyKey);
}
