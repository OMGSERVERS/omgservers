package com.omgservers.base.module.internal.impl.service.internalWebService.impl.serviceApi;

import com.omgservers.base.module.internal.impl.service.internalWebService.InternalWebService;
import com.omgservers.dto.internalModule.DeleteJobRoutedRequest;
import com.omgservers.dto.internalModule.ScheduleJobRoutedRequest;
import com.omgservers.dto.internalModule.ViewLogRequest;
import com.omgservers.dto.internalModule.ViewLogsResponse;
import com.omgservers.dto.internalModule.SyncIndexRequest;
import com.omgservers.dto.internalModule.SyncServiceAccountRequest;
import com.omgservers.base.operation.handleApiRequest.HandleApiRequestOperation;
import com.omgservers.dto.internalModule.DeleteJobRoutedResponse;
import com.omgservers.dto.internalModule.FireEventRoutedRequest;
import com.omgservers.dto.internalModule.FireEventRoutedResponse;
import com.omgservers.dto.internalModule.SyncJobRoutedRequest;
import com.omgservers.dto.internalModule.SyncJobRoutedResponse;
import com.omgservers.dto.internalModule.UnscheduleJobRoutedRequest;
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
    public Uni<Void> syncIndex(SyncIndexRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, internalWebService::syncIndex);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<Void> syncServiceAccount(SyncServiceAccountRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, internalWebService::syncServiceAccount);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<FireEventRoutedResponse> fireEvent(FireEventRoutedRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, internalWebService::fireEvent);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<SyncJobRoutedResponse> syncJob(SyncJobRoutedRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, internalWebService::syncJob);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<DeleteJobRoutedResponse> deleteJob(DeleteJobRoutedRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, internalWebService::deleteJob);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<Void> scheduleJob(ScheduleJobRoutedRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, internalWebService::scheduleJob);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<Void> unscheduleJob(UnscheduleJobRoutedRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, internalWebService::unscheduleJob);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<ViewLogsResponse> viewLogs(ViewLogRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, internalWebService::viewLogs);
    }
}
