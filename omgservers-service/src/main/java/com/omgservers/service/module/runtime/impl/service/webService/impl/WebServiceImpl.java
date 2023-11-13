package com.omgservers.service.module.runtime.impl.service.webService.impl;

import com.omgservers.model.dto.runtime.DeleteRuntimeCommandRequest;
import com.omgservers.model.dto.runtime.DeleteRuntimeCommandResponse;
import com.omgservers.model.dto.runtime.DeleteRuntimeCommandsRequest;
import com.omgservers.model.dto.runtime.DeleteRuntimeCommandsResponse;
import com.omgservers.model.dto.runtime.DeleteRuntimeGrantRequest;
import com.omgservers.model.dto.runtime.DeleteRuntimeGrantResponse;
import com.omgservers.model.dto.runtime.DeleteRuntimePermissionRequest;
import com.omgservers.model.dto.runtime.DeleteRuntimePermissionResponse;
import com.omgservers.model.dto.runtime.DeleteRuntimeRequest;
import com.omgservers.model.dto.runtime.DeleteRuntimeResponse;
import com.omgservers.model.dto.runtime.DoBroadcastMessageRequest;
import com.omgservers.model.dto.runtime.DoBroadcastMessageResponse;
import com.omgservers.model.dto.runtime.DoChangePlayerRequest;
import com.omgservers.model.dto.runtime.DoChangePlayerResponse;
import com.omgservers.model.dto.runtime.DoKickClientRequest;
import com.omgservers.model.dto.runtime.DoKickClientResponse;
import com.omgservers.model.dto.runtime.DoMulticastMessageRequest;
import com.omgservers.model.dto.runtime.DoMulticastMessageResponse;
import com.omgservers.model.dto.runtime.DoRespondClientRequest;
import com.omgservers.model.dto.runtime.DoRespondClientResponse;
import com.omgservers.model.dto.runtime.DoSetAttributesRequest;
import com.omgservers.model.dto.runtime.DoSetAttributesResponse;
import com.omgservers.model.dto.runtime.DoSetProfileRequest;
import com.omgservers.model.dto.runtime.DoSetProfileResponse;
import com.omgservers.model.dto.runtime.DoStopRuntimeRequest;
import com.omgservers.model.dto.runtime.DoStopRuntimeResponse;
import com.omgservers.model.dto.runtime.DoUnicastMessageRequest;
import com.omgservers.model.dto.runtime.DoUnicastMessageResponse;
import com.omgservers.model.dto.runtime.FindRuntimeGrantRequest;
import com.omgservers.model.dto.runtime.FindRuntimeGrantResponse;
import com.omgservers.model.dto.runtime.FindRuntimePermissionRequest;
import com.omgservers.model.dto.runtime.FindRuntimePermissionResponse;
import com.omgservers.model.dto.runtime.GetRuntimeRequest;
import com.omgservers.model.dto.runtime.GetRuntimeResponse;
import com.omgservers.model.dto.runtime.SyncRuntimeCommandRequest;
import com.omgservers.model.dto.runtime.SyncRuntimeCommandResponse;
import com.omgservers.model.dto.runtime.SyncRuntimeGrantRequest;
import com.omgservers.model.dto.runtime.SyncRuntimeGrantResponse;
import com.omgservers.model.dto.runtime.SyncRuntimePermissionRequest;
import com.omgservers.model.dto.runtime.SyncRuntimePermissionResponse;
import com.omgservers.model.dto.runtime.SyncRuntimeRequest;
import com.omgservers.model.dto.runtime.SyncRuntimeResponse;
import com.omgservers.model.dto.runtime.ViewRuntimeCommandsRequest;
import com.omgservers.model.dto.runtime.ViewRuntimeCommandsResponse;
import com.omgservers.model.dto.runtime.ViewRuntimeGrantsRequest;
import com.omgservers.model.dto.runtime.ViewRuntimeGrantsResponse;
import com.omgservers.model.dto.runtime.ViewRuntimePermissionsRequest;
import com.omgservers.model.dto.runtime.ViewRuntimePermissionsResponse;
import com.omgservers.service.module.runtime.impl.service.doService.DoService;
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
    final DoService doService;

    @Override
    public Uni<SyncRuntimeResponse> syncRuntime(final SyncRuntimeRequest request) {
        return runtimeService.syncRuntime(request);
    }

    @Override
    public Uni<GetRuntimeResponse> getRuntime(final GetRuntimeRequest request) {
        return runtimeService.getRuntime(request);
    }

    @Override
    public Uni<DeleteRuntimeResponse> deleteRuntime(final DeleteRuntimeRequest request) {
        return runtimeService.deleteRuntime(request);
    }

    @Override
    public Uni<SyncRuntimePermissionResponse> syncRuntimePermission(final SyncRuntimePermissionRequest request) {
        return runtimeService.syncRuntimePermission(request);
    }

    @Override
    public Uni<ViewRuntimePermissionsResponse> viewRuntimePermissions(final ViewRuntimePermissionsRequest request) {
        return runtimeService.viewRuntimePermissions(request);
    }

    @Override
    public Uni<FindRuntimePermissionResponse> findRuntimePermission(final FindRuntimePermissionRequest request) {
        return runtimeService.findRuntimePermission(request);
    }

    @Override
    public Uni<DeleteRuntimePermissionResponse> deleteRuntimePermission(final DeleteRuntimePermissionRequest request) {
        return runtimeService.deleteRuntimePermission(request);
    }

    @Override
    public Uni<SyncRuntimeCommandResponse> syncRuntimeCommand(final SyncRuntimeCommandRequest request) {
        return runtimeService.syncRuntimeCommand(request);
    }

    @Override
    public Uni<DeleteRuntimeCommandResponse> deleteRuntimeCommand(final DeleteRuntimeCommandRequest request) {
        return runtimeService.deleteRuntimeCommand(request);
    }

    @Override
    public Uni<ViewRuntimeCommandsResponse> viewRuntimeCommands(final ViewRuntimeCommandsRequest request) {
        return runtimeService.viewRuntimeCommands(request);
    }

    @Override
    public Uni<DeleteRuntimeCommandsResponse> deleteRuntimeCommands(final DeleteRuntimeCommandsRequest request) {
        return runtimeService.deleteRuntimeCommands(request);
    }

    @Override
    public Uni<SyncRuntimeGrantResponse> syncRuntimeGrant(final SyncRuntimeGrantRequest request) {
        return runtimeService.syncRuntimeGrant(request);
    }

    @Override
    public Uni<FindRuntimeGrantResponse> findRuntimeGrant(final FindRuntimeGrantRequest request) {
        return runtimeService.findRuntimeGrant(request);
    }

    @Override
    public Uni<ViewRuntimeGrantsResponse> viewRuntimeGrants(final ViewRuntimeGrantsRequest request) {
        return runtimeService.viewRuntimeGrants(request);
    }

    @Override
    public Uni<DeleteRuntimeGrantResponse> deleteRuntimeGrant(final DeleteRuntimeGrantRequest request) {
        return runtimeService.deleteRuntimeGrant(request);
    }

    @Override
    public Uni<DoRespondClientResponse> doRespondClient(DoRespondClientRequest request) {
        return doService.doRespondClient(request);
    }

    @Override
    public Uni<DoSetAttributesResponse> doSetAttributes(DoSetAttributesRequest request) {
        return doService.doSetAttributes(request);
    }

    @Override
    public Uni<DoSetProfileResponse> doSetProfile(DoSetProfileRequest request) {
        return doService.doSetProfile(request);
    }

    @Override
    public Uni<DoKickClientResponse> doKickClient(final DoKickClientRequest request) {
        return doService.doKickClient(request);
    }

    @Override
    public Uni<DoStopRuntimeResponse> doStopRuntime(final DoStopRuntimeRequest request) {
        return doService.doStopRuntime(request);
    }

    @Override
    public Uni<DoChangePlayerResponse> doChangePlayer(final DoChangePlayerRequest request) {
        return doService.doChangePlayer(request);
    }

    @Override
    public Uni<DoUnicastMessageResponse> doUnicastMessage(final DoUnicastMessageRequest request) {
        return doService.doUnicastMessage(request);
    }

    @Override
    public Uni<DoMulticastMessageResponse> doMulticastMessage(final DoMulticastMessageRequest request) {
        return doService.doMulticastMessage(request);
    }

    @Override
    public Uni<DoBroadcastMessageResponse> doBroadcastMessage(final DoBroadcastMessageRequest request) {
        return doService.doBroadcastMessage(request);
    }
}
