package com.omgservers.module.internal.impl.service.internalWebService.impl.serviceApi;

import com.omgservers.module.internal.impl.service.internalWebService.InternalWebService;
import com.omgservers.dto.internalModule.DeleteJobShardRequest;
import com.omgservers.dto.internalModule.FireEventShardRequest;
import com.omgservers.dto.internalModule.ScheduleJobShardRequest;
import com.omgservers.dto.internalModule.ViewLogRequest;
import com.omgservers.dto.internalModule.ViewLogsResponse;
import com.omgservers.dto.internalModule.SyncIndexRequest;
import com.omgservers.dto.internalModule.SyncServiceAccountRequest;
import com.omgservers.operation.handleApiRequest.HandleApiRequestOperation;
import com.omgservers.dto.internalModule.DeleteJobShardedResponse;
import com.omgservers.dto.internalModule.FireEventShardedResponse;
import com.omgservers.dto.internalModule.SyncJobShardRequest;
import com.omgservers.dto.internalModule.SyncJobRoutedResponse;
import com.omgservers.dto.internalModule.UnscheduleJobShardRequest;
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
    public Uni<FireEventShardedResponse> fireEvent(FireEventShardRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, internalWebService::fireEvent);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<SyncJobRoutedResponse> syncJob(SyncJobShardRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, internalWebService::syncJob);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<DeleteJobShardedResponse> deleteJob(DeleteJobShardRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, internalWebService::deleteJob);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<Void> scheduleJob(ScheduleJobShardRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, internalWebService::scheduleJob);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<Void> unscheduleJob(UnscheduleJobShardRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, internalWebService::unscheduleJob);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<ViewLogsResponse> viewLogs(ViewLogRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, internalWebService::viewLogs);
    }
}
