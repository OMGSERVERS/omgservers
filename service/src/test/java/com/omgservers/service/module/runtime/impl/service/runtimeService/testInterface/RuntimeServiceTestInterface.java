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
import com.omgservers.schema.module.runtime.poolServerContainerRef.DeleteRuntimePoolServerContainerRefRequest;
import com.omgservers.schema.module.runtime.poolServerContainerRef.DeleteRuntimePoolServerContainerRefResponse;
import com.omgservers.schema.module.runtime.poolServerContainerRef.FindRuntimePoolServerContainerRefRequest;
import com.omgservers.schema.module.runtime.poolServerContainerRef.FindRuntimePoolServerContainerRefResponse;
import com.omgservers.schema.module.runtime.poolServerContainerRef.GetRuntimePoolServerContainerRefRequest;
import com.omgservers.schema.module.runtime.poolServerContainerRef.GetRuntimePoolServerContainerRefResponse;
import com.omgservers.schema.module.runtime.poolServerContainerRef.SyncRuntimePoolServerContainerRefRequest;
import com.omgservers.schema.module.runtime.poolServerContainerRef.SyncRuntimePoolServerContainerRefResponse;
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

    public GetRuntimeResponse getRuntime(final GetRuntimeRequest request) {
        return runtimeService.getRuntime(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public SyncRuntimeResponse syncRuntime(final SyncRuntimeRequest request) {
        return runtimeService.syncRuntime(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public DeleteRuntimeResponse deleteRuntime(final DeleteRuntimeRequest request) {
        return runtimeService.deleteRuntime(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public SyncRuntimePermissionResponse syncRuntimePermission(final SyncRuntimePermissionRequest request) {
        return runtimeService.syncRuntimePermission(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public ViewRuntimePermissionsResponse viewRuntimePermissions(final ViewRuntimePermissionsRequest request) {
        return runtimeService.viewRuntimePermissions(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public FindRuntimePermissionResponse findRuntimePermission(final FindRuntimePermissionRequest request) {
        return runtimeService.findRuntimePermission(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public DeleteRuntimePermissionResponse deleteRuntimePermission(final DeleteRuntimePermissionRequest request) {
        return runtimeService.deleteRuntimePermission(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public ViewRuntimeCommandsResponse viewRuntimeCommands(final ViewRuntimeCommandsRequest request) {
        return runtimeService.viewRuntimeCommands(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public SyncRuntimeCommandResponse syncRuntimeCommand(final SyncRuntimeCommandRequest request) {
        return runtimeService.syncRuntimeCommand(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public SyncClientCommandResponse syncClientCommand(final SyncClientCommandRequest request) {
        return runtimeService.syncClientCommand(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public DeleteRuntimeCommandResponse deleteRuntimeCommand(final DeleteRuntimeCommandRequest request) {
        return runtimeService.deleteRuntimeCommand(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public DeleteRuntimeCommandsResponse deleteRuntimeCommands(final DeleteRuntimeCommandsRequest request) {
        return runtimeService.deleteRuntimeCommands(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public GetRuntimeAssignmentResponse getRuntimeAssignment(final GetRuntimeAssignmentRequest request) {
        return runtimeService.getRuntimeAssignment(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public FindRuntimeAssignmentResponse findRuntimeAssignment(final FindRuntimeAssignmentRequest request) {
        return runtimeService.findRuntimeAssignment(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public ViewRuntimeAssignmentsResponse viewRuntimeAssignments(final ViewRuntimeAssignmentsRequest request) {
        return runtimeService.viewRuntimeAssignments(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public CountRuntimeAssignmentsResponse countRuntimeAssignments(final CountRuntimeAssignmentsRequest request) {
        return runtimeService.countRuntimeAssignments(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public SyncRuntimeAssignmentResponse syncRuntimeAssignment(final SyncRuntimeAssignmentRequest request) {
        return runtimeService.syncRuntimeAssignment(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public DeleteRuntimeAssignmentResponse deleteRuntimeAssignment(final DeleteRuntimeAssignmentRequest request) {
        return runtimeService.deleteRuntimeAssignment(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public GetRuntimePoolServerContainerRefResponse getRuntimePoolServerContainerRef(
            final GetRuntimePoolServerContainerRefRequest request) {
        return runtimeService.getRuntimePoolServerContainerRef(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public FindRuntimePoolServerContainerRefResponse findRuntimePoolServerContainerRef(
            final FindRuntimePoolServerContainerRefRequest request) {
        return runtimeService.findRuntimePoolServerContainerRef(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public SyncRuntimePoolServerContainerRefResponse syncRuntimePoolServerContainerRef(
            final SyncRuntimePoolServerContainerRefRequest request) {
        return runtimeService.syncRuntimePoolServerContainerRef(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public DeleteRuntimePoolServerContainerRefResponse deleteRuntimePoolServerContainerRef(
            final DeleteRuntimePoolServerContainerRefRequest request) {
        return runtimeService.deleteRuntimePoolServerContainerRef(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public InterchangeResponse interchange(final InterchangeRequest request) {
        return runtimeService.interchange(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }
}
