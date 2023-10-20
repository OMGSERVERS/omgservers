package com.omgservers.module.runtime.impl.service.webService.impl;

import com.omgservers.dto.runtime.DeleteRuntimeCommandRequest;
import com.omgservers.dto.runtime.DeleteRuntimeCommandResponse;
import com.omgservers.dto.runtime.DeleteRuntimeGrantRequest;
import com.omgservers.dto.runtime.DeleteRuntimeGrantResponse;
import com.omgservers.dto.runtime.DeleteRuntimeRequest;
import com.omgservers.dto.runtime.DeleteRuntimeResponse;
import com.omgservers.dto.runtime.DoBroadcastMessageRequest;
import com.omgservers.dto.runtime.DoBroadcastMessageResponse;
import com.omgservers.dto.runtime.DoKickClientRequest;
import com.omgservers.dto.runtime.DoKickClientResponse;
import com.omgservers.dto.runtime.DoMulticastMessageRequest;
import com.omgservers.dto.runtime.DoMulticastMessageResponse;
import com.omgservers.dto.runtime.DoRespondClientRequest;
import com.omgservers.dto.runtime.DoRespondClientResponse;
import com.omgservers.dto.runtime.DoSetAttributesRequest;
import com.omgservers.dto.runtime.DoSetAttributesResponse;
import com.omgservers.dto.runtime.DoSetObjectRequest;
import com.omgservers.dto.runtime.DoSetObjectResponse;
import com.omgservers.dto.runtime.DoStopRuntimeRequest;
import com.omgservers.dto.runtime.DoStopRuntimeResponse;
import com.omgservers.dto.runtime.DoUnicastMessageRequest;
import com.omgservers.dto.runtime.DoUnicastMessageResponse;
import com.omgservers.dto.runtime.FindRuntimeGrantRequest;
import com.omgservers.dto.runtime.FindRuntimeGrantResponse;
import com.omgservers.dto.runtime.GetRuntimeRequest;
import com.omgservers.dto.runtime.GetRuntimeResponse;
import com.omgservers.dto.runtime.SyncRuntimeCommandRequest;
import com.omgservers.dto.runtime.SyncRuntimeCommandResponse;
import com.omgservers.dto.runtime.SyncRuntimeGrantRequest;
import com.omgservers.dto.runtime.SyncRuntimeGrantResponse;
import com.omgservers.dto.runtime.SyncRuntimeRequest;
import com.omgservers.dto.runtime.SyncRuntimeResponse;
import com.omgservers.dto.runtime.UpdateRuntimeCommandsStatusRequest;
import com.omgservers.dto.runtime.UpdateRuntimeCommandsStatusResponse;
import com.omgservers.dto.runtime.ViewRuntimeCommandsRequest;
import com.omgservers.dto.runtime.ViewRuntimeCommandsResponse;
import com.omgservers.module.runtime.impl.service.doService.DoService;
import com.omgservers.module.runtime.impl.service.runtimeService.RuntimeService;
import com.omgservers.module.runtime.impl.service.webService.WebService;
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
    public Uni<UpdateRuntimeCommandsStatusResponse> updateRuntimeCommandsStatus(
            final UpdateRuntimeCommandsStatusRequest request) {
        return runtimeService.updateRuntimeCommandsStatus(request);
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
    public Uni<DoSetObjectResponse> doSetObject(DoSetObjectRequest request) {
        return doService.doSetObject(request);
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
