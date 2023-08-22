package com.omgservers.application.module.internalModule.impl.service.internalWebService.impl.serviceApi;

import com.omgservers.application.module.internalModule.impl.service.eventInternalService.request.FireEventInternalRequest;
import com.omgservers.application.module.internalModule.impl.service.eventInternalService.response.FireEventInternalResponse;
import com.omgservers.application.module.internalModule.impl.service.indexHelpService.request.SyncIndexHelpRequest;
import com.omgservers.application.module.internalModule.impl.service.internalWebService.InternalWebService;
import com.omgservers.application.module.internalModule.impl.service.jobInternalService.request.DeleteJobInternalRequest;
import com.omgservers.application.module.internalModule.impl.service.jobInternalService.request.SyncJobInternalRequest;
import com.omgservers.application.module.internalModule.impl.service.jobInternalService.response.DeleteJobInternalResponse;
import com.omgservers.application.module.internalModule.impl.service.jobInternalService.response.SyncJobInternalResponse;
import com.omgservers.application.module.internalModule.impl.service.jobSchedulerService.request.ScheduleJobInternalRequest;
import com.omgservers.application.module.internalModule.impl.service.jobSchedulerService.request.UnscheduleJobInternalRequest;
import com.omgservers.application.module.internalModule.impl.service.logHelpService.request.ViewLogsHelpRequest;
import com.omgservers.application.module.internalModule.impl.service.logHelpService.response.ViewLogsHelpResponse;
import com.omgservers.application.module.internalModule.impl.service.serviceAccountHelpService.request.SyncServiceAccountHelpRequest;
import com.omgservers.application.module.securityModule.model.InternalRoleEnum;
import com.omgservers.application.operation.handleApiRequestOperation.HandleApiRequestOperation;
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
