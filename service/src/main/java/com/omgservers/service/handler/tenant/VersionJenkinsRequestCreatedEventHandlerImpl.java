package com.omgservers.service.handler.tenant;

import com.omgservers.schema.module.tenant.versionJenkinsRequest.GetVersionJenkinsRequestRequest;
import com.omgservers.schema.module.tenant.versionJenkinsRequest.GetVersionJenkinsRequestResponse;
import com.omgservers.schema.event.EventModel;
import com.omgservers.schema.event.EventQualifierEnum;
import com.omgservers.schema.event.body.module.tenant.VersionJenkinsRequestCreatedEventBodyModel;
import com.omgservers.schema.model.versionJenkinsRequest.VersionJenkinsRequestModel;
import com.omgservers.service.factory.system.EventModelFactory;
import com.omgservers.service.handler.EventHandler;
import com.omgservers.service.module.system.SystemModule;
import com.omgservers.service.module.tenant.TenantModule;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class VersionJenkinsRequestCreatedEventHandlerImpl implements EventHandler {

    final TenantModule tenantModule;
    final SystemModule systemModule;

    final EventModelFactory eventModelFactory;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.VERSION_JENKINS_REQUEST_CREATED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.debug("Handle event, {}", event);

        final var body = (VersionJenkinsRequestCreatedEventBodyModel) event.getBody();
        final var tenantId = body.getTenantId();
        final var id = body.getId();

        return getVersionJenkinsRequest(tenantId, id)
                .flatMap(versionJenkinsRequest -> {
                    final var versionId = versionJenkinsRequest.getVersionId();
                    final var qualifier = versionJenkinsRequest.getQualifier();
                    final var buildNumber = versionJenkinsRequest.getBuildNumber();
                    log.info("Version jenkins request was created, " +
                                    "id={}, version={}/{}, qualifier={}, buildNumber={}",
                            versionJenkinsRequest.getId(),
                            tenantId,
                            versionId,
                            qualifier,
                            buildNumber);

                    return Uni.createFrom().voidItem();
                })
                .replaceWithVoid();
    }

    Uni<VersionJenkinsRequestModel> getVersionJenkinsRequest(final Long tenantId, final Long id) {
        final var request = new GetVersionJenkinsRequestRequest(tenantId, id);
        return tenantModule.getVersionService().getVersionJenkinsRequest(request)
                .map(GetVersionJenkinsRequestResponse::getVersionJenkinsRequest);
    }
}
