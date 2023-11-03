package com.omgservers.module.system.impl.service.webService.impl.api;

import com.omgservers.model.dto.system.DeleteJobRequest;
import com.omgservers.model.dto.system.DeleteJobResponse;
import com.omgservers.model.dto.system.ScheduleJobRequest;
import com.omgservers.model.dto.system.SyncIndexRequest;
import com.omgservers.model.dto.system.SyncJobRequest;
import com.omgservers.model.dto.system.SyncJobResponse;
import com.omgservers.model.dto.system.SyncServiceAccountRequest;
import com.omgservers.model.dto.system.UnscheduleJobRequest;
import com.omgservers.model.dto.system.ViewLogRequest;
import com.omgservers.model.dto.system.ViewLogsResponse;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;

import java.time.Duration;

@Path("/omgservers/system-api/v1/request")
public interface SystemApi {

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
    @Path("/sync-job")
    Uni<SyncJobResponse> syncJob(SyncJobRequest request);

    default SyncJobResponse syncJob(long timeout, SyncJobRequest request) {
        return syncJob(request).await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/delete-job")
    Uni<DeleteJobResponse> deleteJob(DeleteJobRequest request);

    default DeleteJobResponse deleteJob(long timeout, DeleteJobRequest request) {
        return deleteJob(request).await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/schedule-job")
    Uni<Void> scheduleJob(ScheduleJobRequest request);

    default void scheduleJob(long timeout, ScheduleJobRequest request) {
        scheduleJob(request).await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/unschedule-job")
    Uni<Void> unscheduleJob(UnscheduleJobRequest request);

    default void unscheduleJob(long timeout, UnscheduleJobRequest request) {
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
