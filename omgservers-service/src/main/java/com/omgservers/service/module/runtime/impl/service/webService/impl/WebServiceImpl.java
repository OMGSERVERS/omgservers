package com.omgservers.service.module.runtime.impl.service.webService.impl;

import com.omgservers.schema.module.runtime.CountRuntimeAssignmentsRequest;
import com.omgservers.schema.module.runtime.CountRuntimeAssignmentsResponse;
import com.omgservers.schema.module.runtime.DeleteRuntimeAssignmentRequest;
import com.omgservers.schema.module.runtime.DeleteRuntimeAssignmentResponse;
import com.omgservers.schema.module.runtime.DeleteRuntimeCommandRequest;
import com.omgservers.schema.module.runtime.DeleteRuntimeCommandResponse;
import com.omgservers.schema.module.runtime.DeleteRuntimeCommandsRequest;
import com.omgservers.schema.module.runtime.DeleteRuntimeCommandsResponse;
import com.omgservers.schema.module.runtime.DeleteRuntimePermissionRequest;
import com.omgservers.schema.module.runtime.DeleteRuntimePermissionResponse;
import com.omgservers.schema.module.runtime.DeleteRuntimeRequest;
import com.omgservers.schema.module.runtime.DeleteRuntimeResponse;
import com.omgservers.schema.module.runtime.FindRuntimeAssignmentRequest;
import com.omgservers.schema.module.runtime.FindRuntimeAssignmentResponse;
import com.omgservers.schema.module.runtime.FindRuntimePermissionRequest;
import com.omgservers.schema.module.runtime.FindRuntimePermissionResponse;
import com.omgservers.schema.module.runtime.GetRuntimeAssignmentRequest;
import com.omgservers.schema.module.runtime.GetRuntimeAssignmentResponse;
import com.omgservers.schema.module.runtime.GetRuntimeRequest;
import com.omgservers.schema.module.runtime.GetRuntimeResponse;
import com.omgservers.schema.module.runtime.InterchangeRequest;
import com.omgservers.schema.module.runtime.InterchangeResponse;
import com.omgservers.schema.module.runtime.SyncClientCommandRequest;
import com.omgservers.schema.module.runtime.SyncClientCommandResponse;
import com.omgservers.schema.module.runtime.SyncRuntimeAssignmentRequest;
import com.omgservers.schema.module.runtime.SyncRuntimeAssignmentResponse;
import com.omgservers.schema.module.runtime.SyncRuntimeCommandRequest;
import com.omgservers.schema.module.runtime.SyncRuntimeCommandResponse;
import com.omgservers.schema.module.runtime.SyncRuntimePermissionRequest;
import com.omgservers.schema.module.runtime.SyncRuntimePermissionResponse;
import com.omgservers.schema.module.runtime.SyncRuntimeRequest;
import com.omgservers.schema.module.runtime.SyncRuntimeResponse;
import com.omgservers.schema.module.runtime.UpdateRuntimeAssignmentLastActivityRequest;
import com.omgservers.schema.module.runtime.UpdateRuntimeAssignmentLastActivityResponse;
import com.omgservers.schema.module.runtime.ViewRuntimeAssignmentsRequest;
import com.omgservers.schema.module.runtime.ViewRuntimeAssignmentsResponse;
import com.omgservers.schema.module.runtime.ViewRuntimeCommandsRequest;
import com.omgservers.schema.module.runtime.ViewRuntimeCommandsResponse;
import com.omgservers.schema.module.runtime.ViewRuntimePermissionsRequest;
import com.omgservers.schema.module.runtime.ViewRuntimePermissionsResponse;
import com.omgservers.schema.module.runtime.poolContainerRef.DeleteRuntimePoolContainerRefRequest;
import com.omgservers.schema.module.runtime.poolContainerRef.DeleteRuntimePoolContainerRefResponse;
import com.omgservers.schema.module.runtime.poolContainerRef.FindRuntimePoolContainerRefRequest;
import com.omgservers.schema.module.runtime.poolContainerRef.FindRuntimePoolContainerRefResponse;
import com.omgservers.schema.module.runtime.poolContainerRef.GetRuntimePoolContainerRefRequest;
import com.omgservers.schema.module.runtime.poolContainerRef.GetRuntimePoolContainerRefResponse;
import com.omgservers.schema.module.runtime.poolContainerRef.SyncRuntimePoolContainerRefRequest;
import com.omgservers.schema.module.runtime.poolContainerRef.SyncRuntimePoolContainerRefResponse;
import com.omgservers.service.module.runtime.impl.service.runtimeService.RuntimeService;
import com.omgservers.service.module.runtime.impl.service.webService.WebService;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class WebServiceImpl implements WebService {

    final RuntimeService runtimeService;

    @Override
    public Uni<SyncRuntimeResponse> execute(final SyncRuntimeRequest request) {
        return runtimeService.execute(request);
    }

    @Override
    public Uni<GetRuntimeResponse> execute(final GetRuntimeRequest request) {
        return runtimeService.execute(request);
    }

    @Override
    public Uni<DeleteRuntimeResponse> execute(final DeleteRuntimeRequest request) {
        return runtimeService.execute(request);
    }

    @Override
    public Uni<SyncRuntimePermissionResponse> execute(final SyncRuntimePermissionRequest request) {
        return runtimeService.execute(request);
    }

    @Override
    public Uni<ViewRuntimePermissionsResponse> execute(final ViewRuntimePermissionsRequest request) {
        return runtimeService.execute(request);
    }

    @Override
    public Uni<FindRuntimePermissionResponse> execute(final FindRuntimePermissionRequest request) {
        return runtimeService.execute(request);
    }

    @Override
    public Uni<DeleteRuntimePermissionResponse> execute(final DeleteRuntimePermissionRequest request) {
        return runtimeService.execute(request);
    }

    @Override
    public Uni<SyncRuntimeCommandResponse> execute(final SyncRuntimeCommandRequest request) {
        return runtimeService.execute(request);
    }

    @Override
    public Uni<SyncClientCommandResponse> execute(final SyncClientCommandRequest request) {
        return runtimeService.execute(request);
    }

    @Override
    public Uni<DeleteRuntimeCommandResponse> execute(final DeleteRuntimeCommandRequest request) {
        return runtimeService.execute(request);
    }

    @Override
    public Uni<ViewRuntimeCommandsResponse> execute(final ViewRuntimeCommandsRequest request) {
        return runtimeService.execute(request);
    }

    @Override
    public Uni<DeleteRuntimeCommandsResponse> execute(final DeleteRuntimeCommandsRequest request) {
        return runtimeService.execute(request);
    }

    @Override
    public Uni<GetRuntimeAssignmentResponse> execute(final GetRuntimeAssignmentRequest request) {
        return runtimeService.execute(request);
    }

    @Override
    public Uni<FindRuntimeAssignmentResponse> execute(final FindRuntimeAssignmentRequest request) {
        return runtimeService.execute(request);
    }

    @Override
    public Uni<ViewRuntimeAssignmentsResponse> execute(final ViewRuntimeAssignmentsRequest request) {
        return runtimeService.execute(request);
    }

    @Override
    public Uni<CountRuntimeAssignmentsResponse> execute(final CountRuntimeAssignmentsRequest request) {
        return runtimeService.execute(request);
    }

    @Override
    public Uni<SyncRuntimeAssignmentResponse> execute(final SyncRuntimeAssignmentRequest request) {
        return runtimeService.execute(request);
    }

    @Override
    public Uni<UpdateRuntimeAssignmentLastActivityResponse> execute(
            final UpdateRuntimeAssignmentLastActivityRequest request) {
        return runtimeService.execute(request);
    }

    @Override
    public Uni<DeleteRuntimeAssignmentResponse> execute(final DeleteRuntimeAssignmentRequest request) {
        return runtimeService.execute(request);
    }

    @Override
    public Uni<GetRuntimePoolContainerRefResponse> execute(
            final GetRuntimePoolContainerRefRequest request) {
        return runtimeService.execute(request);
    }

    @Override
    public Uni<FindRuntimePoolContainerRefResponse> execute(
            final FindRuntimePoolContainerRefRequest request) {
        return runtimeService.execute(request);
    }

    @Override
    public Uni<SyncRuntimePoolContainerRefResponse> execute(
            final SyncRuntimePoolContainerRefRequest request) {
        return runtimeService.execute(request);
    }

    @Override
    public Uni<DeleteRuntimePoolContainerRefResponse> execute(
            final DeleteRuntimePoolContainerRefRequest request) {
        return runtimeService.execute(request);
    }

    @Override
    public Uni<InterchangeResponse> execute(final InterchangeRequest request) {
        return runtimeService.execute(request);
    }
}
