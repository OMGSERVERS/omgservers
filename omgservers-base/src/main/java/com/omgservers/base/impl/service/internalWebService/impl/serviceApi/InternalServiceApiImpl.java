package com.omgservers.base.impl.service.internalWebService.impl.serviceApi;

import com.omgservers.dto.internalModule.SyncIndexHelpRequest;
import com.omgservers.base.impl.service.internalWebService.InternalWebService;
import com.omgservers.base.impl.service.logHelpService.request.ViewLogsHelpRequest;
import com.omgservers.base.impl.service.logHelpService.response.ViewLogsHelpResponse;
import com.omgservers.dto.internalModule.SyncServiceAccountHelpRequest;
import com.omgservers.base.impl.operation.handleApiRequestOperation.HandleApiRequestOperation;
import com.omgservers.dto.internalModule.DeleteJobInternalRequest;
import com.omgservers.dto.internalModule.DeleteJobInternalResponse;
import com.omgservers.dto.internalModule.FireEventInternalRequest;
import com.omgservers.dto.internalModule.FireEventInternalResponse;
import com.omgservers.dto.internalModule.ScheduleJobInternalRequest;
import com.omgservers.dto.internalModule.SyncJobInternalRequest;
import com.omgservers.dto.internalModule.SyncJobInternalResponse;
import com.omgservers.dto.internalModule.UnscheduleJobInternalRequest;
import com.omgservers.model.internalRole.InternalRoleEnum;
import io.smallrye.mutiny.Uni;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class InternalServiceApiImpl implements InternalServiceApi {

    final HandleApiRequestOperation handleApiRequestOperation;
    final InternalWebService internalWebService;

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<Void> syncIndex(SyncIndexHelpRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, internalWebService::syncIndex);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<Void> syncServiceAccount(SyncServiceAccountHelpRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, internalWebService::syncServiceAccount);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<FireEventInternalResponse> fireEvent(FireEventInternalRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, internalWebService::fireEvent);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<SyncJobInternalResponse> syncJob(SyncJobInternalRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, internalWebService::syncJob);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<DeleteJobInternalResponse> deleteJob(DeleteJobInternalRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, internalWebService::deleteJob);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<Void> scheduleJob(ScheduleJobInternalRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, internalWebService::scheduleJob);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<Void> unscheduleJob(UnscheduleJobInternalRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, internalWebService::unscheduleJob);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<ViewLogsHelpResponse> viewLogs(ViewLogsHelpRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, internalWebService::viewLogs);
    }
}
