package com.omgservers.service.module.pool.impl.service.webService.impl;

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
import com.omgservers.service.module.pool.impl.service.poolService.PoolService;
import com.omgservers.service.module.pool.impl.service.webService.WebService;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class WebServiceImpl implements WebService {

    final PoolService poolService;

    @Override
    public Uni<GetPoolResponse> getPool(final GetPoolRequest request) {
        return poolService.getPool(request);
    }

    @Override
    public Uni<SyncPoolResponse> syncPool(final SyncPoolRequest request) {
        return poolService.syncPool(request);
    }

    @Override
    public Uni<DeletePoolResponse> deletePool(final DeletePoolRequest request) {
        return poolService.deletePool(request);
    }

    @Override
    public Uni<GetPoolServerRefResponse> getPoolServerRef(final GetPoolServerRefRequest request) {
        return poolService.getPoolServerRef(request);
    }

    @Override
    public Uni<FindPoolServerRefResponse> findPoolServerRef(final FindPoolServerRefRequest request) {
        return poolService.findPoolServerRef(request);
    }

    @Override
    public Uni<SyncPoolServerRefResponse> syncPoolServerRef(final SyncPoolServerRefRequest request) {
        return poolService.syncPoolServerRef(request);
    }

    @Override
    public Uni<DeletePoolServerRefResponse> deletePoolServerRef(final DeletePoolServerRefRequest request) {
        return poolService.deletePoolServerRef(request);
    }
}
