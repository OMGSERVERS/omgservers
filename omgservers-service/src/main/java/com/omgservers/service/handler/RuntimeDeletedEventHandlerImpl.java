package com.omgservers.service.handler;

import com.omgservers.model.container.ContainerModel;
import com.omgservers.model.container.ContainerQualifierEnum;
import com.omgservers.model.dto.runtime.DeleteRuntimeCommandRequest;
import com.omgservers.model.dto.runtime.DeleteRuntimeCommandResponse;
import com.omgservers.model.dto.runtime.DeleteRuntimeGrantRequest;
import com.omgservers.model.dto.runtime.DeleteRuntimeGrantResponse;
import com.omgservers.model.dto.runtime.DeleteRuntimePermissionRequest;
import com.omgservers.model.dto.runtime.DeleteRuntimePermissionResponse;
import com.omgservers.model.dto.runtime.GetRuntimeRequest;
import com.omgservers.model.dto.runtime.GetRuntimeResponse;
import com.omgservers.model.dto.runtime.ViewRuntimeCommandsRequest;
import com.omgservers.model.dto.runtime.ViewRuntimeCommandsResponse;
import com.omgservers.model.dto.runtime.ViewRuntimeGrantsRequest;
import com.omgservers.model.dto.runtime.ViewRuntimeGrantsResponse;
import com.omgservers.model.dto.runtime.ViewRuntimePermissionsRequest;
import com.omgservers.model.dto.runtime.ViewRuntimePermissionsResponse;
import com.omgservers.model.dto.system.DeleteContainerRequest;
import com.omgservers.model.dto.system.DeleteContainerResponse;
import com.omgservers.model.dto.system.FindContainerRequest;
import com.omgservers.model.dto.system.FindContainerResponse;
import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.event.body.RuntimeDeletedEventBodyModel;
import com.omgservers.model.runtime.RuntimeModel;
import com.omgservers.model.runtimeCommand.RuntimeCommandModel;
import com.omgservers.model.runtimeGrant.RuntimeGrantModel;
import com.omgservers.model.runtimePermission.RuntimePermissionModel;
import com.omgservers.service.factory.JobModelFactory;
import com.omgservers.service.module.runtime.RuntimeModule;
import com.omgservers.service.module.system.SystemModule;
import com.omgservers.service.module.system.impl.service.handlerService.impl.EventHandler;
import com.omgservers.service.module.tenant.TenantModule;
import com.omgservers.service.operation.getServers.GetServersOperation;
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
public class RuntimeDeletedEventHandlerImpl implements EventHandler {

    final RuntimeModule runtimeModule;
    final TenantModule tenantModule;
    final SystemModule systemModule;

    final GetServersOperation getServersOperation;
    final JobModelFactory jobModelFactory;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.RUNTIME_DELETED;
    }

    @Override
    public Uni<Boolean> handle(EventModel event) {
        final var body = (RuntimeDeletedEventBodyModel) event.getBody();
        final var runtimeId = body.getId();

        return getDeletedRuntime(runtimeId)
                .flatMap(runtime -> {
                    log.info("Runtime was deleted, " +
                                    "id={}, " +
                                    "type={}, " +
                                    "version={}/{}",
                            runtime.getId(),
                            runtime.getType(),
                            runtime.getTenantId(),
                            runtime.getVersionId());

                    // TODO: cleanup container user
                    return deleteContainer(runtimeId)
                            .flatMap(wasContainerDeleted -> deleteRuntimePermissions(runtimeId))
                            .flatMap(voidItem -> deleteRuntimeCommands(runtimeId))
                            .flatMap(voidItem -> deleteRuntimeGrants(runtimeId));
                })
                .replaceWith(true);
    }

    Uni<RuntimeModel> getDeletedRuntime(final Long id) {
        final var request = new GetRuntimeRequest(id);
        return runtimeModule.getRuntimeService().getRuntime(request)
                .map(GetRuntimeResponse::getRuntime);
    }

    Uni<Boolean> deleteContainer(final Long runtimeId) {
        return findContainer(runtimeId)
                .flatMap(container -> {
                    final var request = new DeleteContainerRequest(container.getId());
                    return systemModule.getContainerService().deleteContainer(request)
                            .map(DeleteContainerResponse::getDeleted);
                });
    }

    Uni<ContainerModel> findContainer(final Long runtimeId) {
        final var request = new FindContainerRequest(runtimeId, ContainerQualifierEnum.RUNTIME);
        return systemModule.getContainerService().findContainer(request)
                .map(FindContainerResponse::getContainer);
    }

    Uni<Void> deleteRuntimePermissions(final Long runtimeId) {
        return viewRuntimePermissions(runtimeId)
                .flatMap(runtimePermissions -> Multi.createFrom().iterable(runtimePermissions)
                        .onItem().transformToUniAndConcatenate(runtimePermission ->
                                deleteRuntimePermission(runtimeId, runtimePermission.getId()))
                        .collect().asList()
                        .replaceWithVoid()
                );
    }

    Uni<List<RuntimePermissionModel>> viewRuntimePermissions(final Long runtimeId) {
        final var request = new ViewRuntimePermissionsRequest(runtimeId);
        return runtimeModule.getRuntimeService().viewRuntimePermissions(request)
                .map(ViewRuntimePermissionsResponse::getRuntimePermissions);
    }

    Uni<Boolean> deleteRuntimePermission(final Long runtimeId, final Long id) {
        final var request = new DeleteRuntimePermissionRequest(runtimeId, id);
        return runtimeModule.getRuntimeService().deleteRuntimePermission(request)
                .map(DeleteRuntimePermissionResponse::getDeleted);
    }

    Uni<Void> deleteRuntimeCommands(final Long runtimeId) {
        return viewRuntimeCommands(runtimeId)
                .flatMap(runtimeCommands -> Multi.createFrom().iterable(runtimeCommands)
                        .onItem().transformToUniAndConcatenate(runtimeCommand ->
                                deleteRuntimeCommand(runtimeId, runtimeCommand.getId()))
                        .collect().asList()
                        .replaceWithVoid()
                );
    }

    Uni<List<RuntimeCommandModel>> viewRuntimeCommands(final Long runtimeId) {
        final var request = new ViewRuntimeCommandsRequest(runtimeId);
        return runtimeModule.getRuntimeService().viewRuntimeCommands(request)
                .map(ViewRuntimeCommandsResponse::getRuntimeCommands);
    }

    Uni<Boolean> deleteRuntimeCommand(final Long runtimeId, final Long id) {
        final var request = new DeleteRuntimeCommandRequest(runtimeId, id);
        return runtimeModule.getRuntimeService().deleteRuntimeCommand(request)
                .map(DeleteRuntimeCommandResponse::getDeleted);
    }

    Uni<Void> deleteRuntimeGrants(final Long runtimeId) {
        return viewRuntimeGrants(runtimeId)
                .flatMap(runtimeGrants -> Multi.createFrom().iterable(runtimeGrants)
                        .onItem().transformToUniAndConcatenate(runtimeGrant ->
                                deleteRuntimeGrant(runtimeId, runtimeGrant.getId()))
                        .collect().asList()
                        .replaceWithVoid()
                );
    }

    Uni<List<RuntimeGrantModel>> viewRuntimeGrants(final Long runtimeId) {
        final var request = new ViewRuntimeGrantsRequest(runtimeId);
        return runtimeModule.getRuntimeService().viewRuntimeGrants(request)
                .map(ViewRuntimeGrantsResponse::getRuntimeGrants);
    }

    Uni<Boolean> deleteRuntimeGrant(final Long runtimeId, final Long id) {
        final var request = new DeleteRuntimeGrantRequest(runtimeId, id);
        return runtimeModule.getRuntimeService().deleteRuntimeGrant(request)
                .map(DeleteRuntimeGrantResponse::getDeleted);
    }
}
