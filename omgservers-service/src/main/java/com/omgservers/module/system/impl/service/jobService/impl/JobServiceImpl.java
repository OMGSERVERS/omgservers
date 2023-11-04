package com.omgservers.module.system.impl.service.jobService.impl;

import com.omgservers.model.dto.system.DeleteJobRequest;
import com.omgservers.model.dto.system.DeleteJobResponse;
import com.omgservers.model.dto.system.ScheduleJobRequest;
import com.omgservers.model.dto.system.SyncJobRequest;
import com.omgservers.model.dto.system.SyncJobResponse;
import com.omgservers.model.dto.system.UnscheduleJobRequest;
import com.omgservers.module.system.impl.service.jobService.impl.method.deleteJob.DeleteJobMethod;
import com.omgservers.module.system.impl.service.jobService.impl.method.scheduleJob.ScheduleJobMethod;
import com.omgservers.module.system.impl.service.jobService.impl.method.syncJob.SyncJobMethod;
import com.omgservers.module.system.impl.service.jobService.impl.method.unscheduleJob.UnscheduleJobMethod;
import com.omgservers.module.system.impl.service.jobService.JobService;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class JobServiceImpl implements JobService {

    final UnscheduleJobMethod unscheduleJobMethod;
    final ScheduleJobMethod scheduleJobMethod;
    final DeleteJobMethod deleteJobMethod;
    final SyncJobMethod syncJobMethod;

    @Override
    public Uni<SyncJobResponse> syncJob(@Valid final SyncJobRequest request) {
        return syncJobMethod.syncJob(request);
    }

    @Override
    public Uni<DeleteJobResponse> deleteJob(@Valid final DeleteJobRequest request) {
        return deleteJobMethod.deleteJob(request);
    }

    @Override
    public Uni<Void> scheduleJob(@Valid final ScheduleJobRequest request) {
        return scheduleJobMethod.scheduleJob(request);
    }

    @Override
    public Uni<Void> unscheduleJob(@Valid final UnscheduleJobRequest request) {
        return unscheduleJobMethod.unscheduleJob(request);
    }
}
