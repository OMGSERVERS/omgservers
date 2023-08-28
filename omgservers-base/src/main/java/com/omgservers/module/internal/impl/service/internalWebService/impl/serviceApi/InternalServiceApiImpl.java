package com.omgservers.module.internal.impl.service.internalWebService.impl.serviceApi;

import com.omgservers.module.internal.impl.service.internalWebService.InternalWebService;
import com.omgservers.dto.internal.DeleteJobShardedRequest;
import com.omgservers.dto.internal.FireEventShardedRequest;
import com.omgservers.dto.internal.ScheduleJobShardedRequest;
import com.omgservers.dto.internal.ViewLogRequest;
import com.omgservers.dto.internal.ViewLogsResponse;
import com.omgservers.dto.internal.SyncIndexRequest;
import com.omgservers.dto.internal.SyncServiceAccountRequest;
import com.omgservers.operation.handleApiRequest.HandleApiRequestOperation;
import com.omgservers.dto.internal.DeleteJobShardedResponse;
import com.omgservers.dto.internal.FireEventShardedResponse;
import com.omgservers.dto.internal.SyncJobShardedRequest;
import com.omgservers.dto.internal.SyncJobShardedResponse;
import com.omgservers.dto.internal.UnscheduleJobShardedRequest;
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
    public Uni<FireEventShardedResponse> fireEvent(FireEventShardedRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, internalWebService::fireEvent);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<SyncJobShardedResponse> syncJob(SyncJobShardedRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, internalWebService::syncJob);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<DeleteJobShardedResponse> deleteJob(DeleteJobShardedRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, internalWebService::deleteJob);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<Void> scheduleJob(ScheduleJobShardedRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, internalWebService::scheduleJob);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<Void> unscheduleJob(UnscheduleJobShardedRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, internalWebService::unscheduleJob);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<ViewLogsResponse> viewLogs(ViewLogRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, internalWebService::viewLogs);
    }
}
