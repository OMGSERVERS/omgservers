package com.omgservers.service.module.system.impl.service.webService.impl.api;

import com.omgservers.model.dto.system.DeleteIndexRequest;
import com.omgservers.model.dto.system.DeleteIndexResponse;
import com.omgservers.model.dto.system.DeleteServiceAccountRequest;
import com.omgservers.model.dto.system.DeleteServiceAccountResponse;
import com.omgservers.model.dto.system.FindIndexRequest;
import com.omgservers.model.dto.system.FindIndexResponse;
import com.omgservers.model.dto.system.FindServiceAccountRequest;
import com.omgservers.model.dto.system.FindServiceAccountResponse;
import com.omgservers.model.dto.system.GetIndexRequest;
import com.omgservers.model.dto.system.GetIndexResponse;
import com.omgservers.model.dto.system.GetServiceAccountRequest;
import com.omgservers.model.dto.system.GetServiceAccountResponse;
import com.omgservers.model.dto.system.SyncIndexRequest;
import com.omgservers.model.dto.system.SyncIndexResponse;
import com.omgservers.model.dto.system.SyncServiceAccountRequest;
import com.omgservers.model.dto.system.SyncServiceAccountResponse;
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
@RolesAllowed({InternalRoleEnum.Names.SERVICE})
class SystemApiImpl implements SystemApi {

    final HandleApiRequestOperation handleApiRequestOperation;
    final WebService webService;

    @Override
    public Uni<GetIndexResponse> getIndex(final GetIndexRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::getIndex);
    }

    @Override
    public Uni<FindIndexResponse> findIndex(final FindIndexRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::findIndex);
    }

    @Override
    public Uni<SyncIndexResponse> syncIndex(final SyncIndexRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::syncIndex);
    }

    @Override
    public Uni<DeleteIndexResponse> deleteIndex(final DeleteIndexRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::deleteIndex);
    }

    @Override
    public Uni<GetServiceAccountResponse> getServiceAccount(GetServiceAccountRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::getServiceAccount);
    }

    @Override
    public Uni<FindServiceAccountResponse> findServiceAccount(FindServiceAccountRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::findServiceAccount);
    }

    @Override
    public Uni<SyncServiceAccountResponse> syncServiceAccount(final SyncServiceAccountRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::syncServiceAccount);
    }

    @Override
    public Uni<DeleteServiceAccountResponse> deleteServiceAccount(DeleteServiceAccountRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::deleteServiceAccount);
    }
}
