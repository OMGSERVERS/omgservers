package com.omgservers.service.handler;

import com.omgservers.model.dto.matchmaker.DeleteMatchmakerRequest;
import com.omgservers.model.dto.matchmaker.DeleteMatchmakerResponse;
import com.omgservers.model.dto.tenant.GetVersionMatchmakerRequest;
import com.omgservers.model.dto.tenant.GetVersionMatchmakerResponse;
import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.event.body.VersionMatchmakerDeletedEventBodyModel;
import com.omgservers.model.versionMatchmaker.VersionMatchmakerModel;
import com.omgservers.service.module.matchmaker.MatchmakerModule;
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
public class VersionMatchmakerDeletedEventHandlerImpl implements EventHandler {

    final MatchmakerModule matchmakerModule;
    final TenantModule tenantModule;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.VERSION_MATCHMAKER_DELETED;
    }

    @Override
    public Uni<Boolean> handle(EventModel event) {
        final var body = (VersionMatchmakerDeletedEventBodyModel) event.getBody();
        final var tenantId = body.getTenantId();
        final var id = body.getId();

        return getDeletedVersionMatchmaker(tenantId, id)
                .flatMap(versionMatchmaker -> {
                    log.info("Version matchmaker was deleted, " +
                                    "versionMatchmaker={}/{}, " +
                                    "versionId={}, " +
                                    "matchmakerId={}",
                            tenantId,
                            id,
                            versionMatchmaker.getVersionId(),
                            versionMatchmaker.getMatchmakerId());

                    return deleteMatchmaker(versionMatchmaker);
                })
                .replaceWith(true);
    }

    Uni<VersionMatchmakerModel> getDeletedVersionMatchmaker(final Long tenantId, final Long id) {
        final var request = new GetVersionMatchmakerRequest(tenantId, id, true);
        return tenantModule.getVersionService().getVersionMatchmaker(request)
                .map(GetVersionMatchmakerResponse::getVersionMatchmaker);
    }

    Uni<Boolean> deleteMatchmaker(final VersionMatchmakerModel versionMatchmaker) {
        final var matchmakerId = versionMatchmaker.getMatchmakerId();

        final var request = new DeleteMatchmakerRequest(matchmakerId);
        return matchmakerModule.getMatchmakerService().deleteMatchmaker(request)
                .map(DeleteMatchmakerResponse::getDeleted);
    }
}
