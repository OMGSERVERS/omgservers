package com.omgservers.service.module.runtime.impl.service.shortcutService;

import com.omgservers.model.dto.runtime.DeleteRuntimeCommandRequest;
import com.omgservers.model.dto.runtime.DeleteRuntimeCommandResponse;
import com.omgservers.model.dto.runtime.DeleteRuntimeGrantRequest;
import com.omgservers.model.dto.runtime.DeleteRuntimeGrantResponse;
import com.omgservers.model.dto.runtime.DeleteRuntimePermissionRequest;
import com.omgservers.model.dto.runtime.DeleteRuntimePermissionResponse;
import com.omgservers.model.dto.runtime.DeleteRuntimeRequest;
import com.omgservers.model.dto.runtime.DeleteRuntimeResponse;
import com.omgservers.model.dto.runtime.FindRuntimeGrantRequest;
import com.omgservers.model.dto.runtime.FindRuntimeGrantResponse;
import com.omgservers.model.dto.runtime.GetRuntimeRequest;
import com.omgservers.model.dto.runtime.GetRuntimeResponse;
import com.omgservers.model.dto.runtime.SyncRuntimeCommandRequest;
import com.omgservers.model.dto.runtime.SyncRuntimeCommandResponse;
import com.omgservers.model.dto.runtime.SyncRuntimeRequest;
import com.omgservers.model.dto.runtime.SyncRuntimeResponse;
import com.omgservers.model.dto.runtime.ViewRuntimeCommandsRequest;
import com.omgservers.model.dto.runtime.ViewRuntimeCommandsResponse;
import com.omgservers.model.dto.runtime.ViewRuntimeGrantsRequest;
import com.omgservers.model.dto.runtime.ViewRuntimeGrantsResponse;
import com.omgservers.model.dto.runtime.ViewRuntimePermissionsRequest;
import com.omgservers.model.dto.runtime.ViewRuntimePermissionsResponse;
import com.omgservers.model.runtime.RuntimeModel;
import com.omgservers.model.runtimeCommand.RuntimeCommandModel;
import com.omgservers.model.runtimeGrant.RuntimeGrantModel;
import com.omgservers.model.runtimePermission.RuntimePermissionModel;
import com.omgservers.service.module.runtime.RuntimeModule;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class ShortcutServiceImpl implements ShortcutService {

    final RuntimeModule runtimeModule;

    @Override
    public Uni<RuntimeModel> getRuntime(final Long id) {
        final var request = new GetRuntimeRequest(id);
        return runtimeModule.getRuntimeService().getRuntime(request)
                .map(GetRuntimeResponse::getRuntime);
    }

    @Override
    public Uni<Boolean> syncRuntime(final RuntimeModel runtime) {
        final var syncRuntimeRequest = new SyncRuntimeRequest(runtime);
        return runtimeModule.getRuntimeService().syncRuntime(syncRuntimeRequest)
                .map(SyncRuntimeResponse::getCreated);
    }

    @Override
    public Uni<Boolean> deleteRuntime(final Long runtimeId) {
        final var request = new DeleteRuntimeRequest(runtimeId);
        return runtimeModule.getRuntimeService().deleteRuntime(request)
                .map(DeleteRuntimeResponse::getDeleted);
    }

    @Override
    public Uni<Boolean> syncRuntimeCommand(final RuntimeCommandModel runtimeCommand) {
        final var request = new SyncRuntimeCommandRequest(runtimeCommand);
        return runtimeModule.getRuntimeService().syncRuntimeCommand(request)
                .map(SyncRuntimeCommandResponse::getCreated);
    }

    @Override
    public Uni<List<RuntimeCommandModel>> viewRuntimeCommands(final Long runtimeId) {
        final var request = new ViewRuntimeCommandsRequest(runtimeId);
        return runtimeModule.getRuntimeService().viewRuntimeCommands(request)
                .map(ViewRuntimeCommandsResponse::getRuntimeCommands);
    }

    @Override
    public Uni<Boolean> deleteRuntimeCommand(final Long runtimeId, final Long id) {
        final var request = new DeleteRuntimeCommandRequest(runtimeId, id);
        return runtimeModule.getRuntimeService().deleteRuntimeCommand(request)
                .map(DeleteRuntimeCommandResponse::getDeleted);
    }

    @Override
    public Uni<Void> deleteRuntimeCommands(final Long runtimeId) {
        return viewRuntimeCommands(runtimeId)
                .flatMap(runtimeCommands -> Multi.createFrom().iterable(runtimeCommands)
                        .onItem().transformToUniAndConcatenate(runtimeCommand ->
                                deleteRuntimeCommand(runtimeId, runtimeCommand.getId()))
                        .collect().asList()
                        .replaceWithVoid()
                );
    }

    @Override
    public Uni<List<RuntimePermissionModel>> viewRuntimePermissions(final Long runtimeId) {
        final var request = new ViewRuntimePermissionsRequest(runtimeId);
        return runtimeModule.getRuntimeService().viewRuntimePermissions(request)
                .map(ViewRuntimePermissionsResponse::getRuntimePermissions);
    }

    @Override
    public Uni<Boolean> deleteRuntimePermission(final Long runtimeId, final Long id) {
        final var request = new DeleteRuntimePermissionRequest(runtimeId, id);
        return runtimeModule.getRuntimeService().deleteRuntimePermission(request)
                .map(DeleteRuntimePermissionResponse::getDeleted);
    }

    @Override
    public Uni<Void> deleteRuntimePermissions(final Long runtimeId) {
        return viewRuntimePermissions(runtimeId)
                .flatMap(runtimePermissions -> Multi.createFrom().iterable(runtimePermissions)
                        .onItem().transformToUniAndConcatenate(runtimePermission ->
                                deleteRuntimePermission(runtimeId, runtimePermission.getId()))
                        .collect().asList()
                        .replaceWithVoid()
                );
    }

    @Override
    public Uni<RuntimeGrantModel> findRuntimeGrant(final Long runtimeId, final Long clientId) {
        final var request = new FindRuntimeGrantRequest(runtimeId, clientId);
        return runtimeModule.getRuntimeService().findRuntimeGrant(request)
                .map(FindRuntimeGrantResponse::getRuntimeGrant);
    }

    @Override
    public Uni<List<RuntimeGrantModel>> viewRuntimeGrants(final Long runtimeId) {
        final var request = new ViewRuntimeGrantsRequest(runtimeId);
        return runtimeModule.getRuntimeService().viewRuntimeGrants(request)
                .map(ViewRuntimeGrantsResponse::getRuntimeGrants);
    }

    @Override
    public Uni<Boolean> deleteRuntimeGrant(final Long runtimeId, final Long id) {
        final var request = new DeleteRuntimeGrantRequest(runtimeId, id);
        return runtimeModule.getRuntimeService().deleteRuntimeGrant(request)
                .map(DeleteRuntimeGrantResponse::getDeleted);
    }

    @Override
    public Uni<Void> deleteRuntimeGrants(final Long runtimeId) {
        return viewRuntimeGrants(runtimeId)
                .flatMap(runtimeGrants -> Multi.createFrom().iterable(runtimeGrants)
                        .onItem().transformToUniAndConcatenate(runtimeGrant ->
                                deleteRuntimeGrant(runtimeId, runtimeGrant.getId()))
                        .collect().asList()
                        .replaceWithVoid()
                );
    }
}
