package com.omgservers.module.internal.impl.service.internalWebService.impl.serviceApi;

import com.omgservers.dto.internal.DeleteJobShardedRequest;
import com.omgservers.dto.internal.FireEventShardedRequest;
import com.omgservers.dto.internal.ScheduleJobShardedRequest;
import com.omgservers.dto.internal.SyncJobShardedRequest;
import com.omgservers.dto.internal.ViewLogRequest;
import com.omgservers.dto.internal.ViewLogsResponse;
import com.omgservers.dto.internal.SyncIndexRequest;
import com.omgservers.dto.internal.SyncServiceAccountRequest;
import com.omgservers.dto.internal.DeleteJobShardedResponse;
import com.omgservers.dto.internal.FireEventShardedResponse;
import com.omgservers.dto.internal.SyncJobShardedResponse;
import com.omgservers.dto.internal.UnscheduleJobShardedRequest;
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
    Uni<FireEventShardedResponse> fireEvent(FireEventShardedRequest request);

    default FireEventShardedResponse fireEvent(long timeout, FireEventShardedRequest request) {
        return fireEvent(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/sync-job")
    Uni<SyncJobShardedResponse> syncJob(SyncJobShardedRequest request);

    default SyncJobShardedResponse syncJob(long timeout, SyncJobShardedRequest request) {
        return syncJob(request).await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/delete-job")
    Uni<DeleteJobShardedResponse> deleteJob(DeleteJobShardedRequest request);

    default DeleteJobShardedResponse deleteJob(long timeout, DeleteJobShardedRequest request) {
        return deleteJob(request).await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/schedule-job")
    Uni<Void> scheduleJob(ScheduleJobShardedRequest request);

    default void scheduleJob(long timeout, ScheduleJobShardedRequest request) {
        scheduleJob(request).await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/unschedule-job")
    Uni<Void> unscheduleJob(UnscheduleJobShardedRequest request);

    default void unscheduleJob(long timeout, UnscheduleJobShardedRequest request) {
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
