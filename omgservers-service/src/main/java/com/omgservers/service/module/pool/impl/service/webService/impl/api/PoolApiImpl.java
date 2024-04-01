package com.omgservers.service.module.pool.impl.service.webService.impl.api;

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
    public Uni<ViewPoolServerRefsResponse> viewPoolServerRefs(final ViewPoolServerRefsRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::viewPoolServerRefs);
    }

    @Override
    public Uni<SyncPoolServerRefResponse> syncPoolServerRef(final SyncPoolServerRefRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::syncPoolServerRef);
    }

    @Override
    public Uni<DeletePoolServerRefResponse> deletePoolServerRef(final DeletePoolServerRefRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::deletePoolServerRef);
    }

    @Override
    public Uni<GetPoolRuntimeServerContainerRequestResponse> getPoolRuntimeServerContainerRequest(
            final GetPoolRuntimeServerContainerRequestRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request,
                webService::getPoolRuntimeServerContainerRequest);
    }

    @Override
    public Uni<FindPoolRuntimeServerContainerRequestResponse> findPoolRuntimeServerContainerRequest(
            final FindPoolRuntimeServerContainerRequestRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request,
                webService::findPoolRuntimeServerContainerRequest);
    }

    @Override
    public Uni<ViewPoolRuntimeServerContainerRequestsResponse> viewPoolRuntimeServerContainerRequests(
            final ViewPoolRuntimeServerContainerRequestsRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request,
                webService::viewPoolRuntimeServerContainerRequests);
    }

    @Override
    public Uni<SyncPoolRuntimeServerContainerRequestResponse> syncPoolRuntimeServerContainerRequest(
            final SyncPoolRuntimeServerContainerRequestRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request,
                webService::syncPoolRuntimeServerContainerRequest);
    }

    @Override
    public Uni<DeletePoolRuntimeServerContainerRequestResponse> deletePoolRuntimeServerContainerRequest(
            final DeletePoolRuntimeServerContainerRequestRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request,
                webService::deletePoolRuntimeServerContainerRequest);
    }

    @Override
    public Uni<GetPoolRuntimeAssignmentResponse> getPoolRuntimeAssignment(
            final GetPoolRuntimeAssignmentRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::getPoolRuntimeAssignment);
    }

    @Override
    public Uni<FindPoolRuntimeAssignmentResponse> findPoolRuntimeAssignment(
            final FindPoolRuntimeAssignmentRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::findPoolRuntimeAssignment);
    }

    @Override
    public Uni<ViewPoolRuntimeAssignmentsResponse> viewPoolRuntimeAssignments(
            final ViewPoolRuntimeAssignmentsRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::viewPoolRuntimeAssignments);
    }

    @Override
    public Uni<SyncPoolRuntimeAssignmentResponse> syncPoolRuntimeAssignment(
            final SyncPoolRuntimeAssignmentRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::syncPoolRuntimeAssignment);
    }

    @Override
    public Uni<DeletePoolRuntimeAssignmentResponse> deletePoolRuntimeAssignment(
            final DeletePoolRuntimeAssignmentRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::deletePoolRuntimeAssignment);
    }

    @Override
    public Uni<GetPoolRuntimeServerContainerRefResponse> getPoolRuntimeServerContainerRef(
            final GetPoolRuntimeServerContainerRefRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::getPoolRuntimeServerContainerRef);
    }

    @Override
    public Uni<FindPoolRuntimeServerContainerRefResponse> findPoolRuntimeServerContainerRef(
            final FindPoolRuntimeServerContainerRefRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::findPoolRuntimeServerContainerRef);
    }

    @Override
    public Uni<ViewPoolRuntimeServerContainerRefResponse> viewPoolRuntimeServerContainerRefs(
            final ViewPoolRuntimeServerContainerRefRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::viewPoolRuntimeServerContainerRefs);
    }

    @Override
    public Uni<SyncPoolRuntimeServerContainerRefResponse> syncPoolRuntimeServerContainerRef(
            final SyncPoolRuntimeServerContainerRefRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::syncPoolRuntimeServerContainerRef);
    }

    @Override
    public Uni<DeletePoolRuntimeServerContainerRefResponse> deletePoolRuntimeServerContainerRef(
            final DeletePoolRuntimeServerContainerRefRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request,
                webService::deletePoolRuntimeServerContainerRef);
    }
}
