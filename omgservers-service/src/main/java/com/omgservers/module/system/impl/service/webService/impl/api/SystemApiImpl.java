package com.omgservers.module.system.impl.service.webService.impl.api;

import com.omgservers.dto.internal.DeleteJobRequest;
import com.omgservers.dto.internal.DeleteJobResponse;
import com.omgservers.dto.internal.ScheduleJobRequest;
import com.omgservers.dto.internal.SyncIndexRequest;
import com.omgservers.dto.internal.SyncJobRequest;
import com.omgservers.dto.internal.SyncJobResponse;
import com.omgservers.dto.internal.SyncServiceAccountRequest;
import com.omgservers.dto.internal.UnscheduleJobRequest;
import com.omgservers.dto.internal.ViewLogRequest;
import com.omgservers.dto.internal.ViewLogsResponse;
import com.omgservers.model.internalRole.InternalRoleEnum;
import com.omgservers.module.system.impl.service.webService.WebService;
import com.omgservers.operation.handleApiRequest.HandleApiRequestOperation;
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
