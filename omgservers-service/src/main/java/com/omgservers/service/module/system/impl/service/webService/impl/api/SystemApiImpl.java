package com.omgservers.service.module.system.impl.service.webService.impl.api;

import com.omgservers.model.dto.system.DeleteIndexRequest;
import com.omgservers.model.dto.system.DeleteIndexResponse;
import com.omgservers.model.dto.system.DeleteJobRequest;
import com.omgservers.model.dto.system.DeleteJobResponse;
import com.omgservers.model.dto.system.DeleteServiceAccountRequest;
import com.omgservers.model.dto.system.DeleteServiceAccountResponse;
import com.omgservers.model.dto.system.FindIndexRequest;
import com.omgservers.model.dto.system.FindIndexResponse;
import com.omgservers.model.dto.system.FindJobRequest;
import com.omgservers.model.dto.system.FindJobResponse;
import com.omgservers.model.dto.system.FindServiceAccountRequest;
import com.omgservers.model.dto.system.FindServiceAccountResponse;
import com.omgservers.model.dto.system.GetIndexRequest;
import com.omgservers.model.dto.system.GetIndexResponse;
import com.omgservers.model.dto.system.GetJobRequest;
import com.omgservers.model.dto.system.GetJobResponse;
import com.omgservers.model.dto.system.GetServiceAccountRequest;
import com.omgservers.model.dto.system.GetServiceAccountResponse;
import com.omgservers.model.dto.system.ScheduleJobRequest;
import com.omgservers.model.dto.system.SyncIndexRequest;
import com.omgservers.model.dto.system.SyncIndexResponse;
import com.omgservers.model.dto.system.SyncJobRequest;
import com.omgservers.model.dto.system.SyncJobResponse;
import com.omgservers.model.dto.system.SyncServiceAccountRequest;
import com.omgservers.model.dto.system.SyncServiceAccountResponse;
import com.omgservers.model.dto.system.UnscheduleJobRequest;
import com.omgservers.model.dto.system.ViewLogRequest;
import com.omgservers.model.dto.system.ViewLogsResponse;
import com.omgservers.model.internalRole.InternalRoleEnum;
import com.omgservers.service.module.system.impl.service.webService.WebService;
import com.omgservers.service.operation.handleApiRequest.HandleApiRequestOperation;
import io.smallrye.mutiny.Uni;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SystemApiImpl implements SystemApi {

    final HandleApiRequestOperation handleApiRequestOperation;
    final WebService webService;

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<GetIndexResponse> getIndex(final GetIndexRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::getIndex);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<FindIndexResponse> findIndex(final FindIndexRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::findIndex);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<SyncIndexResponse> syncIndex(final SyncIndexRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::syncIndex);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<DeleteIndexResponse> deleteIndex(final DeleteIndexRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::deleteIndex);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<GetServiceAccountResponse> getServiceAccount(GetServiceAccountRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::getServiceAccount);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<FindServiceAccountResponse> findServiceAccount(FindServiceAccountRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::findServiceAccount);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<SyncServiceAccountResponse> syncServiceAccount(final SyncServiceAccountRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::syncServiceAccount);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<DeleteServiceAccountResponse> deleteServiceAccount(DeleteServiceAccountRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::deleteServiceAccount);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<GetJobResponse> getJob(final GetJobRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::getJob);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<FindJobResponse> findJob(final FindJobRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::findJob);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<SyncJobResponse> syncJob(final SyncJobRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::syncJob);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<DeleteJobResponse> deleteJob(final DeleteJobRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::deleteJob);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<Void> scheduleJob(final ScheduleJobRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::scheduleJob);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<Void> unscheduleJob(final UnscheduleJobRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::unscheduleJob);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<ViewLogsResponse> viewLogs(final ViewLogRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::viewLogs);
    }
}
