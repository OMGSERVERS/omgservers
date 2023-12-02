package com.omgservers.service.handler;

import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.event.body.MatchmakerCreatedEventBodyModel;
import com.omgservers.model.job.JobQualifierEnum;
import com.omgservers.service.factory.JobModelFactory;
import com.omgservers.service.factory.VersionMatchmakerModelFactory;
import com.omgservers.service.module.matchmaker.MatchmakerModule;
import com.omgservers.service.module.system.SystemModule;
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
public class MatchmakerCreatedEventHandlerImpl implements EventHandler {

    final MatchmakerModule matchmakerModule;
    final SystemModule systemModule;
    final TenantModule tenantModule;

    final VersionMatchmakerModelFactory versionMatchmakerModelFactory;
    final JobModelFactory jobModelFactory;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.MATCHMAKER_CREATED;
    }

    @Override
    public Uni<Boolean> handle(final EventModel event) {
        log.debug("Handle event, {}", event);

        final var body = (MatchmakerCreatedEventBodyModel) event.getBody();
        final var id = body.getId();

        return matchmakerModule.getShortcutService().getMatchmaker(id)
                .flatMap(matchmaker -> {
                    log.info("Matchmaker was created, id={}, version={}/{}",
                            matchmaker.getId(),
                            matchmaker.getTenantId(),
                            matchmaker.getVersionId());

                    return syncMatchmakerJob(id);
                });
    }

    Uni<Boolean> syncMatchmakerJob(final Long matchmakerId) {
        final var job = jobModelFactory.create(matchmakerId, matchmakerId, JobQualifierEnum.MATCHMAKER);
        return systemModule.getShortcutService().syncJob(job);
    }
}
