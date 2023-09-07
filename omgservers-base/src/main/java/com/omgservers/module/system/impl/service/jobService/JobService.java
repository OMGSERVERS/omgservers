package com.omgservers.module.system.impl.service.jobService;

import com.omgservers.dto.internal.DeleteJobRequest;
import com.omgservers.dto.internal.DeleteJobResponse;
import com.omgservers.dto.internal.ScheduleJobRequest;
import com.omgservers.dto.internal.SyncJobRequest;
import com.omgservers.dto.internal.SyncJobResponse;
import com.omgservers.dto.internal.UnscheduleJobRequest;
import io.smallrye.mutiny.Uni;

public interface JobService {
    Uni<SyncJobResponse> syncJob(SyncJobRequest request);

    Uni<DeleteJobResponse> deleteJob(DeleteJobRequest request);

    Uni<Void> scheduleJob(ScheduleJobRequest request);

    Uni<Void> unscheduleJob(UnscheduleJobRequest request);
}
