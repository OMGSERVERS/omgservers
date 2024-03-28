package com.omgservers.service.module.pool.impl.service.poolService.impl;

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
import com.omgservers.service.module.pool.impl.operation.getPoolModuleClient.GetPoolModuleClientOperation;
import com.omgservers.service.module.pool.impl.service.poolService.PoolService;
import com.omgservers.service.module.pool.impl.service.poolService.impl.method.deletePool.DeletePoolMethod;
import com.omgservers.service.module.pool.impl.service.poolService.impl.method.deletePoolServerRef.DeletePoolServerRefMethod;
import com.omgservers.service.module.pool.impl.service.poolService.impl.method.findPoolServerRef.FindPoolServerRefMethod;
import com.omgservers.service.module.pool.impl.service.poolService.impl.method.getPool.GetPoolMethod;
import com.omgservers.service.module.pool.impl.service.poolService.impl.method.getPoolServerRef.GetPoolServerRefMethod;
import com.omgservers.service.module.pool.impl.service.poolService.impl.method.syncPool.SyncPoolMethod;
import com.omgservers.service.module.pool.impl.service.poolService.impl.method.syncPoolServerRef.SyncPoolServerRefMethod;
import com.omgservers.service.module.pool.impl.service.webService.impl.api.PoolApi;
import com.omgservers.service.operation.calculateShard.CalculateShardOperation;
import com.omgservers.service.operation.handleInternalRequest.HandleInternalRequestOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class PoolServiceImpl implements PoolService {

    final DeletePoolServerRefMethod deletePoolServerRefMethod;
    final FindPoolServerRefMethod findPoolServerRefMethod;
    final SyncPoolServerRefMethod syncPoolServerRefMethod;
    final GetPoolServerRefMethod getPoolServerRefMethod;
    final DeletePoolMethod deletePoolMethod;
    final SyncPoolMethod syncPoolMethod;
    final GetPoolMethod getPoolMethod;

    final GetPoolModuleClientOperation getMatchServiceApiClientOperation;
    final HandleInternalRequestOperation handleInternalRequestOperation;
    final CalculateShardOperation calculateShardOperation;

    @Override
    public Uni<GetPoolResponse> getPool(@Valid final GetPoolRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getMatchServiceApiClientOperation::getClient,
                PoolApi::getPool,
                getPoolMethod::getPool);
    }

    @Override
    public Uni<SyncPoolResponse> syncPool(@Valid final SyncPoolRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getMatchServiceApiClientOperation::getClient,
                PoolApi::syncPool,
                syncPoolMethod::syncPool);
    }

    @Override
    public Uni<DeletePoolResponse> deletePool(@Valid final DeletePoolRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getMatchServiceApiClientOperation::getClient,
                PoolApi::deletePool,
                deletePoolMethod::deletePool);
    }

    @Override
    public Uni<GetPoolServerRefResponse> getPoolServerRef(@Valid final GetPoolServerRefRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getMatchServiceApiClientOperation::getClient,
                PoolApi::getPoolServerRef,
                getPoolServerRefMethod::getPoolServerRef);
    }

    @Override
    public Uni<FindPoolServerRefResponse> findPoolServerRef(@Valid final FindPoolServerRefRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getMatchServiceApiClientOperation::getClient,
                PoolApi::findPoolServerRef,
                findPoolServerRefMethod::findPoolServerRef);
    }

    @Override
    public Uni<SyncPoolServerRefResponse> syncPoolServerRef(@Valid final SyncPoolServerRefRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getMatchServiceApiClientOperation::getClient,
                PoolApi::syncPoolServerRef,
                syncPoolServerRefMethod::syncPoolServerRef);
    }

    @Override
    public Uni<DeletePoolServerRefResponse> deletePoolServerRef(@Valid final DeletePoolServerRefRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getMatchServiceApiClientOperation::getClient,
                PoolApi::deletePoolServerRef,
                deletePoolServerRefMethod::deletePoolServerRef);
    }
}
