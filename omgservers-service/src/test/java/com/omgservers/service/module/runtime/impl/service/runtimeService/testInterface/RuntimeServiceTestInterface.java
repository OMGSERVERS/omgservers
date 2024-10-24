package com.omgservers.service.module.runtime.impl.service.runtimeService.testInterface;

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
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class RuntimeServiceTestInterface {
    private static final long TIMEOUT = 1L;

    final RuntimeService runtimeService;

    public GetRuntimeResponse execute(final GetRuntimeRequest request) {
        return runtimeService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public SyncRuntimeResponse execute(final SyncRuntimeRequest request) {
        return runtimeService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public DeleteRuntimeResponse execute(final DeleteRuntimeRequest request) {
        return runtimeService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public SyncRuntimePermissionResponse execute(final SyncRuntimePermissionRequest request) {
        return runtimeService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public ViewRuntimePermissionsResponse execute(final ViewRuntimePermissionsRequest request) {
        return runtimeService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public FindRuntimePermissionResponse execute(final FindRuntimePermissionRequest request) {
        return runtimeService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public DeleteRuntimePermissionResponse execute(final DeleteRuntimePermissionRequest request) {
        return runtimeService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public ViewRuntimeCommandsResponse execute(final ViewRuntimeCommandsRequest request) {
        return runtimeService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public SyncRuntimeCommandResponse execute(final SyncRuntimeCommandRequest request) {
        return runtimeService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public SyncClientCommandResponse execute(final SyncClientCommandRequest request) {
        return runtimeService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public DeleteRuntimeCommandResponse execute(final DeleteRuntimeCommandRequest request) {
        return runtimeService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public DeleteRuntimeCommandsResponse execute(final DeleteRuntimeCommandsRequest request) {
        return runtimeService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public GetRuntimeAssignmentResponse execute(final GetRuntimeAssignmentRequest request) {
        return runtimeService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public FindRuntimeAssignmentResponse execute(final FindRuntimeAssignmentRequest request) {
        return runtimeService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public ViewRuntimeAssignmentsResponse execute(final ViewRuntimeAssignmentsRequest request) {
        return runtimeService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public CountRuntimeAssignmentsResponse execute(final CountRuntimeAssignmentsRequest request) {
        return runtimeService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public SyncRuntimeAssignmentResponse execute(final SyncRuntimeAssignmentRequest request) {
        return runtimeService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public DeleteRuntimeAssignmentResponse execute(final DeleteRuntimeAssignmentRequest request) {
        return runtimeService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public GetRuntimePoolContainerRefResponse execute(
            final GetRuntimePoolContainerRefRequest request) {
        return runtimeService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public FindRuntimePoolContainerRefResponse execute(
            final FindRuntimePoolContainerRefRequest request) {
        return runtimeService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public SyncRuntimePoolContainerRefResponse execute(
            final SyncRuntimePoolContainerRefRequest request) {
        return runtimeService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public DeleteRuntimePoolContainerRefResponse execute(
            final DeleteRuntimePoolContainerRefRequest request) {
        return runtimeService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public InterchangeResponse execute(final InterchangeRequest request) {
        return runtimeService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }
}
