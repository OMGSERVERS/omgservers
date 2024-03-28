package com.omgservers.service.module.pool.impl.service.webService.impl.api;

import com.omgservers.model.dto.pool.DeletePoolRequest;
import com.omgservers.model.dto.pool.DeletePoolResponse;
import com.omgservers.model.dto.pool.DeletePoolServerRefRequest;
import com.omgservers.model.dto.pool.DeletePoolServerRefResponse;
import com.omgservers.model.dto.pool.FindPoolServerRefRequest;
import com.omgservers.model.dto.pool.FindPoolServerRefResponse;
import com.omgservers.model.dto.pool.GetPoolRequest;
import com.omgservers.model.dto.pool.GetPoolResponse;
import com.omgservers.model.dto.pool.GetPoolServerRefRequest;
import com.omgservers.model.dto.pool.GetPoolServerRefResponse;
import com.omgservers.model.dto.pool.SyncPoolRequest;
import com.omgservers.model.dto.pool.SyncPoolResponse;
import com.omgservers.model.dto.pool.SyncPoolServerRefRequest;
import com.omgservers.model.dto.pool.SyncPoolServerRefResponse;
import com.omgservers.service.module.pool.impl.service.webService.WebService;
import com.omgservers.service.operation.handleApiRequest.HandleApiRequestOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class PoolApiImpl implements PoolApi {

    final HandleApiRequestOperation handleApiRequestOperation;
    final WebService webService;

    @Override
    public Uni<GetPoolResponse> getPool(final GetPoolRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::getPool);
    }

    @Override
    public Uni<SyncPoolResponse> syncPool(final SyncPoolRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::syncPool);
    }

    @Override
    public Uni<DeletePoolResponse> deletePool(final DeletePoolRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::deletePool);
    }

    @Override
    public Uni<GetPoolServerRefResponse> getPoolServerRef(final GetPoolServerRefRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::getPoolServerRef);
    }

    @Override
    public Uni<FindPoolServerRefResponse> findPoolServerRef(final FindPoolServerRefRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::findPoolServerRef);
    }

    @Override
    public Uni<SyncPoolServerRefResponse> syncPoolServerRef(final SyncPoolServerRefRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::syncPoolServerRef);
    }

    @Override
    public Uni<DeletePoolServerRefResponse> deletePoolServerRef(final DeletePoolServerRefRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::deletePoolServerRef);
    }
}
