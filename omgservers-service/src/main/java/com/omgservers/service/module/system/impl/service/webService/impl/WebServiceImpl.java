package com.omgservers.service.module.system.impl.service.webService.impl;

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
import com.omgservers.service.module.system.impl.service.eventService.EventService;
import com.omgservers.service.module.system.impl.service.indexService.IndexService;
import com.omgservers.service.module.system.impl.service.serviceAccountService.ServiceAccountService;
import com.omgservers.service.module.system.impl.service.webService.WebService;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class WebServiceImpl implements WebService {

    final ServiceAccountService serviceAccountService;
    final EventService eventService;
    final IndexService indexService;

    @Override
    public Uni<GetIndexResponse> getIndex(final GetIndexRequest request) {
        return indexService.getIndex(request);
    }

    @Override
    public Uni<FindIndexResponse> findIndex(final FindIndexRequest request) {
        return indexService.findIndex(request);
    }

    @Override
    public Uni<SyncIndexResponse> syncIndex(final SyncIndexRequest request) {
        return indexService.syncIndex(request);
    }

    @Override
    public Uni<DeleteIndexResponse> deleteIndex(final DeleteIndexRequest request) {
        return indexService.deleteIndex(request);
    }

    @Override
    public Uni<GetServiceAccountResponse> getServiceAccount(GetServiceAccountRequest request) {
        return serviceAccountService.getServiceAccount(request);
    }

    @Override
    public Uni<FindServiceAccountResponse> findServiceAccount(FindServiceAccountRequest request) {
        return serviceAccountService.findServiceAccount(request);
    }

    @Override
    public Uni<SyncServiceAccountResponse> syncServiceAccount(final SyncServiceAccountRequest request) {
        return serviceAccountService.syncServiceAccount(request);
    }

    @Override
    public Uni<DeleteServiceAccountResponse> deleteServiceAccount(final DeleteServiceAccountRequest request) {
        return serviceAccountService.deleteServiceAccount(request);
    }
}
