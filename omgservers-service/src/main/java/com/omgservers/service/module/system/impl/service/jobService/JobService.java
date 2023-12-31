package com.omgservers.service.module.system.impl.service.jobService;

import com.omgservers.model.dto.system.DeleteJobRequest;
import com.omgservers.model.dto.system.DeleteJobResponse;
import com.omgservers.model.dto.system.FindJobRequest;
import com.omgservers.model.dto.system.FindJobResponse;
import com.omgservers.model.dto.system.GetJobRequest;
import com.omgservers.model.dto.system.GetJobResponse;
import com.omgservers.model.dto.system.ScheduleJobRequest;
import com.omgservers.model.dto.system.SyncJobRequest;
import com.omgservers.model.dto.system.SyncJobResponse;
import com.omgservers.model.dto.system.UnscheduleJobRequest;
import io.smallrye.mutiny.Uni;
import jakarta.validation.Valid;

public interface JobService {

    Uni<GetJobResponse> getJob(@Valid GetJobRequest request);

    Uni<FindJobResponse> findJob(@Valid FindJobRequest request);

    Uni<SyncJobResponse> syncJob(@Valid SyncJobRequest request);

    Uni<DeleteJobResponse> deleteJob(@Valid DeleteJobRequest request);

    Uni<Void> scheduleJob(@Valid ScheduleJobRequest request);

    Uni<Void> unscheduleJob(@Valid UnscheduleJobRequest request);
}
