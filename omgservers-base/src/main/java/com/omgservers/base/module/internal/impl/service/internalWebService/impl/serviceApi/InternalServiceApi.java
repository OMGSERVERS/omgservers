package com.omgservers.base.module.internal.impl.service.internalWebService.impl.serviceApi;

import com.omgservers.dto.internalModule.DeleteJobRoutedRequest;
import com.omgservers.dto.internalModule.FireEventRoutedRequest;
import com.omgservers.dto.internalModule.SyncJobRoutedRequest;
import com.omgservers.dto.internalModule.ViewLogRequest;
import com.omgservers.dto.internalModule.ViewLogsResponse;
import com.omgservers.dto.internalModule.SyncIndexRequest;
import com.omgservers.dto.internalModule.SyncServiceAccountRequest;
import com.omgservers.dto.internalModule.DeleteJobRoutedResponse;
import com.omgservers.dto.internalModule.FireEventRoutedResponse;
import com.omgservers.dto.internalModule.ScheduleJobRoutedRequest;
import com.omgservers.dto.internalModule.SyncJobRoutedResponse;
import com.omgservers.dto.internalModule.UnscheduleJobRoutedRequest;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;

import java.time.Duration;

@Path("/omgservers/service-api/internal-api/v1/request")
public interface InternalServiceApi {

    @PUT
    @Path("/sync-index")
    Uni<Void> syncIndex(SyncIndexRequest request);

    default void syncIndex(long timeout, SyncIndexRequest request) {
        syncIndex(request).await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/sync-service-account")
    Uni<Void> syncServiceAccount(SyncServiceAccountRequest request);

    default void syncServiceAccount(long timeout, SyncServiceAccountRequest request) {
        syncServiceAccount(request).await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/fire-event")
    Uni<FireEventRoutedResponse> fireEvent(FireEventRoutedRequest request);

    default FireEventRoutedResponse fireEvent(long timeout, FireEventRoutedRequest request) {
        return fireEvent(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/sync-job")
    Uni<SyncJobRoutedResponse> syncJob(SyncJobRoutedRequest request);

    default SyncJobRoutedResponse syncJob(long timeout, SyncJobRoutedRequest request) {
        return syncJob(request).await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/delete-job")
    Uni<DeleteJobRoutedResponse> deleteJob(DeleteJobRoutedRequest request);

    default DeleteJobRoutedResponse deleteJob(long timeout, DeleteJobRoutedRequest request) {
        return deleteJob(request).await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/schedule-job")
    Uni<Void> scheduleJob(ScheduleJobRoutedRequest request);

    default void scheduleJob(long timeout, ScheduleJobRoutedRequest request) {
        scheduleJob(request).await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/unschedule-job")
    Uni<Void> unscheduleJob(UnscheduleJobRoutedRequest request);

    default void unscheduleJob(long timeout, UnscheduleJobRoutedRequest request) {
        unscheduleJob(request).await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/view-logs")
    Uni<ViewLogsResponse> viewLogs(ViewLogRequest request);

    default ViewLogsResponse viewLogs(long timeout, ViewLogRequest request) {
        return viewLogs(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
