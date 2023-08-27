package com.omgservers.module.internal.impl.service.jobShardedService;

import com.omgservers.dto.internal.DeleteJobShardedRequest;
import com.omgservers.dto.internal.DeleteJobShardedResponse;
import com.omgservers.dto.internal.ScheduleJobShardedRequest;
import com.omgservers.dto.internal.SyncJobShardedRequest;
import com.omgservers.dto.internal.SyncJobRoutedResponse;
import com.omgservers.dto.internal.UnscheduleJobShardedRequest;
import io.smallrye.mutiny.Uni;

public interface JobShardedService {
    Uni<SyncJobRoutedResponse> syncJob(SyncJobShardedRequest request);

    Uni<DeleteJobShardedResponse> deleteJob(DeleteJobShardedRequest request);

    Uni<Void> scheduleJob(ScheduleJobShardedRequest request);

    Uni<Void> unscheduleJob(UnscheduleJobShardedRequest request);
}
