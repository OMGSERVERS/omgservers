package com.omgservers.base.impl.service.jobSchedulerService.impl;

import com.omgservers.base.impl.service.jobSchedulerService.JobSchedulerService;
import com.omgservers.base.impl.service.jobSchedulerService.impl.method.scheduleJobInternalMethod.ScheduleJobInternalMethod;
import com.omgservers.base.impl.service.jobSchedulerService.impl.method.unscheduleJobInternalMethod.UnscheduleJobInternalMethod;
import com.omgservers.base.impl.operation.getInternalsServiceApiClientOperation.GetInternalsServiceApiClientOperation;
import com.omgservers.base.impl.operation.getInternalsServiceApiClientOperation.InternalsServiceApiClient;
import com.omgservers.base.impl.operation.handleInternalRequestOperation.HandleInternalRequestOperation;
import com.omgservers.dto.internalModule.ScheduleJobInternalRequest;
import com.omgservers.dto.internalModule.UnscheduleJobInternalRequest;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class JobSchedulerServiceImpl implements JobSchedulerService {

    final UnscheduleJobInternalMethod unscheduleJobInternalMethod;
    final ScheduleJobInternalMethod scheduleJobInternalMethod;

    final GetInternalsServiceApiClientOperation getInternalsServiceApiClientOperation;
    final HandleInternalRequestOperation handleInternalRequestOperation;

    @Override
    public Uni<Void> scheduleJob(ScheduleJobInternalRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                ScheduleJobInternalRequest::validate,
                getInternalsServiceApiClientOperation::getClient,
                InternalsServiceApiClient::scheduleJob,
                scheduleJobInternalMethod::scheduleJob);
    }

    @Override
    public Uni<Void> unscheduleJob(UnscheduleJobInternalRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                UnscheduleJobInternalRequest::validate,
                getInternalsServiceApiClientOperation::getClient,
                InternalsServiceApiClient::unscheduleJob,
                unscheduleJobInternalMethod::unscheduleJob);
    }
}
