package com.omgservers.application.module.internalModule.impl.service.internalWebService;

import com.omgservers.application.module.internalModule.impl.service.eventInternalService.request.FireEventInternalRequest;
import com.omgservers.application.module.internalModule.impl.service.indexHelpService.request.SyncIndexHelpRequest;
import com.omgservers.application.module.internalModule.impl.service.jobInternalService.request.DeleteJobInternalRequest;
import com.omgservers.application.module.internalModule.impl.service.jobInternalService.request.SyncJobInternalRequest;
import com.omgservers.application.module.internalModule.impl.service.jobInternalService.response.DeleteJobInternalResponse;
import com.omgservers.application.module.internalModule.impl.service.jobInternalService.response.SyncJobInternalResponse;
import com.omgservers.application.module.internalModule.impl.service.jobSchedulerService.request.ScheduleJobInternalRequest;
import com.omgservers.application.module.internalModule.impl.service.jobSchedulerService.request.UnscheduleJobInternalRequest;
import com.omgservers.application.module.internalModule.impl.service.logHelpService.request.ViewLogsHelpRequest;
import com.omgservers.application.module.internalModule.impl.service.logHelpService.response.ViewLogsHelpResponse;
import com.omgservers.application.module.internalModule.impl.service.serviceAccountHelpService.request.SyncServiceAccountHelpRequest;
import io.smallrye.mutiny.Uni;

public interface InternalWebService {
    Uni<Void> syncIndex(SyncIndexHelpRequest request);

    Uni<Void> syncServiceAccount(SyncServiceAccountHelpRequest request);

    Uni<Void> fireEvent(FireEventInternalRequest request);

    Uni<SyncJobInternalResponse> syncJob(SyncJobInternalRequest request);

    Uni<DeleteJobInternalResponse> deleteJob(DeleteJobInternalRequest request);

    Uni<Void> scheduleJob(ScheduleJobInternalRequest request);

    Uni<Void> unscheduleJob(UnscheduleJobInternalRequest request);

    Uni<ViewLogsHelpResponse> viewLogs(ViewLogsHelpRequest request);
}
