package com.omgservers.service.module.pool.impl.service.poolService.impl;

import com.omgservers.model.dto.pool.pool.DeletePoolRequest;
import com.omgservers.model.dto.pool.pool.DeletePoolResponse;
import com.omgservers.model.dto.pool.pool.GetPoolRequest;
import com.omgservers.model.dto.pool.pool.GetPoolResponse;
import com.omgservers.model.dto.pool.pool.SyncPoolRequest;
import com.omgservers.model.dto.pool.pool.SyncPoolResponse;
import com.omgservers.model.dto.pool.poolRuntimeAssignment.DeletePoolRuntimeAssignmentRequest;
import com.omgservers.model.dto.pool.poolRuntimeAssignment.DeletePoolRuntimeAssignmentResponse;
import com.omgservers.model.dto.pool.poolRuntimeAssignment.FindPoolRuntimeAssignmentRequest;
import com.omgservers.model.dto.pool.poolRuntimeAssignment.FindPoolRuntimeAssignmentResponse;
import com.omgservers.model.dto.pool.poolRuntimeAssignment.GetPoolRuntimeAssignmentRequest;
import com.omgservers.model.dto.pool.poolRuntimeAssignment.GetPoolRuntimeAssignmentResponse;
import com.omgservers.model.dto.pool.poolRuntimeAssignment.SyncPoolRuntimeAssignmentRequest;
import com.omgservers.model.dto.pool.poolRuntimeAssignment.SyncPoolRuntimeAssignmentResponse;
import com.omgservers.model.dto.pool.poolRuntimeAssignment.ViewPoolRuntimeAssignmentsRequest;
import com.omgservers.model.dto.pool.poolRuntimeAssignment.ViewPoolRuntimeAssignmentsResponse;
import com.omgservers.model.dto.pool.poolRuntimeServerContainerRef.DeletePoolRuntimeServerContainerRefRequest;
import com.omgservers.model.dto.pool.poolRuntimeServerContainerRef.DeletePoolRuntimeServerContainerRefResponse;
import com.omgservers.model.dto.pool.poolRuntimeServerContainerRef.FindPoolRuntimeServerContainerRefRequest;
import com.omgservers.model.dto.pool.poolRuntimeServerContainerRef.FindPoolRuntimeServerContainerRefResponse;
import com.omgservers.model.dto.pool.poolRuntimeServerContainerRef.GetPoolRuntimeServerContainerRefRequest;
import com.omgservers.model.dto.pool.poolRuntimeServerContainerRef.GetPoolRuntimeServerContainerRefResponse;
import com.omgservers.model.dto.pool.poolRuntimeServerContainerRef.SyncPoolRuntimeServerContainerRefRequest;
import com.omgservers.model.dto.pool.poolRuntimeServerContainerRef.SyncPoolRuntimeServerContainerRefResponse;
import com.omgservers.model.dto.pool.poolRuntimeServerContainerRef.ViewPoolRuntimeServerContainerRefRequest;
import com.omgservers.model.dto.pool.poolRuntimeServerContainerRef.ViewPoolRuntimeServerContainerRefResponse;
import com.omgservers.model.dto.pool.poolRuntimeServerContainerRequest.DeletePoolRuntimeServerContainerRequestRequest;
import com.omgservers.model.dto.pool.poolRuntimeServerContainerRequest.DeletePoolRuntimeServerContainerRequestResponse;
import com.omgservers.model.dto.pool.poolRuntimeServerContainerRequest.FindPoolRuntimeServerContainerRequestRequest;
import com.omgservers.model.dto.pool.poolRuntimeServerContainerRequest.FindPoolRuntimeServerContainerRequestResponse;
import com.omgservers.model.dto.pool.poolRuntimeServerContainerRequest.GetPoolRuntimeServerContainerRequestRequest;
import com.omgservers.model.dto.pool.poolRuntimeServerContainerRequest.GetPoolRuntimeServerContainerRequestResponse;
import com.omgservers.model.dto.pool.poolRuntimeServerContainerRequest.SyncPoolRuntimeServerContainerRequestRequest;
import com.omgservers.model.dto.pool.poolRuntimeServerContainerRequest.SyncPoolRuntimeServerContainerRequestResponse;
import com.omgservers.model.dto.pool.poolRuntimeServerContainerRequest.ViewPoolRuntimeServerContainerRequestsRequest;
import com.omgservers.model.dto.pool.poolRuntimeServerContainerRequest.ViewPoolRuntimeServerContainerRequestsResponse;
import com.omgservers.model.dto.pool.poolServerRef.DeletePoolServerRefRequest;
import com.omgservers.model.dto.pool.poolServerRef.DeletePoolServerRefResponse;
import com.omgservers.model.dto.pool.poolServerRef.FindPoolServerRefRequest;
import com.omgservers.model.dto.pool.poolServerRef.FindPoolServerRefResponse;
import com.omgservers.model.dto.pool.poolServerRef.GetPoolServerRefRequest;
import com.omgservers.model.dto.pool.poolServerRef.GetPoolServerRefResponse;
import com.omgservers.model.dto.pool.poolServerRef.SyncPoolServerRefRequest;
import com.omgservers.model.dto.pool.poolServerRef.SyncPoolServerRefResponse;
import com.omgservers.model.dto.pool.poolServerRef.ViewPoolServerRefsRequest;
import com.omgservers.model.dto.pool.poolServerRef.ViewPoolServerRefsResponse;
import com.omgservers.service.module.pool.impl.operation.getPoolModuleClient.GetPoolModuleClientOperation;
import com.omgservers.service.module.pool.impl.service.poolService.PoolService;
import com.omgservers.service.module.pool.impl.service.poolService.impl.method.pool.deletePool.DeletePoolMethod;
import com.omgservers.service.module.pool.impl.service.poolService.impl.method.pool.getPool.GetPoolMethod;
import com.omgservers.service.module.pool.impl.service.poolService.impl.method.pool.syncPool.SyncPoolMethod;
import com.omgservers.service.module.pool.impl.service.poolService.impl.method.poolRuntimeAssignment.deletePoolRuntimeAssignment.DeletePoolRuntimeAssignmentMethod;
import com.omgservers.service.module.pool.impl.service.poolService.impl.method.poolRuntimeAssignment.findPoolRuntimeAssignment.FindPoolRuntimeAssignmentMethod;
import com.omgservers.service.module.pool.impl.service.poolService.impl.method.poolRuntimeAssignment.getPoolRuntimeAssignment.GetPoolRuntimeAssignmentMethod;
import com.omgservers.service.module.pool.impl.service.poolService.impl.method.poolRuntimeAssignment.syncPoolRuntimeAssignment.SyncPoolRuntimeAssignmentMethod;
import com.omgservers.service.module.pool.impl.service.poolService.impl.method.poolRuntimeAssignment.viewPoolRuntimeAssignment.ViewPoolRuntimeAssignmentsMethod;
import com.omgservers.service.module.pool.impl.service.poolService.impl.method.poolRuntimeServerContainerRef.deletePoolRuntimeServerContainerRef.DeletePoolRuntimeServerContainerRefMethod;
import com.omgservers.service.module.pool.impl.service.poolService.impl.method.poolRuntimeServerContainerRef.findPoolRuntimeServerContainerRef.FindPoolRuntimeServerContainerRefMethod;
import com.omgservers.service.module.pool.impl.service.poolService.impl.method.poolRuntimeServerContainerRef.getPoolRuntimeServerContainerRef.GetPoolRuntimeServerContainerRefMethod;
import com.omgservers.service.module.pool.impl.service.poolService.impl.method.poolRuntimeServerContainerRef.syncPoolRuntimeServerContainerRef.SyncPoolRuntimeServerContainerRefMethod;
import com.omgservers.service.module.pool.impl.service.poolService.impl.method.poolRuntimeServerContainerRef.viewPoolRuntimeServerContainerRef.ViewPoolRuntimeServerContainerRefsMethod;
import com.omgservers.service.module.pool.impl.service.poolService.impl.method.poolRuntimeServerContainerRequest.deletePoolRuntimeServerContainerRequest.DeletePoolRuntimeServerContainerRequestMethod;
import com.omgservers.service.module.pool.impl.service.poolService.impl.method.poolRuntimeServerContainerRequest.findPoolRuntimeServerContainerRequest.FindPoolRuntimeServerContainerRequestMethod;
import com.omgservers.service.module.pool.impl.service.poolService.impl.method.poolRuntimeServerContainerRequest.getPoolRuntimeServerContainerRequest.GetPoolRuntimeServerContainerRequestMethod;
import com.omgservers.service.module.pool.impl.service.poolService.impl.method.poolRuntimeServerContainerRequest.syncPoolRuntimeServerContainerRequest.SyncPoolRuntimeServerContainerRequestMethod;
import com.omgservers.service.module.pool.impl.service.poolService.impl.method.poolRuntimeServerContainerRequest.viewPoolRuntimeServerContainerRequest.ViewPoolRuntimeServerContainerRequestsMethod;
import com.omgservers.service.module.pool.impl.service.poolService.impl.method.poolServerRef.deletePoolServerRef.DeletePoolServerRefMethod;
import com.omgservers.service.module.pool.impl.service.poolService.impl.method.poolServerRef.findPoolServerRef.FindPoolServerRefMethod;
import com.omgservers.service.module.pool.impl.service.poolService.impl.method.poolServerRef.getPoolServerRef.GetPoolServerRefMethod;
import com.omgservers.service.module.pool.impl.service.poolService.impl.method.poolServerRef.syncPoolServerRef.SyncPoolServerRefMethod;
import com.omgservers.service.module.pool.impl.service.poolService.impl.method.poolServerRef.viewPoolServerRefs.ViewPoolServerRefsMethod;
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

    final DeletePoolRuntimeServerContainerRequestMethod deletePoolRuntimeServerContainerRequestMethod;
    final ViewPoolRuntimeServerContainerRequestsMethod viewPoolRuntimeServerContainerRequestsMethod;
    final FindPoolRuntimeServerContainerRequestMethod findPoolRuntimeServerContainerRequestMethod;
    final SyncPoolRuntimeServerContainerRequestMethod syncPoolRuntimeServerContainerRequestMethod;
    final GetPoolRuntimeServerContainerRequestMethod getPoolRuntimeServerContainerRequestMethod;
    final DeletePoolRuntimeServerContainerRefMethod deletePoolRuntimeServerContainerRefMethod;
    final ViewPoolRuntimeServerContainerRefsMethod viewPoolRuntimeServerContainerRefsMethod;
    final FindPoolRuntimeServerContainerRefMethod findPoolRuntimeServerContainerRefMethod;
    final SyncPoolRuntimeServerContainerRefMethod syncPoolRuntimeServerContainerRefMethod;
    final GetPoolRuntimeServerContainerRefMethod getPoolRuntimeServerContainerRefMethod;
    final DeletePoolRuntimeAssignmentMethod deletePoolRuntimeAssignmentMethod;
    final ViewPoolRuntimeAssignmentsMethod viewPoolRuntimeAssignmentsMethod;
    final FindPoolRuntimeAssignmentMethod findPoolRuntimeAssignmentMethod;
    final SyncPoolRuntimeAssignmentMethod syncPoolRuntimeAssignmentMethod;
    final GetPoolRuntimeAssignmentMethod getPoolRuntimeAssignmentMethod;
    final DeletePoolServerRefMethod deletePoolServerRefMethod;
    final ViewPoolServerRefsMethod viewPoolServerRefsMethod;
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
    public Uni<ViewPoolServerRefsResponse> viewPoolServerRefs(@Valid final ViewPoolServerRefsRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getMatchServiceApiClientOperation::getClient,
                PoolApi::viewPoolServerRefs,
                viewPoolServerRefsMethod::viewPoolServerRefs);
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

    @Override
    public Uni<GetPoolRuntimeServerContainerRequestResponse> getPoolRuntimeServerContainerRequest(
            @Valid final GetPoolRuntimeServerContainerRequestRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getMatchServiceApiClientOperation::getClient,
                PoolApi::getPoolRuntimeServerContainerRequest,
                getPoolRuntimeServerContainerRequestMethod::getPoolRuntimeServerContainerRequest);
    }

    @Override
    public Uni<FindPoolRuntimeServerContainerRequestResponse> findPoolRuntimeServerContainerRequest(
            @Valid final FindPoolRuntimeServerContainerRequestRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getMatchServiceApiClientOperation::getClient,
                PoolApi::findPoolRuntimeServerContainerRequest,
                findPoolRuntimeServerContainerRequestMethod::findPoolRuntimeServerContainerRequest);
    }

    @Override
    public Uni<ViewPoolRuntimeServerContainerRequestsResponse> viewPoolRuntimeServerContainerRequests(
            @Valid final ViewPoolRuntimeServerContainerRequestsRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getMatchServiceApiClientOperation::getClient,
                PoolApi::viewPoolRuntimeServerContainerRequests,
                viewPoolRuntimeServerContainerRequestsMethod::viewPoolRuntimeServerContainerRequests);
    }

    @Override
    public Uni<SyncPoolRuntimeServerContainerRequestResponse> syncPoolRuntimeServerContainerRequest(
            @Valid final SyncPoolRuntimeServerContainerRequestRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getMatchServiceApiClientOperation::getClient,
                PoolApi::syncPoolRuntimeServerContainerRequest,
                syncPoolRuntimeServerContainerRequestMethod::syncPoolRuntimeServerContainerRequest);
    }

    @Override
    public Uni<DeletePoolRuntimeServerContainerRequestResponse> deletePoolRuntimeServerContainerRequest(
            @Valid final DeletePoolRuntimeServerContainerRequestRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getMatchServiceApiClientOperation::getClient,
                PoolApi::deletePoolRuntimeServerContainerRequest,
                deletePoolRuntimeServerContainerRequestMethod::deletePoolRuntimeServerContainerRequest);
    }

    @Override
    public Uni<GetPoolRuntimeAssignmentResponse> getPoolRuntimeAssignment(
            @Valid final GetPoolRuntimeAssignmentRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getMatchServiceApiClientOperation::getClient,
                PoolApi::getPoolRuntimeAssignment,
                getPoolRuntimeAssignmentMethod::getPoolRuntimeAssignment);
    }

    @Override
    public Uni<FindPoolRuntimeAssignmentResponse> findPoolRuntimeAssignment(
            @Valid final FindPoolRuntimeAssignmentRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getMatchServiceApiClientOperation::getClient,
                PoolApi::findPoolRuntimeAssignment,
                findPoolRuntimeAssignmentMethod::findPoolRuntimeAssignment);
    }

    @Override
    public Uni<ViewPoolRuntimeAssignmentsResponse> viewPoolRuntimeAssignments(
            @Valid final ViewPoolRuntimeAssignmentsRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getMatchServiceApiClientOperation::getClient,
                PoolApi::viewPoolRuntimeAssignments,
                viewPoolRuntimeAssignmentsMethod::viewPoolRuntimeAssignments);
    }

    @Override
    public Uni<SyncPoolRuntimeAssignmentResponse> syncPoolRuntimeAssignment(
            @Valid final SyncPoolRuntimeAssignmentRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getMatchServiceApiClientOperation::getClient,
                PoolApi::syncPoolRuntimeAssignment,
                syncPoolRuntimeAssignmentMethod::syncPoolRuntimeAssignment);
    }

    @Override
    public Uni<DeletePoolRuntimeAssignmentResponse> deletePoolRuntimeAssignment(
            @Valid final DeletePoolRuntimeAssignmentRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getMatchServiceApiClientOperation::getClient,
                PoolApi::deletePoolRuntimeAssignment,
                deletePoolRuntimeAssignmentMethod::deletePoolRuntimeAssignment);
    }

    @Override
    public Uni<GetPoolRuntimeServerContainerRefResponse> getPoolRuntimeServerContainerRef(
            @Valid final GetPoolRuntimeServerContainerRefRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getMatchServiceApiClientOperation::getClient,
                PoolApi::getPoolRuntimeServerContainerRef,
                getPoolRuntimeServerContainerRefMethod::getPoolRuntimeServerContainerRef);
    }

    @Override
    public Uni<FindPoolRuntimeServerContainerRefResponse> findPoolRuntimeServerContainerRef(
            @Valid final FindPoolRuntimeServerContainerRefRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getMatchServiceApiClientOperation::getClient,
                PoolApi::findPoolRuntimeServerContainerRef,
                findPoolRuntimeServerContainerRefMethod::findPoolRuntimeServerContainerRef);
    }

    @Override
    public Uni<ViewPoolRuntimeServerContainerRefResponse> viewPoolRuntimeServerContainerRefs(
            @Valid final ViewPoolRuntimeServerContainerRefRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getMatchServiceApiClientOperation::getClient,
                PoolApi::viewPoolRuntimeServerContainerRefs,
                viewPoolRuntimeServerContainerRefsMethod::viewPoolRuntimeServerContainerRefs);
    }

    @Override
    public Uni<SyncPoolRuntimeServerContainerRefResponse> syncPoolRuntimeServerContainerRef(
            @Valid final SyncPoolRuntimeServerContainerRefRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getMatchServiceApiClientOperation::getClient,
                PoolApi::syncPoolRuntimeServerContainerRef,
                syncPoolRuntimeServerContainerRefMethod::syncPoolRuntimeServerContainerRef);
    }

    @Override
    public Uni<DeletePoolRuntimeServerContainerRefResponse> deletePoolRuntimeServerContainerRef(
            @Valid final DeletePoolRuntimeServerContainerRefRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getMatchServiceApiClientOperation::getClient,
                PoolApi::deletePoolRuntimeServerContainerRef,
                deletePoolRuntimeServerContainerRefMethod::deletePoolRuntimeServerContainerRef);
    }
}
