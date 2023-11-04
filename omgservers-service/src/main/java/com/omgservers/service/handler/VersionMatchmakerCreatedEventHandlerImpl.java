package com.omgservers.service.handler;

import com.omgservers.model.dto.tenant.GetVersionMatchmakerRequest;
import com.omgservers.model.dto.tenant.GetVersionMatchmakerResponse;
import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.event.body.VersionMatchmakerCreatedEventBodyModel;
import com.omgservers.model.versionMatchmaker.VersionMatchmakerModel;
import com.omgservers.service.module.system.impl.service.handlerService.impl.EventHandler;
import com.omgservers.service.module.tenant.TenantModule;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class VersionMatchmakerCreatedEventHandlerImpl implements EventHandler {

    final TenantModule tenantModule;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.VERSION_MATCHMAKER_CREATED;
    }

    @Override
    public Uni<Boolean> handle(EventModel event) {
        final var body = (VersionMatchmakerCreatedEventBodyModel) event.getBody();
        final var tenantId = body.getTenantId();
        final var id = body.getId();

        return getVersionMatchmaker(tenantId, id)
                .invoke(versionMatchmaker -> log.info("Version matchmaker was created, " +
                                "id={}, " +
                                "tenantId={}, " +
                                "versionId={}, " +
                                "matchmakerId={}",
                        id,
                        tenantId,
                        versionMatchmaker.getVersionId(),
                        versionMatchmaker.getMatchmakerId()))
                .replaceWith(true);
    }

    Uni<VersionMatchmakerModel> getVersionMatchmaker(final Long tenantId, final Long id) {
        final var request = new GetVersionMatchmakerRequest(tenantId, id);
        return tenantModule.getVersionService().getVersionMatchmaker(request)
                .map(GetVersionMatchmakerResponse::getVersionMatchmaker);
    }
}
