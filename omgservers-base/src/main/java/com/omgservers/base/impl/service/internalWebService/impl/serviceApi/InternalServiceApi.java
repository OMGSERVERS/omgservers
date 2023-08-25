package com.omgservers.base.impl.service.internalWebService.impl.serviceApi;

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
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;

import java.time.Duration;

@Path("/omgservers/service-api/internal-api/v1/request")
public interface InternalServiceApi {

    @PUT
    @Path("/sync-index")
    Uni<Void> syncIndex(SyncIndexHelpRequest request);

    default void syncIndex(long timeout, SyncIndexHelpRequest request) {
        syncIndex(request).await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/sync-service-account")
    Uni<Void> syncServiceAccount(SyncServiceAccountHelpRequest request);

    default void syncServiceAccount(long timeout, SyncServiceAccountHelpRequest request) {
        syncServiceAccount(request).await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/fire-event")
    Uni<FireEventInternalResponse> fireEvent(FireEventInternalRequest request);

    default FireEventInternalResponse fireEvent(long timeout, FireEventInternalRequest request) {
        return fireEvent(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/sync-job")
    Uni<SyncJobInternalResponse> syncJob(SyncJobInternalRequest request);

    default SyncJobInternalResponse syncJob(long timeout, SyncJobInternalRequest request) {
        return syncJob(request).await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/delete-job")
    Uni<DeleteJobInternalResponse> deleteJob(DeleteJobInternalRequest request);

    default DeleteJobInternalResponse deleteJob(long timeout, DeleteJobInternalRequest request) {
        return deleteJob(request).await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/schedule-job")
    Uni<Void> scheduleJob(ScheduleJobInternalRequest request);

    default void scheduleJob(long timeout, ScheduleJobInternalRequest request) {
        scheduleJob(request).await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/unschedule-job")
    Uni<Void> unscheduleJob(UnscheduleJobInternalRequest request);

    default void unscheduleJob(long timeout, UnscheduleJobInternalRequest request) {
        unscheduleJob(request).await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/view-logs")
    Uni<ViewLogsHelpResponse> viewLogs(ViewLogsHelpRequest request);

    default ViewLogsHelpResponse viewLogs(long timeout, ViewLogsHelpRequest request) {
        return viewLogs(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
