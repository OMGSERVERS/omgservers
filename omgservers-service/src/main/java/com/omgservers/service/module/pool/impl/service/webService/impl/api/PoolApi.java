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
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Tag(name = "Pool Module API")
@Path("/omgservers/v1/module/pool/request")
public interface PoolApi {

    @PUT
    @Path("/get-pool")
    Uni<GetPoolResponse> getPool(GetPoolRequest request);

    @PUT
    @Path("/sync-pool")
    Uni<SyncPoolResponse> syncPool(SyncPoolRequest request);

    @PUT
    @Path("/delete-pool")
    Uni<DeletePoolResponse> deletePool(DeletePoolRequest request);

    @PUT
    @Path("/get-pool-server-ref")
    Uni<GetPoolServerRefResponse> getPoolServerRef(GetPoolServerRefRequest request);

    @PUT
    @Path("/find-pool-server-ref")
    Uni<FindPoolServerRefResponse> findPoolServerRef(FindPoolServerRefRequest request);

    @PUT
    @Path("/view-pool-server-ref")
    Uni<ViewPoolServerRefsResponse> viewPoolServerRefs(ViewPoolServerRefsRequest request);

    @PUT
    @Path("/sync-pool-server-ref")
    Uni<SyncPoolServerRefResponse> syncPoolServerRef(SyncPoolServerRefRequest request);

    @PUT
    @Path("/delete-pool-server-ref")
    Uni<DeletePoolServerRefResponse> deletePoolServerRef(DeletePoolServerRefRequest request);

    @PUT
    @Path("/get-pool-runtime-server-container-request")
    Uni<GetPoolRuntimeServerContainerRequestResponse> getPoolRuntimeServerContainerRequest(
            GetPoolRuntimeServerContainerRequestRequest request);

    @PUT
    @Path("/find-pool-runtime-server-container-request")
    Uni<FindPoolRuntimeServerContainerRequestResponse> findPoolRuntimeServerContainerRequest(
            FindPoolRuntimeServerContainerRequestRequest request);

    @PUT
    @Path("/view-pool-runtime-server-container-requests")
    Uni<ViewPoolRuntimeServerContainerRequestsResponse> viewPoolRuntimeServerContainerRequests(
            ViewPoolRuntimeServerContainerRequestsRequest request);

    @PUT
    @Path("/sync-pool-runtime-server-container-request")
    Uni<SyncPoolRuntimeServerContainerRequestResponse> syncPoolRuntimeServerContainerRequest(
            SyncPoolRuntimeServerContainerRequestRequest request);

    @PUT
    @Path("/delete-pool-runtime-server-container-request")
    Uni<DeletePoolRuntimeServerContainerRequestResponse> deletePoolRuntimeServerContainerRequest(
            DeletePoolRuntimeServerContainerRequestRequest request);

    @PUT
    @Path("/get-pool-runtime-assignment")
    Uni<GetPoolRuntimeAssignmentResponse> getPoolRuntimeAssignment(
            GetPoolRuntimeAssignmentRequest request);

    @PUT
    @Path("/find-pool-runtime-assignment")
    Uni<FindPoolRuntimeAssignmentResponse> findPoolRuntimeAssignment(
            FindPoolRuntimeAssignmentRequest request);

    @PUT
    @Path("/view-pool-runtime-assignments")
    Uni<ViewPoolRuntimeAssignmentsResponse> viewPoolRuntimeAssignments(
            ViewPoolRuntimeAssignmentsRequest request);

    @PUT
    @Path("/sync-pool-runtime-assignment")
    Uni<SyncPoolRuntimeAssignmentResponse> syncPoolRuntimeAssignment(
            SyncPoolRuntimeAssignmentRequest request);

    @PUT
    @Path("/delete-pool-runtime-assignment")
    Uni<DeletePoolRuntimeAssignmentResponse> deletePoolRuntimeAssignment(
            DeletePoolRuntimeAssignmentRequest request);

    @PUT
    @Path("/get-pool-runtime-server-container-ref")
    Uni<GetPoolRuntimeServerContainerRefResponse> getPoolRuntimeServerContainerRef(
            GetPoolRuntimeServerContainerRefRequest request);

    @PUT
    @Path("/find-pool-runtime-server-container-ref")
    Uni<FindPoolRuntimeServerContainerRefResponse> findPoolRuntimeServerContainerRef(
            FindPoolRuntimeServerContainerRefRequest request);

    @PUT
    @Path("/view-pool-runtime-server-container-ref")
    Uni<ViewPoolRuntimeServerContainerRefResponse> viewPoolRuntimeServerContainerRefs(
            ViewPoolRuntimeServerContainerRefRequest request);

    @PUT
    @Path("/sync-pool-runtime-server-container-ref")
    Uni<SyncPoolRuntimeServerContainerRefResponse> syncPoolRuntimeServerContainerRef(
            SyncPoolRuntimeServerContainerRefRequest request);

    @PUT
    @Path("/delete-pool-runtime-server-container-ref")
    Uni<DeletePoolRuntimeServerContainerRefResponse> deletePoolRuntimeServerContainerRef(
            DeletePoolRuntimeServerContainerRefRequest request);
}
