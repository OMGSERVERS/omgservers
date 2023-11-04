package com.omgservers.service.module.system.impl.service.webService.impl.api;

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
    public Uni<Void> syncIndex(SyncIndexRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::syncIndex);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<Void> syncServiceAccount(SyncServiceAccountRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::syncServiceAccount);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<SyncJobResponse> syncJob(SyncJobRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::syncJob);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<DeleteJobResponse> deleteJob(DeleteJobRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::deleteJob);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<Void> scheduleJob(ScheduleJobRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::scheduleJob);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<Void> unscheduleJob(UnscheduleJobRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::unscheduleJob);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<ViewLogsResponse> viewLogs(ViewLogRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::viewLogs);
    }
}
