package com.omgservers.service.module.system.impl.service.webService.impl.api;

import com.omgservers.model.dto.system.DeleteEntityRequest;
import com.omgservers.model.dto.system.DeleteEntityResponse;
import com.omgservers.model.dto.system.DeleteHandlerRequest;
import com.omgservers.model.dto.system.DeleteHandlerResponse;
import com.omgservers.model.dto.system.DeleteIndexRequest;
import com.omgservers.model.dto.system.DeleteIndexResponse;
import com.omgservers.model.dto.system.DeleteJobRequest;
import com.omgservers.model.dto.system.DeleteJobResponse;
import com.omgservers.model.dto.system.DeleteServiceAccountRequest;
import com.omgservers.model.dto.system.DeleteServiceAccountResponse;
import com.omgservers.model.dto.system.FindEntityRequest;
import com.omgservers.model.dto.system.FindEntityResponse;
import com.omgservers.model.dto.system.FindIndexRequest;
import com.omgservers.model.dto.system.FindIndexResponse;
import com.omgservers.model.dto.system.FindJobRequest;
import com.omgservers.model.dto.system.FindJobResponse;
import com.omgservers.model.dto.system.FindServiceAccountRequest;
import com.omgservers.model.dto.system.FindServiceAccountResponse;
import com.omgservers.model.dto.system.GetHandlerRequest;
import com.omgservers.model.dto.system.GetHandlerResponse;
import com.omgservers.model.dto.system.GetIndexRequest;
import com.omgservers.model.dto.system.GetIndexResponse;
import com.omgservers.model.dto.system.GetJobRequest;
import com.omgservers.model.dto.system.GetJobResponse;
import com.omgservers.model.dto.system.GetServiceAccountRequest;
import com.omgservers.model.dto.system.GetServiceAccountResponse;
import com.omgservers.model.dto.system.ScheduleJobRequest;
import com.omgservers.model.dto.system.SyncEntityRequest;
import com.omgservers.model.dto.system.SyncEntityResponse;
import com.omgservers.model.dto.system.SyncHandlerRequest;
import com.omgservers.model.dto.system.SyncHandlerResponse;
import com.omgservers.model.dto.system.SyncIndexRequest;
import com.omgservers.model.dto.system.SyncIndexResponse;
import com.omgservers.model.dto.system.SyncJobRequest;
import com.omgservers.model.dto.system.SyncJobResponse;
import com.omgservers.model.dto.system.SyncServiceAccountRequest;
import com.omgservers.model.dto.system.SyncServiceAccountResponse;
import com.omgservers.model.dto.system.UnscheduleJobRequest;
import com.omgservers.model.dto.system.ViewHandlersRequest;
import com.omgservers.model.dto.system.ViewHandlersResponse;
import com.omgservers.model.dto.system.ViewLogRequest;
import com.omgservers.model.dto.system.ViewLogsResponse;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;

@Path("/omgservers/system-api/v1/request")
public interface SystemApi {

    @PUT
    @Path("/get-index")
    Uni<GetIndexResponse> getIndex(GetIndexRequest request);

    @PUT
    @Path("/find-index")
    Uni<FindIndexResponse> findIndex(FindIndexRequest request);

    @PUT
    @Path("/sync-index")
    Uni<SyncIndexResponse> syncIndex(SyncIndexRequest request);

    @PUT
    @Path("/delete-index")
    Uni<DeleteIndexResponse> deleteIndex(DeleteIndexRequest request);

    @PUT
    @Path("/get-service-account")
    Uni<GetServiceAccountResponse> getServiceAccount(GetServiceAccountRequest request);

    @PUT
    @Path("/find-service-account")
    Uni<FindServiceAccountResponse> findServiceAccount(FindServiceAccountRequest request);

    @PUT
    @Path("/sync-service-account")
    Uni<SyncServiceAccountResponse> syncServiceAccount(SyncServiceAccountRequest request);

    @PUT
    @Path("/delete-service-account")
    Uni<DeleteServiceAccountResponse> deleteServiceAccount(DeleteServiceAccountRequest request);

    @PUT
    @Path("/get-handler")
    Uni<GetHandlerResponse> getHandler(GetHandlerRequest request);

    @PUT
    @Path("/view-handlers")
    Uni<ViewHandlersResponse> viewHandlers(ViewHandlersRequest request);

    @PUT
    @Path("/sync-handler")
    Uni<SyncHandlerResponse> syncHandler(SyncHandlerRequest request);

    @PUT
    @Path("/delete-handler")
    Uni<DeleteHandlerResponse> deleteHandler(DeleteHandlerRequest request);

    @PUT
    @Path("/get-job")
    Uni<GetJobResponse> getJob(GetJobRequest request);

    @PUT
    @Path("/find-job")
    Uni<FindJobResponse> findJob(FindJobRequest request);

    @PUT
    @Path("/sync-job")
    Uni<SyncJobResponse> syncJob(SyncJobRequest request);

    @PUT
    @Path("/delete-job")
    Uni<DeleteJobResponse> deleteJob(DeleteJobRequest request);

    @PUT
    @Path("/schedule-job")
    Uni<Void> scheduleJob(ScheduleJobRequest request);

    @PUT
    @Path("/unschedule-job")
    Uni<Void> unscheduleJob(UnscheduleJobRequest request);

    @PUT
    @Path("/view-logs")
    Uni<ViewLogsResponse> viewLogs(ViewLogRequest request);

    @PUT
    @Path("/find-entity")
    Uni<FindEntityResponse> findEntity(FindEntityRequest request);

    @PUT
    @Path("/sync-entity")
    Uni<SyncEntityResponse> syncEntity(SyncEntityRequest request);

    @PUT
    @Path("/delete-entity")
    Uni<DeleteEntityResponse> deleteEntity(DeleteEntityRequest request);
}
