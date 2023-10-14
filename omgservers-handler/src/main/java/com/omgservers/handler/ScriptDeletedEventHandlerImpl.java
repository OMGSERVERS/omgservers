package com.omgservers.handler;

import com.omgservers.dto.script.GetScriptRequest;
import com.omgservers.dto.script.GetScriptResponse;
import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.event.body.ScriptDeletedEventBodyModel;
import com.omgservers.model.script.ScriptModel;
import com.omgservers.module.script.ScriptModule;
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

        return Uni.createFrom().item(true);
    }

    Uni<ScriptModel> getScript(final Long id) {
        final var request = new GetScriptRequest(id);
        return scriptModule.getScriptService().getScript(request)
                .map(GetScriptResponse::getScriptModel);
    }
}
