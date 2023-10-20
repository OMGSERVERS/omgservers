package com.omgservers.handler;

import com.omgservers.dto.matchmaker.SyncMatchmakerRequest;
import com.omgservers.dto.matchmaker.SyncMatchmakerResponse;
import com.omgservers.dto.runtime.SyncRuntimeRequest;
import com.omgservers.dto.runtime.SyncRuntimeResponse;
import com.omgservers.dto.tenant.GetVersionRequest;
import com.omgservers.dto.tenant.GetVersionResponse;
import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.event.body.VersionCreatedEventBodyModel;
import com.omgservers.model.runtime.RuntimeConfigModel;
import com.omgservers.model.runtime.RuntimeTypeEnum;
import com.omgservers.model.version.VersionModeModel;
import com.omgservers.model.version.VersionModel;
import com.omgservers.module.matchmaker.MatchmakerModule;
import com.omgservers.module.matchmaker.factory.MatchmakerModelFactory;
import com.omgservers.module.runtime.RuntimeModule;
import com.omgservers.module.runtime.factory.RuntimeModelFactory;
import com.omgservers.module.system.impl.service.handlerService.impl.EventHandler;
import com.omgservers.module.tenant.TenantModule;
import com.omgservers.operation.generateId.GenerateIdOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class VersionCreatedEventHandlerImpl implements EventHandler {

    final MatchmakerModule matchmakerModule;
    final RuntimeModule runtimeModule;
    final TenantModule tenantModule;

    final MatchmakerModelFactory matchmakerModelFactory;
    final RuntimeModelFactory runtimeModelFactory;

    final GenerateIdOperation generateIdOperation;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.VERSION_CREATED;
    }

    @Override
    public Uni<Boolean> handle(EventModel event) {
        final var body = (VersionCreatedEventBodyModel) event.getBody();
        final var tenantId = body.getTenantId();
        final var id = body.getId();

        return getVersion(tenantId, id)
                .flatMap(version -> {
                    log.info("Version was created, versionId={}, tenantId={}, stageId={}, modes={}, files={}",
                            id,
                            tenantId,
                            version.getStageId(),
                            version.getConfig().getModes().stream().map(VersionModeModel::getName).toList(),
                            version.getSourceCode().getFiles().size());

                    return syncMatchmaker(version)
                            .flatMap(wasMatchmakerCreated -> syncRuntime(version));
                })
                .replaceWith(true);
    }

    Uni<VersionModel> getVersion(Long tenantId, Long id) {
        final var request = new GetVersionRequest(tenantId, id);
        return tenantModule.getVersionService().getVersion(request)
                .map(GetVersionResponse::getVersion);
    }

    Uni<Boolean> syncMatchmaker(final VersionModel version) {
        final var matchmakerId = version.getDefaultMatchmakerId();
        final var tenantId = version.getTenantId();
        final var versionId = version.getId();
        final var matchmaker = matchmakerModelFactory.create(matchmakerId, tenantId, versionId);
        final var request = new SyncMatchmakerRequest(matchmaker);
        return matchmakerModule.getMatchmakerService().syncMatchmaker(request)
                .map(SyncMatchmakerResponse::getCreated);
    }

    Uni<Boolean> syncRuntime(final VersionModel version) {
        final var runtimeConfig = new RuntimeConfigModel();
        final var scriptId = generateIdOperation.generateId();
        runtimeConfig.setScriptConfig(new RuntimeConfigModel.ScriptConfig(scriptId));
        final var runtime = runtimeModelFactory.create(
                version.getDefaultRuntimeId(),
                version.getTenantId(),
                version.getId(),
                RuntimeTypeEnum.EMBEDDED_GLOBAL_SCRIPT,
                runtimeConfig);
        final var syncRuntimeRequest = new SyncRuntimeRequest(runtime);
        return runtimeModule.getRuntimeService().syncRuntime(syncRuntimeRequest)
                .map(SyncRuntimeResponse::getCreated);
    }
}
