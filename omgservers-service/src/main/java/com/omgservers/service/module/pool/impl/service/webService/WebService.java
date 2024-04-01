package com.omgservers.service.module.pool.impl.service.webService;

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
import io.smallrye.mutiny.Uni;

public interface WebService {

    Uni<GetPoolResponse> getPool(GetPoolRequest request);

    Uni<SyncPoolResponse> syncPool(SyncPoolRequest request);

    Uni<DeletePoolResponse> deletePool(DeletePoolRequest request);

    Uni<GetPoolServerRefResponse> getPoolServerRef(GetPoolServerRefRequest request);

    Uni<FindPoolServerRefResponse> findPoolServerRef(FindPoolServerRefRequest request);

    Uni<ViewPoolServerRefsResponse> viewPoolServerRefs(ViewPoolServerRefsRequest request);

    Uni<SyncPoolServerRefResponse> syncPoolServerRef(SyncPoolServerRefRequest request);

    Uni<DeletePoolServerRefResponse> deletePoolServerRef(DeletePoolServerRefRequest request);

    Uni<GetPoolRuntimeServerContainerRequestResponse> getPoolRuntimeServerContainerRequest(
            GetPoolRuntimeServerContainerRequestRequest request);

    Uni<FindPoolRuntimeServerContainerRequestResponse> findPoolRuntimeServerContainerRequest(
            FindPoolRuntimeServerContainerRequestRequest request);

    Uni<ViewPoolRuntimeServerContainerRequestsResponse> viewPoolRuntimeServerContainerRequests(
            ViewPoolRuntimeServerContainerRequestsRequest request);

    Uni<SyncPoolRuntimeServerContainerRequestResponse> syncPoolRuntimeServerContainerRequest(
            SyncPoolRuntimeServerContainerRequestRequest request);

    Uni<DeletePoolRuntimeServerContainerRequestResponse> deletePoolRuntimeServerContainerRequest(
            DeletePoolRuntimeServerContainerRequestRequest request);

    Uni<GetPoolRuntimeAssignmentResponse> getPoolRuntimeAssignment(
            GetPoolRuntimeAssignmentRequest request);

    Uni<FindPoolRuntimeAssignmentResponse> findPoolRuntimeAssignment(
            FindPoolRuntimeAssignmentRequest request);

    Uni<ViewPoolRuntimeAssignmentsResponse> viewPoolRuntimeAssignments(
            ViewPoolRuntimeAssignmentsRequest request);

    Uni<SyncPoolRuntimeAssignmentResponse> syncPoolRuntimeAssignment(
            SyncPoolRuntimeAssignmentRequest request);

    Uni<DeletePoolRuntimeAssignmentResponse> deletePoolRuntimeAssignment(
            DeletePoolRuntimeAssignmentRequest request);

    Uni<GetPoolRuntimeServerContainerRefResponse> getPoolRuntimeServerContainerRef(
            GetPoolRuntimeServerContainerRefRequest request);

    Uni<FindPoolRuntimeServerContainerRefResponse> findPoolRuntimeServerContainerRef(
            FindPoolRuntimeServerContainerRefRequest request);

    Uni<ViewPoolRuntimeServerContainerRefResponse> viewPoolRuntimeServerContainerRefs(
            ViewPoolRuntimeServerContainerRefRequest request);

    Uni<SyncPoolRuntimeServerContainerRefResponse> syncPoolRuntimeServerContainerRef(
            SyncPoolRuntimeServerContainerRefRequest request);

    Uni<DeletePoolRuntimeServerContainerRefResponse> deletePoolRuntimeServerContainerRef(
            DeletePoolRuntimeServerContainerRefRequest request);
}
