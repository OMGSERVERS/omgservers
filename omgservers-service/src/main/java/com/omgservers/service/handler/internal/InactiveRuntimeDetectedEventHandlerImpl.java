package com.omgservers.service.handler.internal;

import com.omgservers.schema.module.runtime.DeleteRuntimeRequest;
import com.omgservers.schema.module.runtime.DeleteRuntimeResponse;
import com.omgservers.schema.module.runtime.GetRuntimeRequest;
import com.omgservers.schema.module.runtime.GetRuntimeResponse;
import com.omgservers.schema.event.EventModel;
import com.omgservers.schema.event.EventQualifierEnum;
import com.omgservers.schema.event.body.internal.InactiveRuntimeDetectedEventBodyModel;
import com.omgservers.schema.model.runtime.RuntimeModel;
import com.omgservers.service.handler.EventHandler;
import com.omgservers.service.module.runtime.RuntimeModule;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class InactiveRuntimeDetectedEventHandlerImpl implements EventHandler {

    final RuntimeModule runtimeModule;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.INACTIVE_RUNTIME_DETECTED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.debug("Handle event, {}", event);

        final var body = (InactiveRuntimeDetectedEventBodyModel) event.getBody();
        final var runtimeId = body.getRuntimeId();

        return getRuntime(runtimeId)
                .flatMap(runtime -> {
                    if (runtime.getDeleted()) {
                        log.info("Runtime was already deleted, " +
                                "skip operation, runtimeId={}", runtimeId);
                        return Uni.createFrom().item(Boolean.TRUE);
                    } else {
                        log.warn("Inactive runtime was detected, runtimeId={}", runtimeId);

                        return deleteRuntime(runtimeId);
                    }
                })
                .replaceWithVoid();
    }

    Uni<RuntimeModel> getRuntime(final Long id) {
        final var request = new GetRuntimeRequest(id);
        return runtimeModule.getRuntimeService().getRuntime(request)
                .map(GetRuntimeResponse::getRuntime);
    }

    Uni<Boolean> deleteRuntime(final Long runtimeId) {
        final var request = new DeleteRuntimeRequest(runtimeId);
        return runtimeModule.getRuntimeService().deleteRuntime(request)
                .map(DeleteRuntimeResponse::getDeleted);
    }
}
