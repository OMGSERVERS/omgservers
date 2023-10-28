package com.omgservers.handler;

import com.omgservers.model.dto.internal.DeleteJobRequest;
import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.event.body.ScriptDeletedEventBodyModel;
import com.omgservers.model.job.JobQualifierEnum;
import com.omgservers.model.script.ScriptModel;
import com.omgservers.module.script.ScriptModule;
import com.omgservers.module.system.SystemModule;
import com.omgservers.module.system.impl.service.handlerService.impl.EventHandler;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class ScriptDeletedEventHandlerImpl implements EventHandler {

    final SystemModule systemModule;
    final ScriptModule scriptModule;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.SCRIPT_DELETED;
    }

    @Override
    public Uni<Boolean> handle(EventModel event) {
        final var body = (ScriptDeletedEventBodyModel) event.getBody();
        final var script = body.getScript();

        log.info("Script was deleted, id={}, type={}, tenantId={}, versionId={}, config={}",
                script.getId(),
                script.getType(),
                script.getTenantId(),
                script.getVersionId(),
                script.getConfig());

        return deleteScriptJob(script)
                .replaceWith(true);
    }

    Uni<Boolean> deleteScriptJob(final ScriptModel script) {
        final var shardKey = script.getId();
        final var entityId = script.getId();
        final var request = new DeleteJobRequest(shardKey, entityId, JobQualifierEnum.SCRIPT);
        return systemModule.getJobService().deleteJob(request)
                .replaceWith(true);
    }
}
