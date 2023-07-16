package com.omgservers.application.module.internalModule.impl.service.internalWebService.impl.serviceApi;

import com.omgservers.application.module.internalModule.impl.service.eventInternalService.request.FireEventInternalRequest;
import com.omgservers.application.module.internalModule.impl.service.indexHelpService.request.SyncIndexHelpRequest;
import com.omgservers.application.module.internalModule.impl.service.jobInternalService.request.DeleteJobInternalRequest;
import com.omgservers.application.module.internalModule.impl.service.jobInternalService.request.SyncJobInternalRequest;
import com.omgservers.application.module.internalModule.impl.service.jobInternalService.response.DeleteJobInternalResponse;
import com.omgservers.application.module.internalModule.impl.service.jobInternalService.response.SyncJobInternalResponse;
import com.omgservers.application.module.internalModule.impl.service.jobSchedulerService.request.ScheduleJobInternalRequest;
import com.omgservers.application.module.internalModule.impl.service.jobSchedulerService.request.UnscheduleJobInternalRequest;
import com.omgservers.application.module.internalModule.impl.service.serviceAccountHelpService.request.SyncServiceAccountHelpRequest;
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
    Uni<Void> fireEvent(FireEventInternalRequest request);

    default void fireEvent(long timeout, FireEventInternalRequest request) {
        fireEvent(request).await().atMost(Duration.ofSeconds(timeout));
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
}
