package com.omgservers.module.internal.impl.service.jobShardedService;

import com.omgservers.dto.internalModule.DeleteJobShardRequest;
import com.omgservers.dto.internalModule.DeleteJobShardedResponse;
import com.omgservers.dto.internalModule.ScheduleJobShardRequest;
import com.omgservers.dto.internalModule.SyncJobShardRequest;
import com.omgservers.dto.internalModule.SyncJobRoutedResponse;
import com.omgservers.dto.internalModule.UnscheduleJobShardRequest;
import io.smallrye.mutiny.Uni;

public interface JobShardedService {
    Uni<SyncJobRoutedResponse> syncJob(SyncJobShardRequest request);

    Uni<DeleteJobShardedResponse> deleteJob(DeleteJobShardRequest request);

    Uni<Void> scheduleJob(ScheduleJobShardRequest request);

    Uni<Void> unscheduleJob(UnscheduleJobShardRequest request);
}
