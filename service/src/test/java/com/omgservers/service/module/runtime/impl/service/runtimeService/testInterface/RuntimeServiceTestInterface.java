package com.omgservers.service.module.runtime.impl.service.runtimeService.testInterface;

import com.omgservers.model.dto.runtime.CountRuntimeAssignmentsRequest;
import com.omgservers.model.dto.runtime.CountRuntimeAssignmentsResponse;
import com.omgservers.model.dto.runtime.DeleteRuntimeAssignmentRequest;
import com.omgservers.model.dto.runtime.DeleteRuntimeAssignmentResponse;
import com.omgservers.model.dto.runtime.DeleteRuntimeCommandRequest;
import com.omgservers.model.dto.runtime.DeleteRuntimeCommandResponse;
import com.omgservers.model.dto.runtime.DeleteRuntimeCommandsRequest;
import com.omgservers.model.dto.runtime.DeleteRuntimeCommandsResponse;
import com.omgservers.model.dto.runtime.DeleteRuntimePermissionRequest;
import com.omgservers.model.dto.runtime.DeleteRuntimePermissionResponse;
import com.omgservers.model.dto.runtime.DeleteRuntimeRequest;
import com.omgservers.model.dto.runtime.DeleteRuntimeResponse;
import com.omgservers.model.dto.runtime.FindRuntimeAssignmentRequest;
import com.omgservers.model.dto.runtime.FindRuntimeAssignmentResponse;
import com.omgservers.model.dto.runtime.FindRuntimePermissionRequest;
import com.omgservers.model.dto.runtime.FindRuntimePermissionResponse;
import com.omgservers.model.dto.runtime.GetRuntimeAssignmentRequest;
import com.omgservers.model.dto.runtime.GetRuntimeAssignmentResponse;
import com.omgservers.model.dto.runtime.GetRuntimeRequest;
import com.omgservers.model.dto.runtime.GetRuntimeResponse;
import com.omgservers.model.dto.runtime.InterchangeRequest;
import com.omgservers.model.dto.runtime.InterchangeResponse;
import com.omgservers.model.dto.runtime.SyncClientCommandRequest;
import com.omgservers.model.dto.runtime.SyncClientCommandResponse;
import com.omgservers.model.dto.runtime.SyncRuntimeAssignmentRequest;
import com.omgservers.model.dto.runtime.SyncRuntimeAssignmentResponse;
import com.omgservers.model.dto.runtime.SyncRuntimeCommandRequest;
import com.omgservers.model.dto.runtime.SyncRuntimeCommandResponse;
import com.omgservers.model.dto.runtime.SyncRuntimePermissionRequest;
import com.omgservers.model.dto.runtime.SyncRuntimePermissionResponse;
import com.omgservers.model.dto.runtime.SyncRuntimeRequest;
import com.omgservers.model.dto.runtime.SyncRuntimeResponse;
import com.omgservers.model.dto.runtime.ViewRuntimeAssignmentsRequest;
import com.omgservers.model.dto.runtime.ViewRuntimeAssignmentsResponse;
import com.omgservers.model.dto.runtime.ViewRuntimeCommandsRequest;
import com.omgservers.model.dto.runtime.ViewRuntimeCommandsResponse;
import com.omgservers.model.dto.runtime.ViewRuntimePermissionsRequest;
import com.omgservers.model.dto.runtime.ViewRuntimePermissionsResponse;
import com.omgservers.model.dto.runtime.poolServerContainerRef.DeleteRuntimePoolServerContainerRefRequest;
import com.omgservers.model.dto.runtime.poolServerContainerRef.DeleteRuntimePoolServerContainerRefResponse;
import com.omgservers.model.dto.runtime.poolServerContainerRef.FindRuntimePoolServerContainerRefRequest;
import com.omgservers.model.dto.runtime.poolServerContainerRef.FindRuntimePoolServerContainerRefResponse;
import com.omgservers.model.dto.runtime.poolServerContainerRef.GetRuntimePoolServerContainerRefRequest;
import com.omgservers.model.dto.runtime.poolServerContainerRef.GetRuntimePoolServerContainerRefResponse;
import com.omgservers.model.dto.runtime.poolServerContainerRef.SyncRuntimePoolServerContainerRefRequest;
import com.omgservers.model.dto.runtime.poolServerContainerRef.SyncRuntimePoolServerContainerRefResponse;
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
