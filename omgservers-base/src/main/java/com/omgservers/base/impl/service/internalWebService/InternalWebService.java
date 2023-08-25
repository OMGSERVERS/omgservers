package com.omgservers.base.impl.service.internalWebService;

import com.omgservers.dto.internalModule.SyncIndexHelpRequest;
import com.omgservers.base.impl.service.logHelpService.request.ViewLogsHelpRequest;
import com.omgservers.base.impl.service.logHelpService.response.ViewLogsHelpResponse;
import com.omgservers.dto.internalModule.SyncServiceAccountHelpRequest;
import com.omgservers.dto.internalModule.DeleteJobInternalRequest;
import com.omgservers.dto.internalModule.DeleteJobInternalResponse;
import com.omgservers.dto.internalModule.FireEventInternalRequest;
import com.omgservers.dto.internalModule.FireEventInternalResponse;
import com.omgservers.dto.internalModule.ScheduleJobInternalRequest;
import com.omgservers.dto.internalModule.SyncJobInternalRequest;
import com.omgservers.dto.internalModule.SyncJobInternalResponse;
import com.omgservers.dto.internalModule.UnscheduleJobInternalRequest;
import io.smallrye.mutiny.Uni;

public interface InternalWebService {
    Uni<Void> syncIndex(SyncIndexHelpRequest request);

    Uni<Void> syncServiceAccount(SyncServiceAccountHelpRequest request);

    Uni<FireEventInternalResponse> fireEvent(FireEventInternalRequest request);

    Uni<SyncJobInternalResponse> syncJob(SyncJobInternalRequest request);

    Uni<DeleteJobInternalResponse> deleteJob(DeleteJobInternalRequest request);

    Uni<Void> scheduleJob(ScheduleJobInternalRequest request);

    Uni<Void> unscheduleJob(UnscheduleJobInternalRequest request);

    Uni<ViewLogsHelpResponse> viewLogs(ViewLogsHelpRequest request);
}
