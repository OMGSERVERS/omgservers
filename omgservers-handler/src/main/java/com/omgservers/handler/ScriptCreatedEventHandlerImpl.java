package com.omgservers.handler;

import com.omgservers.dto.script.GetScriptRequest;
import com.omgservers.dto.script.GetScriptResponse;
import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.event.body.ScriptCreatedEventBodyModel;
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
public class ScriptCreatedEventHandlerImpl implements EventHandler {

    final ScriptModule scriptModule;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.SCRIPT_CREATED;
    }

    @Override
    public Uni<Boolean> handle(EventModel event) {
        final var body = (ScriptCreatedEventBodyModel) event.getBody();
        final var id = body.getId();


        return getScript(id)
                .invoke(script -> {
                    log.info("Script was created, id={}, type={}, tenantId={}, versionId={}, config={}",
                            id,
                            script.getType(),
                            script.getTenantId(),
                            script.getVersionId(),
                            script.getConfig());
                })
                .replaceWith(true);
    }

    Uni<ScriptModel> getScript(final Long id) {
        final var request = new GetScriptRequest(id);
        return scriptModule.getScriptService().getScript(request)
                .map(GetScriptResponse::getScriptModel);
    }
}
