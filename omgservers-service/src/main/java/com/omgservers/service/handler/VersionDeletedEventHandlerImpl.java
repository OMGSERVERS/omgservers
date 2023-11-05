package com.omgservers.service.handler;

import com.omgservers.model.dto.tenant.DeleteVersionMatchmakerRequest;
import com.omgservers.model.dto.tenant.DeleteVersionMatchmakerResponse;
import com.omgservers.model.dto.tenant.DeleteVersionRuntimeRequest;
import com.omgservers.model.dto.tenant.DeleteVersionRuntimeResponse;
import com.omgservers.model.dto.tenant.GetVersionRequest;
import com.omgservers.model.dto.tenant.GetVersionResponse;
import com.omgservers.model.dto.tenant.ViewVersionMatchmakersRequest;
import com.omgservers.model.dto.tenant.ViewVersionMatchmakersResponse;
import com.omgservers.model.dto.tenant.ViewVersionRuntimesRequest;
import com.omgservers.model.dto.tenant.ViewVersionRuntimesResponse;
import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.event.body.VersionDeletedEventBodyModel;
import com.omgservers.model.version.VersionModeModel;
import com.omgservers.model.version.VersionModel;
import com.omgservers.model.versionMatchmaker.VersionMatchmakerModel;
import com.omgservers.model.versionRuntime.VersionRuntimeModel;
import com.omgservers.service.factory.VersionMatchmakerModelFactory;
import com.omgservers.service.factory.VersionRuntimeModelFactory;
import com.omgservers.service.module.system.impl.service.handlerService.impl.EventHandler;
import com.omgservers.service.module.tenant.TenantModule;
import com.omgservers.service.operation.generateId.GenerateIdOperation;
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
public class VersionDeletedEventHandlerImpl implements EventHandler {

    final TenantModule tenantModule;

    final VersionMatchmakerModelFactory versionMatchmakerModelFactory;
    final VersionRuntimeModelFactory versionRuntimeModelFactory;

    final GenerateIdOperation generateIdOperation;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.VERSION_DELETED;
    }

    @Override
    public Uni<Boolean> handle(EventModel event) {
        final var body = (VersionDeletedEventBodyModel) event.getBody();
        final var tenantId = body.getTenantId();
        final var id = body.getId();

        return getDeletedVersion(tenantId, id)
                .flatMap(version -> {
                    log.info("Version was deleted, versionId={}, tenantId={}, stageId={}, modes={}, files={}",
                            id,
                            tenantId,
                            version.getStageId(),
                            version.getConfig().getModes().stream().map(VersionModeModel::getName).toList(),
                            version.getSourceCode().getFiles().size());

                    return deleteVersionMatchmakers(version)
                            .flatMap(wasVersionRuntimesDeleted -> deleteVersionRuntimes(version));
                })
                .replaceWith(true);
    }

    Uni<VersionModel> getDeletedVersion(Long tenantId, Long id) {
        final var request = new GetVersionRequest(tenantId, id, true);
        return tenantModule.getVersionService().getVersion(request)
                .map(GetVersionResponse::getVersion);
    }

    Uni<Boolean> deleteVersionMatchmakers(final VersionModel version) {
        return viewVersionMatchmakers(version)
                .flatMap(versionMatchmakers -> Multi.createFrom().iterable(versionMatchmakers)
                        .onItem().transformToUniAndConcatenate(versionMatchmaker -> {
                            final var request = new DeleteVersionMatchmakerRequest(versionMatchmaker.getTenantId(),
                                    versionMatchmaker.getId());
                            return tenantModule.getVersionService().deleteVersionMatchmaker(request)
                                    .map(DeleteVersionMatchmakerResponse::getDeleted);
                        })
                        .collect().asList()
                        .replaceWith(Boolean.TRUE));
    }

    Uni<List<VersionMatchmakerModel>> viewVersionMatchmakers(final VersionModel version) {
        final var tenantId = version.getTenantId();
        final var versionId = version.getId();
        final var request = new ViewVersionMatchmakersRequest(tenantId, versionId, false);
        return tenantModule.getVersionService().viewVersionMatchmakers(request)
                .map(ViewVersionMatchmakersResponse::getVersionMatchmakers);
    }

    Uni<Boolean> deleteVersionRuntimes(final VersionModel version) {
        return viewVersionRuntimes(version)
                .flatMap(versionRuntimes -> {
                    return Multi.createFrom().iterable(versionRuntimes)
                            .onItem().transformToUniAndConcatenate(versionRuntime -> {
                                final var request = new DeleteVersionRuntimeRequest(versionRuntime.getTenantId(),
                                        versionRuntime.getId());
                                return tenantModule.getVersionService().deleteVersionRuntime(request)
                                        .map(DeleteVersionRuntimeResponse::getDeleted);
                            })
                            .collect().asList()
                            .replaceWith(Boolean.TRUE);
                });
    }

    Uni<List<VersionRuntimeModel>> viewVersionRuntimes(final VersionModel version) {
        final var tenantId = version.getTenantId();
        final var versionId = version.getId();
        final var request = new ViewVersionRuntimesRequest(tenantId, versionId, false);
        return tenantModule.getVersionService().viewVersionRuntimes(request)
                .map(ViewVersionRuntimesResponse::getVersionRuntimes);
    }
}
