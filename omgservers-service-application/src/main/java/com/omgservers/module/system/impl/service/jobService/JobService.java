package com.omgservers.module.system.impl.service.jobService;

import com.omgservers.model.dto.internal.DeleteJobRequest;
import com.omgservers.model.dto.internal.DeleteJobResponse;
import com.omgservers.model.dto.internal.ScheduleJobRequest;
import com.omgservers.model.dto.internal.SyncJobRequest;
import com.omgservers.model.dto.internal.SyncJobResponse;
import com.omgservers.model.dto.internal.UnscheduleJobRequest;
import io.smallrye.mutiny.Uni;
import jakarta.validation.Valid;

public interface JobService {
    Uni<SyncJobResponse> syncJob(@Valid SyncJobRequest request);

    Uni<DeleteJobResponse> deleteJob(@Valid DeleteJobRequest request);

    Uni<Void> scheduleJob(@Valid ScheduleJobRequest request);

    Uni<Void> unscheduleJob(@Valid UnscheduleJobRequest request);
}
