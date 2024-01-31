package com.omgservers.service.module.runtime.impl.service.webService.impl;

import com.omgservers.model.dto.runtime.CountRuntimeClientsRequest;
import com.omgservers.model.dto.runtime.CountRuntimeClientsResponse;
import com.omgservers.model.dto.runtime.DeleteRuntimeClientRequest;
import com.omgservers.model.dto.runtime.DeleteRuntimeClientResponse;
import com.omgservers.model.dto.runtime.DeleteRuntimeCommandRequest;
import com.omgservers.model.dto.runtime.DeleteRuntimeCommandResponse;
import com.omgservers.model.dto.runtime.DeleteRuntimeCommandsRequest;
import com.omgservers.model.dto.runtime.DeleteRuntimeCommandsResponse;
import com.omgservers.model.dto.runtime.DeleteRuntimePermissionRequest;
import com.omgservers.model.dto.runtime.DeleteRuntimePermissionResponse;
import com.omgservers.model.dto.runtime.DeleteRuntimeRequest;
import com.omgservers.model.dto.runtime.DeleteRuntimeResponse;
import com.omgservers.model.dto.runtime.DoBroadcastMessageRequest;
import com.omgservers.model.dto.runtime.DoBroadcastMessageResponse;
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
import com.omgservers.model.dto.runtime.FindRuntimeClientRequest;
import com.omgservers.model.dto.runtime.FindRuntimeClientResponse;
import com.omgservers.model.dto.runtime.FindRuntimePermissionRequest;
import com.omgservers.model.dto.runtime.FindRuntimePermissionResponse;
import com.omgservers.model.dto.runtime.GetRuntimeClientRequest;
import com.omgservers.model.dto.runtime.GetRuntimeClientResponse;
import com.omgservers.model.dto.runtime.GetRuntimeRequest;
import com.omgservers.model.dto.runtime.GetRuntimeResponse;
import com.omgservers.model.dto.runtime.SyncRuntimeClientRequest;
import com.omgservers.model.dto.runtime.SyncRuntimeClientResponse;
import com.omgservers.model.dto.runtime.SyncRuntimeCommandRequest;
import com.omgservers.model.dto.runtime.SyncRuntimeCommandResponse;
import com.omgservers.model.dto.runtime.SyncRuntimePermissionRequest;
import com.omgservers.model.dto.runtime.SyncRuntimePermissionResponse;
import com.omgservers.model.dto.runtime.SyncRuntimeRequest;
import com.omgservers.model.dto.runtime.SyncRuntimeResponse;
import com.omgservers.model.dto.runtime.ViewRuntimeClientsRequest;
import com.omgservers.model.dto.runtime.ViewRuntimeClientsResponse;
import com.omgservers.model.dto.runtime.ViewRuntimeCommandsRequest;
import com.omgservers.model.dto.runtime.ViewRuntimeCommandsResponse;
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
    public Uni<SyncRuntimeClientResponse> syncRuntimeClient(final SyncRuntimeClientRequest request) {
        return runtimeService.syncRuntimeClient(request);
    }

    @Override
    public Uni<GetRuntimeClientResponse> getRuntimeClient(final GetRuntimeClientRequest request) {
        return runtimeService.getRuntimeClient(request);
    }

    @Override
    public Uni<FindRuntimeClientResponse> findRuntimeClient(final FindRuntimeClientRequest request) {
        return runtimeService.findRuntimeClient(request);
    }

    @Override
    public Uni<ViewRuntimeClientsResponse> viewRuntimeClients(final ViewRuntimeClientsRequest request) {
        return runtimeService.viewRuntimeClients(request);
    }

    @Override
    public Uni<CountRuntimeClientsResponse> countRuntimeClients(final CountRuntimeClientsRequest request) {
        return runtimeService.countRuntimeClients(request);
    }

    @Override
    public Uni<DeleteRuntimeClientResponse> deleteRuntimeClient(final DeleteRuntimeClientRequest request) {
        return runtimeService.deleteRuntimeClient(request);
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
    public Uni<DoMulticastMessageResponse> doMulticastMessage(final DoMulticastMessageRequest request) {
        return doService.doMulticastMessage(request);
    }

    @Override
    public Uni<DoBroadcastMessageResponse> doBroadcastMessage(final DoBroadcastMessageRequest request) {
        return doService.doBroadcastMessage(request);
    }
}
