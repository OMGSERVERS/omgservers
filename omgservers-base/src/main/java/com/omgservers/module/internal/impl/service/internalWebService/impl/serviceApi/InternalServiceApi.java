package com.omgservers.module.internal.impl.service.internalWebService.impl.serviceApi;

import com.omgservers.dto.internalModule.DeleteJobShardRequest;
import com.omgservers.dto.internalModule.FireEventShardRequest;
import com.omgservers.dto.internalModule.ScheduleJobShardRequest;
import com.omgservers.dto.internalModule.SyncJobShardRequest;
import com.omgservers.dto.internalModule.ViewLogRequest;
import com.omgservers.dto.internalModule.ViewLogsResponse;
import com.omgservers.dto.internalModule.SyncIndexRequest;
import com.omgservers.dto.internalModule.SyncServiceAccountRequest;
import com.omgservers.dto.internalModule.DeleteJobShardedResponse;
import com.omgservers.dto.internalModule.FireEventShardedResponse;
import com.omgservers.dto.internalModule.SyncJobRoutedResponse;
import com.omgservers.dto.internalModule.UnscheduleJobShardRequest;
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
    Uni<FireEventShardedResponse> fireEvent(FireEventShardRequest request);

    default FireEventShardedResponse fireEvent(long timeout, FireEventShardRequest request) {
        return fireEvent(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/sync-job")
    Uni<SyncJobRoutedResponse> syncJob(SyncJobShardRequest request);

    default SyncJobRoutedResponse syncJob(long timeout, SyncJobShardRequest request) {
        return syncJob(request).await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/delete-job")
    Uni<DeleteJobShardedResponse> deleteJob(DeleteJobShardRequest request);

    default DeleteJobShardedResponse deleteJob(long timeout, DeleteJobShardRequest request) {
        return deleteJob(request).await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/schedule-job")
    Uni<Void> scheduleJob(ScheduleJobShardRequest request);

    default void scheduleJob(long timeout, ScheduleJobShardRequest request) {
        scheduleJob(request).await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/unschedule-job")
    Uni<Void> unscheduleJob(UnscheduleJobShardRequest request);

    default void unscheduleJob(long timeout, UnscheduleJobShardRequest request) {
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
